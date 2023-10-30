package com.restapi.mapper;

import com.restapi.dto.MenuDTO;
import com.restapi.models.Menu;

public class MenuMapper {
    public static MenuDTO menuToMenuDTOMapper(Menu menu){
        return new MenuDTO(
                menu.getName(),
                menu.getDescription(),
                menu.getImage(),
                menu.getPrice(),
                TypeMapper.typeToTypeDTOMapper(menu.getType()));
    }
    public static Menu menuDTOToMenuMapper(Menu menu){
        return new Menu();
    }
}
