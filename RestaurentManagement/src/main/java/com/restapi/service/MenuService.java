package com.restapi.service;

import com.restapi.exceptions.ErrorDetail;
import com.restapi.exceptions.ResourceException;
import com.restapi.models.Menu;
import com.restapi.models.Type;
import com.restapi.repositories.MenuRepository;
import com.restapi.repositories.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public ErrorDetail createNotFoundErrorDetail(UUID id) {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date().toString());
        errorDetail.setStatus("Not found");
        errorDetail.setCode(HttpStatus.NOT_FOUND.value());
        errorDetail.setMessage("Check your id '" + id + "' and try again");
        errorDetail.setData(null);
        return errorDetail;
    }

    public ErrorDetail createNotFoundErrorDetail(String message, Menu menu) {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date().toString());
        errorDetail.setStatus("Bad Request");
        errorDetail.setCode(HttpStatus.BAD_REQUEST.value());
        errorDetail.setMessage(message);
        errorDetail.setData(menu);
        return errorDetail;
    }

    public Menu createMenu(Menu menu) {
        if (menu.getType().isEmpty()) {
            ErrorDetail errorDetail = createNotFoundErrorDetail("Cannot create!",menu);
            throw new ResourceException(errorDetail);
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
        return menuRepository.save(menu);
    }

    public List<Menu> getAllMenus() {
        return menuRepository.findAll();
    }

    public Optional<Menu> getMenuById(UUID id){
        if (!menuRepository.existsById(id)) {
            ErrorDetail errorDetail = createNotFoundErrorDetail(id);
            throw new ResourceException(errorDetail);
        }
        return menuRepository.findById(id);
    }

    public Menu updateMenu(UUID id, Menu menu) {
        if (!menuRepository.existsById(id)) {
            ErrorDetail errorDetail = createNotFoundErrorDetail(id);
            throw new ResourceException(errorDetail);
        }
        if (menuRepository.existsById(id)) {
            if (menu.getType().isEmpty()) {
                ErrorDetail errorDetail = createNotFoundErrorDetail("Cannot update!",menu);
                throw new ResourceException(errorDetail);
            }
            menu.setId(id);
            typeRepository.saveAll(menu.getType());
            return menuRepository.save(menu);
        }
        return null;
    }

    public void deleteMenu(UUID id) {
        if (!menuRepository.existsById(id)) {
            ErrorDetail errorDetail = createNotFoundErrorDetail(id);
            throw new ResourceException(errorDetail);
        }
        menuRepository.deleteById(id);
    }
}
