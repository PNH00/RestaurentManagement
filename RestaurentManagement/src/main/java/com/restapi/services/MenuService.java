package com.restapi.services;

import com.restapi.dto.MenuDTO;
import com.restapi.exceptions.ErrorDetail;
import com.restapi.exceptions.RMValidateException;
import com.restapi.mapper.MenuMapper;
import com.restapi.mapper.TypeMapper;
import com.restapi.models.Menu;
import com.restapi.models.Type;
import com.restapi.repositories.MenuRepository;
import com.restapi.repositories.TypeRepository;
import com.restapi.constants.RMConstant;
import com.restapi.utils.RMUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final TypeRepository typeRepository;

    @Autowired
    public MenuService(MenuRepository menuRepository, TypeRepository typeRepository) {
        this.menuRepository = menuRepository;
        this.typeRepository = typeRepository;
    }

    public MenuDTO createMenu(Menu menu) {
        if (menu.getType().isEmpty()) {
            throw new RMValidateException(new ErrorDetail(
                    new Date().toString(),
                    HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST.getReasonPhrase(),
                    RMConstant.TYPE_BAD_REQUEST));
        }
        List<Type> types = menu.getType();
        List<Type> existingTypes = new ArrayList<>();
        for (Type type : types) {
            if (type.getId() != null && typeRepository.existsById(type.getId())) {
                existingTypes.add(type);
            } else {
                Type newType = typeRepository.save(type);
                existingTypes.add(newType);
            }
        }
        menu.setType(existingTypes);
        try {
            Menu menuCreated = menuRepository.save(menu);
            return MenuMapper.menuMapper(menuCreated);
        }catch (Exception e){
            throw new RMValidateException(new ErrorDetail(
                    new Date().toString(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                    RMConstant.INTERNAL_SERVER_ERROR));
        }
    }

    public List<MenuDTO> getAllMenusPaged(int page, int size, String sortBy,String order) {
        int trueSize = RMUtils.setSize(size);
        int truePage = RMUtils.setPage(page,trueSize, (int) menuRepository.count());
        Pageable pageable = RMUtils.sortOrder(truePage,trueSize,sortBy,order);
        Page<Menu> pagedResult = menuRepository.findAll(pageable);
        if (pagedResult.hasContent()){
            List<MenuDTO> menuDTOs = new ArrayList<MenuDTO>();
            for (Menu menu : pagedResult.getContent())  {
                menuDTOs.add(MenuMapper.menuMapper(menu));
            }
            return menuDTOs;
        }
        else
            return new ArrayList<MenuDTO>();
    }

    public MenuDTO getMenuById(UUID id){
        if (menuRepository.findById(id).isEmpty())
            throw new RMValidateException(new ErrorDetail(
                    new Date().toString(),
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    RMConstant.MENU_NOT_FOUND));
        return MenuMapper.menuMapper(menuRepository.findById(id).get());
    }

    public MenuDTO updateMenu(UUID id, Menu menu) {
        if(!menuRepository.existsById(id))
            throw new RMValidateException(new ErrorDetail(
                    new Date().toString(),
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    RMConstant.MENU_NOT_FOUND));
        else {
            if (menu.getType().isEmpty()) {
                throw new RMValidateException(new ErrorDetail(
                        new Date().toString(),
                        HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        RMConstant.MENU_BAD_REQUEST));
            }
            menu.setId(id);
            typeRepository.saveAll(menu.getType());
            Menu menuUpdated = menuRepository.save(menu);
            return MenuMapper.menuMapper(menuUpdated);
        }
    }

    public void deleteMenu(UUID id) {
        if(menuRepository.existsById(id))
            menuRepository.deleteById(id);
        else
            throw new RMValidateException(new ErrorDetail(
                    new Date().toString(),
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    RMConstant.MENU_NOT_FOUND));
    }
}