package com.restapi.controllers;

import com.restapi.dto.MenuDTO;
import com.restapi.exceptions.SuccessfulResponse;
import com.restapi.models.Menu;
import com.restapi.services.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
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
    public SuccessfulResponse createMenu(@RequestBody Menu menu) {
        return menuService.createMenu(menu);
    }

    @GetMapping
    public ResponseEntity<List<MenuDTO>> getAllMenusPaged(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String order)
    {
        List<MenuDTO> list = menuService.getAllMenusPaged(page, size, sortBy,order);
        return new ResponseEntity<>(list,new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMenuById(@PathVariable UUID id) {
        return new ResponseEntity<>(menuService.getMenuById(id),HttpStatus.OK);

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