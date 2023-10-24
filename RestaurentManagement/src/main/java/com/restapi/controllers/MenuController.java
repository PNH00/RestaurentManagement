package com.restapi.controllers;

import com.restapi.models.Menu;
import com.restapi.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Objects;
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
    public Object createMenu(@RequestBody Menu menu) {
        return menuService.createMenu(menu);
    }

    @GetMapping
    public ResponseEntity<List<Menu>> getAllMenusPaged(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy)
    {
        List<Menu> list = menuService.getAllMenusPaged(page, size, sortBy);
        return new ResponseEntity<>(list,new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMenuById(@PathVariable UUID id) {
        Object menu = menuService.getMenuById(id);
        return menu!=null ?
                new ResponseEntity<>(menu,HttpStatus.OK):
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMenu(@PathVariable UUID id, @RequestBody Menu menu) {
        return new  ResponseEntity<>(menuService.updateMenu(id, menu),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMenu(@PathVariable UUID id) {
        return new ResponseEntity<>(menuService.deleteMenu(id), HttpStatus.OK);
    }
}