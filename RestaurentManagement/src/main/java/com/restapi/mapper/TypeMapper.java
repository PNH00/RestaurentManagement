package com.restapi.mapper;

import com.restapi.dto.TypeDTO;
import com.restapi.models.Type;
import java.util.ArrayList;
import java.util.List;

public class TypeMapper {
    public static TypeDTO typeToTypeDTOMapper(Type type){
        return new TypeDTO(type.getId(),type.getType());
    }

    public static List<TypeDTO> typeToTypeDTOMapper(List<Type> types){
        List<TypeDTO> typeDTOs = new ArrayList<>();
        for (Type type:types) {
            typeDTOs.add(typeToTypeDTOMapper(type));
        }
        return typeDTOs;
    }
}
