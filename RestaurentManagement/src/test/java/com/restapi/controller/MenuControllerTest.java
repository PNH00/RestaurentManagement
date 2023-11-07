package com.restapi.controller;

import com.restapi.controllers.MenuController;
import com.restapi.dto.MenuDTO;
import com.restapi.dto.TypeDTO;
import com.restapi.exceptions.RMValidateException;
import com.restapi.mapper.MenuMapper;
import com.restapi.response.SuccessResponse;
import com.restapi.services.MenuService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MenuControllerTest {

    @Mock
    private MenuService menuService;

    @InjectMocks
    private MenuController menuController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllMenusWhenSuccessShouldReturnSuccessResponse() {
        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setCode(HttpStatus.OK.value());
        successResponse.setData(new ArrayList<>());
        successResponse.setMessage("Get menus successfully!");
        successResponse.setStatus(HttpStatus.OK.getReasonPhrase());
        assertEquals(successResponse,menuController.getAllMenusPaged(1,10,"id","asc").getBody());
        verify(menuService,times(1)).getAllMenusPaged(1,10,"id","asc");
    }

    @Test
    void getMenuByIdWhenMenuNotFoundThrowErrorResponse() {
        UUID id = UUID.randomUUID();
        when(menuService.getMenuById(id)).thenThrow(RMValidateException.class);
        assertThrows(RMValidateException.class,()->menuController.getMenuById(id));
        verify(menuService,times(1)).getMenuById(id);
    }

    @Test
    void getMenuByIdWhenMenuFoundReturnSuccessResponseHasMenuDTO() {
        UUID id = UUID.randomUUID();
        MenuDTO menuDTO  = new MenuDTO("Menu 4", "ice cream",
                "URL", 10.99,
                List.of(new TypeDTO("apple"),new TypeDTO("orange")));
        when(menuService.getMenuById(id)).thenReturn(menuDTO);
        assertEquals(menuDTO, Objects.requireNonNull(menuController.getMenuById(id).getBody()).getData());
        verify(menuService,times(1)).getMenuById(id);
    }

    @Test
    void createMenuWhenSuccessShouldReturnSuccessResponseHasMenuDTO() {
        MenuDTO menuDTO  = new MenuDTO("Menu 4", "ice cream",
                "URL", 10.99,
                 List.of(new TypeDTO("apple"),new TypeDTO("orange")));
        when(menuService.createMenu(menuDTO)).thenReturn(menuDTO);
        assertEquals(menuDTO, Objects.requireNonNull(menuController.createMenu(menuDTO).getBody()).getData());
        verify(menuService,times(1)).createMenu(menuDTO);
    }

    @Test
    void createMenuWhenMenuHasNoTypeShouldThrowErrorResponse() {
        MenuDTO menuDTO  = new MenuDTO("Menu 4", "ice cream",
                "URL", 10.99,
                new ArrayList<>());
        when(menuService.createMenu(menuDTO)).thenThrow(RMValidateException.class);
        assertThrows(RMValidateException.class, ()->menuController.createMenu(menuDTO));
        verify(menuService,times(1)).createMenu(menuDTO);
    }

    @Test
    void createMenuWhenMenuHasPriceLessThanZeroShouldThrowErrorResponse() {
        MenuDTO menuDTO  = new MenuDTO("Menu 4", "ice cream",
                "URL", -1,
                List.of(new TypeDTO("apple"),new TypeDTO("orange")));
        when(menuService.createMenu(menuDTO)).thenThrow(RMValidateException.class);
        assertThrows(RMValidateException.class,() -> menuController.createMenu(menuDTO));
        verify(menuService,times(1)).createMenu(menuDTO);
    }

    @Test
    void createMenuWhenMenuNameHadExistShouldThrowErrorResponse() {
        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setName("ExistingMenuName");
        MenuDTO existingMenu = new MenuDTO();
        existingMenu.setName("ExistingMenuName");
        when(menuService.createMenu(existingMenu)).thenReturn(existingMenu);
        when(menuService.searchMenuByName(menuDTO.getName())).thenReturn(MenuMapper.menuDTOToMenuMapper(menuDTO));
        when(menuService.createMenu(menuDTO)).thenThrow(RMValidateException.class);
        assertThrows(RMValidateException.class,() -> menuController.createMenu(menuDTO));
        verify(menuService,times(1)).createMenu(menuDTO);
    }

    @Test
    void updateMenuWhenMenuNotFoundShouldThrowErrorResponse() {
        UUID uuid = UUID.randomUUID();
        MenuDTO menuDTO = new MenuDTO();
        when(menuService.updateMenu(uuid,menuDTO)).thenThrow(RMValidateException.class);
        assertThrows(RMValidateException.class,()->menuController.updateMenu(uuid,menuDTO));
        verify(menuService,times(1)).updateMenu(uuid,menuDTO);
    }

    @Test
    void updateMenuWhenMenuFoundShouldReturnSuccessResponse() {
        UUID uuid = UUID.randomUUID();
        MenuDTO menuDTO  = new MenuDTO("Menu 4", "ice cream",
                "URL", 10.99,
                List.of(new TypeDTO("apple"),new TypeDTO("orange")));
        when(menuService.updateMenu(uuid,menuDTO)).thenReturn(menuDTO);
        assertEquals(SuccessResponse.class, Objects.requireNonNull(menuController.updateMenu(uuid, menuDTO).getBody()).getClass());
        verify(menuService,times(1)).updateMenu(uuid,menuDTO);
    }

    @Test
    void updateMenuWhenMenuFoundShouldReturnSuccessResponseHasMenuDTO() {
        UUID uuid = UUID.randomUUID();
        MenuDTO menuDTO = new MenuDTO();
        when(menuService.updateMenu(uuid,menuDTO)).thenReturn(menuDTO);
        assertEquals(menuDTO, Objects.requireNonNull(menuController.updateMenu(uuid, menuDTO).getBody()).getData());
        verify(menuService,times(1)).updateMenu(uuid,menuDTO);
    }

    @Test
    void updateMenuWhenMenuHasNoTypeShouldThrowErrorResponse() {
        UUID id = UUID.randomUUID();
        MenuDTO menuDTO  = new MenuDTO("Menu 4", "ice cream",
                "URL", 10.99,
                new ArrayList<>());
        when(menuService.updateMenu(id,menuDTO)).thenThrow(RMValidateException.class);
        assertThrows(RMValidateException.class, ()->menuController.updateMenu(id,menuDTO));
        verify(menuService,times(1)).updateMenu(id,menuDTO);
    }

    @Test
    void updateMenuWhenMenuHasPriceLessThanZeroShouldThrowErrorResponse() {
        UUID id = UUID.randomUUID();
        MenuDTO menuDTO  = new MenuDTO("Menu 4", "ice cream",
                "URL", -1,
                List.of(new TypeDTO("apple"),new TypeDTO("orange")));
        when(menuService.updateMenu(id,menuDTO)).thenThrow(RMValidateException.class);
        assertThrows(RMValidateException.class,() -> menuController.updateMenu(id,menuDTO));
        verify(menuService,times(1)).updateMenu(id,menuDTO);
    }

    @Test
    void updateMenuWhenMenuNameHadExistShouldThrowErrorResponse() {
        UUID id = UUID.randomUUID();
        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setName("ExistingMenuName");
        MenuDTO existingMenu = new MenuDTO();
        existingMenu.setName("ExistingMenuName");
        when(menuService.createMenu(existingMenu)).thenReturn(existingMenu);
        when(menuService.searchMenuByName(menuDTO.getName())).thenReturn(MenuMapper.menuDTOToMenuMapper(menuDTO));
        when(menuService.updateMenu(id,menuDTO)).thenThrow(RMValidateException.class);
        assertThrows(RMValidateException.class,() -> menuController.updateMenu(id,menuDTO));
        verify(menuService,times(1)).updateMenu(id,menuDTO);
    }

    @Test
    void deleteMenuWhenMenuNotFoundShouldThrowErrorResponse() {
        UUID uuid = UUID.randomUUID();
        doThrow(RMValidateException.class).when(menuService).deleteMenu(uuid);
        assertThrows(RMValidateException.class,()->menuController.deleteMenu(uuid));
        verify(menuService,times(1)).deleteMenu(uuid);
    }

    @Test
    void deleteMenuWhenMenuFoundShouldReturnSuccessResponse() {
        UUID uuid = UUID.randomUUID();
        doNothing().when(menuService).deleteMenu(uuid);
        assertEquals(SuccessResponse.class, Objects.requireNonNull(menuController.deleteMenu(uuid).getBody()).getClass());
        verify(menuService,times(1)).deleteMenu(uuid);
    }


    @Test
    void searchMenusWhenMenusFoundShouldReturnSuccessResponse(){
        String keyword = "menu 1";
        when(menuService.searchMenus(keyword)).thenReturn(List.of(new MenuDTO()));
        assertEquals(SuccessResponse.class, Objects.requireNonNull(menuController.searchMenus(keyword).getBody()).getClass());
        verify(menuService,times(1)).searchMenus(keyword);
    }

    @Test
    void searchMenusWhenMenusNotFoundShouldReturnSuccessResponse(){
        String keyword = "menu 1";
        when(menuService.searchMenus(keyword)).thenThrow(RMValidateException.class);
        assertThrows(RMValidateException.class, ()->menuController.searchMenus(keyword));
        verify(menuService,times(1)).searchMenus(keyword);
    }

    @Test
    void searchMenuByNameShouldThrowExceptionWhenNameIsNull() {
        String keyword = null;
        when(menuService.searchMenus(keyword)).thenThrow(RMValidateException.class);
        assertThrows(RMValidateException.class, () -> menuController.searchMenus(keyword));
        verify(menuService,times(1)).searchMenus(keyword);
    }
}