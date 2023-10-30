package com.restapi.utils;

import com.restapi.dto.MenuDTO;
import com.restapi.dto.TypeDTO;
import com.restapi.models.Menu;
import com.restapi.models.Type;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

public class RMUtils {

    public static int setPage(int page,int size,int totalItems){
        int totalPages = (int) Math.ceil((double)  totalItems / size);
        int truePage = page;
        if (truePage > totalPages) {
            truePage = totalPages;
        }
        if (truePage <=0) {
            truePage = 1;
        }
        truePage = truePage - 1;
        return truePage;
    }

    public static int setSize(int size){
        int trueSize = size;
        if (size > 0){
            return trueSize;
        }else {
            trueSize = 10;
            return trueSize;
        }
    }

    public static Pageable sortOrder (int truePage, int trueSize, String sortBy, String order){
        if (order.equals("asc")){
            return PageRequest.of(truePage, trueSize, Sort.by(Sort.Order.asc(sortBy)));
        }else {
            return PageRequest.of(truePage, trueSize, Sort.by(Sort.Order.desc(sortBy)));
        }
    }

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

    public static MenuDTO menuMapper(Menu menu){
        return new MenuDTO(
                menu.getName(),
                menu.getDescription(),
                menu.getImage(),
                menu.getPrice(),
                typeMapper(menu.getType()));
    }
}