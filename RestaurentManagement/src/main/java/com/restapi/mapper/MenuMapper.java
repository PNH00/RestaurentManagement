package com.restapi.mapper;

import com.restapi.dto.MenuDTO;
import com.restapi.models.Menu;
import java.util.ArrayList;
import java.util.List;

public class MenuMapper {
    public static MenuDTO menuToMenuDTOMapper(Menu menu){
        return new MenuDTO(
                menu.getId(),
                menu.getName(),
                menu.getDescription(),
                menu.getImage(),
                menu.getPrice(),
                TypeMapper.typeToTypeDTOMapper(menu.getTypes()));
    }

    public static List<MenuDTO> menuToMenuDTOMapper(List<Menu> menus){
        List<MenuDTO> menuDTOs = new ArrayList<>();
        for (Menu menu: menus) {
            menuDTOs.add(menuToMenuDTOMapper(menu));
        }
        return menuDTOs;
    }

    public static Menu menuDTOToMenuMapper(MenuDTO menuDTO){
        Menu menu = new Menu();
        menu.setName(menuDTO.getName());
        menu.setImage(menuDTO.getImage());
        menu.setPrice(menuDTO.getPrice());
        menu.setDescription(menuDTO.getDescription());
        return menu;
    }
}
