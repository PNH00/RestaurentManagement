package com.restapi.mapper;

import com.restapi.dto.TypeDTO;
import com.restapi.models.Type;

import java.util.ArrayList;
import java.util.List;

public class TypeMapper {
    public static TypeDTO typeMapper(Type type){
        return new TypeDTO(type.getType());
    }

    public static List<TypeDTO> typeMapper(List<Type> types){
        List<TypeDTO> typeDTOs = new ArrayList<TypeDTO>();
        for (Type type:types) {
            typeDTOs.add(typeMapper(type));
        }
        return typeDTOs;
    }
}
