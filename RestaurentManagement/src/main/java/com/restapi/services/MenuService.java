package com.restapi.services;

import com.restapi.dto.MenuDTO;
import com.restapi.response.ErrorResponse;
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
        if (menu.getType().isEmpty()) {
            throw new RMValidateException(new ErrorResponse(
                    new Date().toString(),
                    HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST.getReasonPhrase(),
                    RMConstant.TYPE_BAD_REQUEST));
        }
        if (menu.getPrice()<0){
            throw new RMValidateException(new ErrorResponse(
                    new Date().toString(),
                    HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST.getReasonPhrase(),
                    "Price cannot less than 0!"));
        }
        List<Type> types = typeService.saveAllType(menu.getType());
        Menu menuToCreate = MenuMapper.menuDTOToMenuMapper(menu);
        menuToCreate.setType(types);
        if(searchMenuByName(menuToCreate.getName())!=null){
            throw new RMValidateException(new ErrorResponse(
                    new Date().toString(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                    RMConstant.NAME_EXISTED));
        }
        try {
            menuRepository.save(menuToCreate);
            return menu;
        }catch (Exception e){
            throw new RMValidateException(new ErrorResponse(
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
            return MenuMapper.menuToMenuDTOMapper(pagedResult.getContent());
        }
        else
            return new ArrayList<MenuDTO>();
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
            if (menu.getType().isEmpty()) {
                throw new RMValidateException(new ErrorResponse(
                        new Date().toString(),
                        HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        RMConstant.MENU_BAD_REQUEST));
            }
            List<Type> types = typeService.saveAllType(menu.getType());
            Menu menuToUpdate = MenuMapper.menuDTOToMenuMapper(menu);
            menuToUpdate.setType(types);
            menuToUpdate.setId(id);
            menuRepository.save(menuToUpdate);
            return menu;
        }
    }

    public void deleteMenu(UUID id) {
        if(!menuRepository.existsById(id)){
            throw new RMValidateException(new ErrorResponse(
                    new Date().toString(),
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    RMConstant.MENU_NOT_FOUND));
        }
        else{
            try {
                menuRepository.deleteById(id);
            }catch (Exception e){
                throw new RMValidateException(new ErrorResponse(
                        new Date().toString(),
                        HttpStatus.NOT_FOUND.value(),
                        HttpStatus.NOT_FOUND.getReasonPhrase(),
                        RMConstant.BILL_INTERNAL_SERVER_ERROR));
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
        List<Menu> menusByName = menuRepository.findByNameEquals(keyword);
        List<Menu> menusByDescription = menuRepository.findByDescriptionEquals(keyword);
        List<Menu> menusByType = menuRepository.findByTypeTypeEquals(keyword);

        Set<Menu> uniqueMenus = new HashSet<>();
        uniqueMenus.addAll(menusByName);
        uniqueMenus.addAll(menusByDescription);
        uniqueMenus.addAll(menusByType);

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
        if (name==null){
            throw new RMValidateException(new ErrorResponse(
                    new Date().toString(),
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    RMConstant.MENU_NOT_FOUND));
        }
        List<Menu> menusByName = menuRepository.findByNameEquals(name);
        Set<Menu> uniqueMenus = new HashSet<>(menusByName);
        if (uniqueMenus.isEmpty()){
            return null;
        }
        return new ArrayList<>(uniqueMenus).get(0);
    }
}