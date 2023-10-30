package com.restapi.services;

import com.restapi.dto.MenuDTO;
import com.restapi.dto.TypeDTO;
import com.restapi.exceptions.ErrorDetail;
import com.restapi.exceptions.RMValidateException;
import com.restapi.mapper.MenuMapper;
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

    public MenuDTO createMenu(MenuDTO menu) {
        if (menu.getType().isEmpty()) {
            throw new RMValidateException(new ErrorDetail(
                    new Date().toString(),
                    HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST.getReasonPhrase(),
                    RMConstant.TYPE_BAD_REQUEST));
        }
        List<Type> types = new ArrayList<>();
        for (TypeDTO typeDTO : menu.getType()) {
            Type type = new Type();
            type.setType(typeDTO.getType());
            types.add(type);
        }
        Menu menuToCreate = new Menu();
        menuToCreate.setName(menu.getName());
        menuToCreate.setDescription(menu.getDescription());
        menuToCreate.setPrice(menu.getPrice());
        menuToCreate.setImage(menu.getImage());
        menuToCreate.setType(types);

        typeRepository.saveAll(types);
        try {
            menuRepository.save(menuToCreate);
            return menu;
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
                menuDTOs.add(MenuMapper.menuToMenuDTOMapper(menu));
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
        return MenuMapper.menuToMenuDTOMapper(menuRepository.findById(id).get());
    }

    public MenuDTO updateMenu(UUID id, MenuDTO menu) {
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
            List<Type> types = new ArrayList<>();
            for (TypeDTO typeDTO : menu.getType()) {
                Type type = new Type();
                type.setType(typeDTO.getType());
                types.add(type);
            }
            Menu menuToUpdate = new Menu();
            menuToUpdate.setId(id);
            menuToUpdate.setName(menu.getName());
            menuToUpdate.setDescription(menu.getDescription());
            menuToUpdate.setImage(menu.getImage());
            menuToUpdate.setPrice(menu.getPrice());
            menuToUpdate.setType(types);
            typeRepository.saveAll(types);
            menuRepository.save(menuToUpdate);
            return menu;
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