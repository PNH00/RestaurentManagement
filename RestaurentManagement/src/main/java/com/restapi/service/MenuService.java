package com.restapi.service;

import com.restapi.exceptions.ErrorDetail;
import com.restapi.exceptions.ErrorDetailHandler;
import com.restapi.exceptions.ResourceException;
import com.restapi.models.Menu;
import com.restapi.models.Type;
import com.restapi.repositories.MenuRepository;
import com.restapi.repositories.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.util.*;

@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final TypeRepository typeRepository;
    private final ErrorDetailHandler errorDetailHandler = new ErrorDetailHandler();
    @Autowired
    public MenuService(MenuRepository menuRepository, TypeRepository typeRepository) {
        this.menuRepository = menuRepository;
        this.typeRepository = typeRepository;
    }

    public Menu createMenu(Menu menu) {
        if (menu.getType().isEmpty()) {
            ErrorDetail errorDetail = errorDetailHandler.createNotFoundErrorDetail("Cannot create!",menu);
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
        return menuRepository.findById(id);
    }

    public Menu updateMenu(UUID id, Menu menu) {
        if (menuRepository.existsById(id)) {
            if (menu.getType().isEmpty()) {
                ErrorDetail errorDetail = errorDetailHandler.createNotFoundErrorDetail("Cannot update!",menu);
                throw new ResourceException(errorDetail);
            }
            menu.setId(id);
            typeRepository.saveAll(menu.getType());
            return menuRepository.save(menu);
        }
        return null;
    }

    public void deleteMenu(UUID id) {
        try {
            if (!menuRepository.existsById(id)) {
                throw new EmptyResultDataAccessException(1);
            }
            menuRepository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Menu not found with id: " + id);
        } catch (DataAccessException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error occurred");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal server error occurred");
        }
    }
}