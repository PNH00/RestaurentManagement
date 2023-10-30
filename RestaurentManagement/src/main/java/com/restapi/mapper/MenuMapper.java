package com.restapi.mapper;

import com.restapi.dto.MenuDTO;
import com.restapi.models.Menu;
import com.restapi.models.Type;

public class MenuMapper {
    public static MenuDTO menuToMenuDTOMapper(Menu menu){
        return new MenuDTO(
                menu.getName(),
                menu.getDescription(),
                menu.getImage(),
                menu.getPrice(),
                TypeMapper.typeToTypeDTOMapper(menu.getType()));
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
