package com.restapi.controllers;

import com.restapi.constants.RMConstant;
import com.restapi.dto.MenuDTO;
import com.restapi.dto.SuccessResponse;
import com.restapi.services.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<SuccessResponse> createMenu(@RequestBody MenuDTO menu) {
        SuccessResponse  successResponse = new SuccessResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                RMConstant.CREATE_MENU_SUCCESSFULLY,
                menuService.createMenu(menu));
        return new ResponseEntity<>(successResponse,HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<SuccessResponse> getAllMenusPaged(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String order)
    {
        List<MenuDTO> list = menuService.getAllMenusPaged(page, size, sortBy,order);
        SuccessResponse  successResponse = new SuccessResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                RMConstant.GET_MENUS_SUCCESSFULLY,
                list);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse> getMenuById(@PathVariable UUID id) {
        SuccessResponse  successResponse = new SuccessResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                RMConstant.GET_MENU_SUCCESSFULLY,
                menuService.getMenuById(id));
        return new ResponseEntity<>(successResponse,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse> updateMenu(@PathVariable UUID id, @RequestBody MenuDTO menu) {
        SuccessResponse  successResponse = new SuccessResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                RMConstant.UPDATE_MENU_SUCCESSFULLY,
                menuService.updateMenu(id,menu));
        return new ResponseEntity<>(successResponse,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> deleteMenu(@PathVariable UUID id) {
        menuService.deleteMenu(id);
        SuccessResponse successResponse = new SuccessResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                RMConstant.DELETE_MESSAGE,
                RMConstant.NO_DATA_MESSAGE);
        return new ResponseEntity<>(successResponse,HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<SuccessResponse> searchMenus(@RequestParam String keyword) {
        SuccessResponse successResponseFound = new SuccessResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                RMConstant.GET_MENUS_SUCCESSFULLY,
                menuService.searchMenus(keyword));
        return new ResponseEntity<>(successResponseFound,HttpStatus.OK);
    }
}