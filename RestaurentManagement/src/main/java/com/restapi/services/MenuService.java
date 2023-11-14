package com.restapi.services;

import com.restapi.dto.MenuDTO;
import com.restapi.dto.ErrorResponse;
import com.restapi.exceptions.RMValidateException;
import com.restapi.mapper.MenuMapper;
import com.restapi.models.Menu;
import com.restapi.models.Type;
import com.restapi.repositories.MenuRepository;
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
    private final TypeService typeService;

    @Autowired
    public MenuService(MenuRepository menuRepository, TypeService typeService) {
        this.menuRepository = menuRepository;
        this.typeService = typeService;
    }

    public MenuDTO createMenu(MenuDTO menu) {
        Menu menuToCreate = checkTypesAndPrice(menu);
        if(searchMenuByName(menuToCreate.getName())!=null){
            throw new RMValidateException(new ErrorResponse(
                    new Date().toString(),
                    HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST.getReasonPhrase(),
                    RMConstant.NAME_EXISTED));
        }
        try {
            menuRepository.save(menuToCreate);
            return MenuMapper.menuToMenuDTOMapper(menuToCreate);
        }catch (Exception e){
            throw new RMValidateException(new ErrorResponse(
                    new Date().toString(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                    RMConstant.SOME_THING_WRONG));
        }
    }

    public List<MenuDTO> getAllMenusPaged(int page, int size, String sortBy,String order) {
        int trueSize = RMUtils.setSize(size);
        int truePage = RMUtils.setPage(page,trueSize, (int) menuRepository.count());
        Pageable pageable = RMUtils.sortOrder(truePage,trueSize,sortBy,order);
        Page<Menu> pagedResult = menuRepository.findAll(pageable);
        if (pagedResult.hasContent()){
            return MenuMapper.menuToMenuDTOMapper(pagedResult.getContent());
        }
        else
            return new ArrayList<>();
    }

    public MenuDTO getMenuById(UUID id){
        if (menuRepository.findById(id).isEmpty())
            throw new RMValidateException(new ErrorResponse(
                    new Date().toString(),
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    RMConstant.MENU_NOT_FOUND));
        return MenuMapper.menuToMenuDTOMapper(menuRepository.findById(id).get());
    }

    public MenuDTO updateMenu(UUID id, MenuDTO menu) {
        if(!menuRepository.existsById(id))
            throw new RMValidateException(new ErrorResponse(
                    new Date().toString(),
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    RMConstant.MENU_NOT_FOUND));
        else {
            Menu menuToUpdate = checkTypesAndPrice(menu);
            menuToUpdate.setId(id);
            try {
                menuRepository.save(menuToUpdate);
                return MenuMapper.menuToMenuDTOMapper(menuToUpdate);
            }catch (Exception e){
                throw new RMValidateException(new ErrorResponse(
                        new Date().toString(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                        RMConstant.SOME_THING_WRONG));
            }
        }
    }

    public void deleteMenu(UUID id) {
        if(menuRepository.findById(id).isEmpty()){
            throw new RMValidateException(new ErrorResponse(
                    new Date().toString(),
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    RMConstant.MENU_NOT_FOUND));
        }
        else{
            if(!menuRepository.findById(id).get().getBills().isEmpty()){
                throw new RMValidateException(new ErrorResponse(
                        new Date().toString(),
                        HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        RMConstant.MENU_HAD_USED));
            }
            try {
                menuRepository.deleteById(id);
            }catch (Exception e){
                throw new RMValidateException(new ErrorResponse(
                        new Date().toString(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                        RMConstant.SOME_THING_WRONG));
            }
        }

    }

    public List<MenuDTO> searchMenus(String keyword) {
        if (keyword==null){
            throw new RMValidateException(new ErrorResponse(
                    new Date().toString(),
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    RMConstant.MENU_NOT_FOUND));
        }
        Set<Menu> uniqueMenus = new HashSet<>();
        uniqueMenus.addAll(menuRepository.findByNameContaining(keyword));
        uniqueMenus.addAll(menuRepository.findByDescriptionContaining(keyword));
        if (!typeService.searchTypesByType(keyword).isEmpty()){
            Set<Type> types = new HashSet<>(typeService.searchTypesByType(keyword));
            for (Type type:types) {
                uniqueMenus.addAll(menuRepository.findMenusByTypesContaining(type));
            }
        }
        if (uniqueMenus.isEmpty()){
            throw new RMValidateException(new ErrorResponse(
                    new Date().toString(),
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    RMConstant.MENU_NOT_FOUND));
        }
        return MenuMapper.menuToMenuDTOMapper(new ArrayList<>(uniqueMenus));
    }
    public Menu searchMenuByName(String name) {
        return menuRepository.findByNameEquals(name);
    }

    public Menu checkTypesAndPrice(MenuDTO menu){
        if (menu.getTypes().isEmpty()) {
            throw new RMValidateException(new ErrorResponse(
                    new Date().toString(),
                    HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST.getReasonPhrase(),
                    RMConstant.MENU_BAD_REQUEST));
        }
        if (menu.getPrice()<0){
            throw new RMValidateException(new ErrorResponse(
                    new Date().toString(),
                    HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST.getReasonPhrase(),
                    RMConstant.PRICE_VALIDATION));
        }
        List<Type> types = typeService.saveAllType(menu.getTypes());
        Menu menuChecked = MenuMapper.menuDTOToMenuMapper(menu);
        menuChecked.setTypes(types);
        return menuChecked;
    }
}