package com.restapi.testservice;

import com.restapi.dto.MenuDTO;
import com.restapi.dto.TypeDTO;
import com.restapi.exceptions.RMValidateException;
import com.restapi.models.Menu;
import com.restapi.models.Type;
import com.restapi.repositories.MenuRepository;
import com.restapi.services.MenuService;
import com.restapi.services.TypeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
class MenuServiceTest {

    @Mock
    private MenuRepository menuRepository;

    @Mock
    private TypeService typeService;

    @InjectMocks
    private MenuService menuService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createMenuWhenTypeIsEmptyShouldThrowException() {
        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setType(Collections.emptyList());
        assertThrows(RMValidateException.class, () -> menuService.createMenu(menuDTO));
    }

    @Test
    void createMenuWhenPriceIsNegativeShouldThrowException() {
        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setType(List.of(new TypeDTO("Type1")));
        menuDTO.setPrice(-10);
        assertThrows(RMValidateException.class, () -> menuService.createMenu(menuDTO));
    }

    @Test
    void createMenuWhenMenuNameExistsShouldThrowException() {
        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setType(List.of(new TypeDTO("Type1")));
        menuDTO.setName("ExistingMenu");

        when(menuRepository.findByNameEquals(menuDTO.getName())).thenReturn(Collections.singletonList(new Menu()));
        assertThrows(RMValidateException.class, () -> menuService.createMenu(menuDTO));
    }

    @Test
    void getMenuByIdWhenMenuNotFoundShouldThrowException() {
        UUID id = UUID.randomUUID();
        when(menuRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(RMValidateException.class, () -> menuService.getMenuById(id));
    }

    @Test
    void updateMenuWhenMenuNotFoundShouldThrowException() {
        UUID id = UUID.randomUUID();
        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setType(List.of(new TypeDTO("Type1")));
        when(menuRepository.existsById(id)).thenReturn(false);
        assertThrows(RMValidateException.class, () -> menuService.updateMenu(id, menuDTO));
    }

    @Test
    void updateMenuWhenMenuFoundShouldUpdateMenu() {
        UUID id = UUID.randomUUID();
        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setType(List.of(new TypeDTO("Type1")));
        menuDTO.setName("UpdatedMenu");

        when(menuRepository.existsById(id)).thenReturn(true);
        when(typeService.saveAllType(menuDTO.getType())).thenReturn(Collections.singletonList(new Type()));
        when(menuRepository.save(any(Menu.class))).thenReturn(new Menu());
        MenuDTO result = menuService.updateMenu(id, menuDTO);
        assertNotNull(result);
        assertEquals(menuDTO.getName(), result.getName());
    }

    @Test
    void deleteMenuWhenMenuNotFoundShouldThrowException() {
        UUID id = UUID.randomUUID();

        when(menuRepository.existsById(id)).thenReturn(false);
        assertThrows(RMValidateException.class, () -> menuService.deleteMenu(id));
    }

    @Test
    void deleteMenuWhenMenuFoundShouldDeleteMenu() {
        UUID id = UUID.randomUUID();

        when(menuRepository.existsById(id)).thenReturn(true);
        assertDoesNotThrow(() -> menuService.deleteMenu(id));
    }

    @Test
    void searchMenusWhenKeywordIsNullShouldThrowException() {
        String keyword = null;
        assertThrows(RMValidateException.class, () -> menuService.searchMenus(keyword));
    }

    @Test
    void searchMenuByNameWhenNameIsNullShouldThrowException() {
        String name = null;
        assertThrows(RMValidateException.class, () -> menuService.searchMenuByName(name));
    }

    @Test
    void searchMenuByNameWhenMenuFoundShouldReturnMenu() {
        String name = "TestMenu";
        Menu menu = new Menu();
        menu.setName(name);

        when(menuRepository.findByNameEquals(name)).thenReturn(Collections.singletonList(menu));
        Menu result = menuService.searchMenuByName(name);
        assertNotNull(result);
        assertEquals(name, result.getName());
    }
}
