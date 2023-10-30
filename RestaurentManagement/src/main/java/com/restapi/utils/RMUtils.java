package com.restapi.utils;

<<<<<<< HEAD
import com.restapi.dto.MenuDTO;
import com.restapi.dto.TypeDTO;
import com.restapi.models.Menu;
import com.restapi.models.Type;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import java.util.ArrayList;
import java.util.List;
=======
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
>>>>>>> 391ec39174d202c8b9d04559ed7dd8a39d05c4a5

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
<<<<<<< HEAD

    public static TypeDTO typeMapper(Type type){
        return new TypeDTO(type.getType());
    }
    public static MenuDTO menuMapper(Menu menu){
        MenuDTO menuDTO = new MenuDTO();
        menuDTO.setName(menu.getName());
        menuDTO.setDescription(menu.getDescription());
        menuDTO.setImage(menu.getImage());
        menuDTO.setPrice(menu.getPrice());

        List<TypeDTO> typeDTOs = new ArrayList<TypeDTO>();
        for (Type type : menu.getType()) {
            typeDTOs.add(new TypeDTO(type.getType()));
        }
        menuDTO.setType(typeDTOs);
        return menuDTO;
    }
=======
>>>>>>> 391ec39174d202c8b9d04559ed7dd8a39d05c4a5
}