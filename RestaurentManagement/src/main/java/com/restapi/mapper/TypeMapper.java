package com.restapi.mapper;

import com.restapi.dto.TypeDTO;
import com.restapi.models.Type;
import java.util.ArrayList;
import java.util.List;

public class TypeMapper {
    public static TypeDTO typesToTypeDTOsMapper(Type type){
        return new TypeDTO(type.getId(),type.getType());
    }

    public static List<TypeDTO> typesToTypeDTOsMapper(List<Type> types){
        List<TypeDTO> typeDTOs = new ArrayList<>();
        for (Type type:types) {
            typeDTOs.add(typesToTypeDTOsMapper(type));
        }
        return typeDTOs;
    }
}
