package com.restapi.controllers;

import com.restapi.exceptions.ErrorDetail;
import com.restapi.exceptions.ResourceException;
import com.restapi.models.Menu;
import com.restapi.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/api/menus")
public class MenuController {

    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping
    public Menu createMenu(@RequestBody Menu menu) {
        return menuService.createMenu(menu);
    }

    @GetMapping
    public ResponseEntity<List<Menu>> getAllMenus() {
        List<Menu> menus = menuService.getAllMenus();
        return new ResponseEntity<>(menus, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMenuById(@PathVariable UUID id) {
        try {
            Optional<Menu> menuOptional = menuService.getMenuById(id);
            Menu menu = menuOptional.orElseThrow(() -> new ResourceException(menuService.createNotFoundErrorDetail(id)));
            return new ResponseEntity<>(menu, HttpStatus.OK);
        } catch (ResourceException e) {
            ErrorDetail errorDetail = e.getErrorDetail();
            return new ResponseEntity<>(errorDetail, HttpStatus.valueOf(errorDetail.getCode()));
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMenu(@PathVariable UUID id, @RequestBody Menu menu) {
        try {
            Menu updatedMenu = menuService.updateMenu(id, menu);
            return new ResponseEntity<>(updatedMenu, HttpStatus.OK);
        } catch (ResourceException e) {
            ErrorDetail errorDetail = e.getErrorDetail();
            return new ResponseEntity<>(errorDetail, HttpStatus.valueOf(errorDetail.getCode()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ErrorDetail> deleteMenu(@PathVariable UUID id) {
        try {
            menuService.deleteMenu(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (ResourceException e) {
            ErrorDetail errorDetail = e.getErrorDetail();
            return new ResponseEntity<>(errorDetail, HttpStatus.valueOf(errorDetail.getCode()));
        }
    }
}
