package com.restapi.service;

import com.restapi.exceptions.ErrorDetail;
import com.restapi.exceptions.RMValidateException;
import com.restapi.exceptions.SuccessDetail;
import com.restapi.models.Menu;
import com.restapi.models.Type;
import com.restapi.repositories.MenuRepository;
import com.restapi.repositories.TypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.*;

@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final TypeRepository typeRepository;

    @Autowired
    public MenuService(MenuRepository menuRepository, TypeRepository typeRepository) {
        this.menuRepository = menuRepository;
        this.typeRepository = typeRepository;
    }

    public Object createMenu(Menu menu) {
        if (menu.getType().isEmpty()) {
            throw new RMValidateException(new ErrorDetail(
                    new Date().toString(),
                    HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST.getReasonPhrase(),
                    "Cannot create!" ));
        }
        List<Type> types = menu.getType();
        List<Type> existingTypes = new ArrayList<>();
        for (Type type : types) {
            if (type.getId() != null && typeRepository.existsById(type.getId())) {
                existingTypes.add(type);
            } else {
                Type newType = typeRepository.save(type);
                existingTypes.add(newType);
            }
        }
        menu.setType(existingTypes);
        return new SuccessDetail(HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                "Create success!",
                menuRepository.save(menu));
    }

    public List<Menu> getAllMenusPaged(int page, int size, String sortBy) {
        if (page <=0)
            page = 1;
        int totalMenus = (int) menuRepository.count();
        int totalPages = (int) Math.ceil((double) totalMenus / size);
        if (page > totalPages) {
            page = totalPages;
        }
        System.out.println(totalMenus);
        System.out.println(totalPages);
        Pageable pageable = PageRequest.of(page-1, size, Sort.by(Sort.Order.desc(sortBy)));
        Page<Menu> pagedResult = menuRepository.findAll(pageable);
        if (pagedResult.hasContent())
            return pagedResult.getContent();
        else
            return new ArrayList<Menu>();
    }

    public Object getMenuById(UUID id){
        if (!menuRepository.existsById(id))
            throw new RMValidateException(new ErrorDetail(
                    new Date().toString(),
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    "Please check the id!" ));
        return new SuccessDetail(HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                "Get the menu successfully!",
                menuRepository.findById(id));
    }

    public Object updateMenu(UUID id, Menu menu) {
        if(!menuRepository.existsById(id))
            throw new RMValidateException(new ErrorDetail(
                    new Date().toString(),
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    "Please check the id!" ));
        else {
            if (menu.getType().isEmpty()) {
                throw new RMValidateException(new ErrorDetail(
                        new Date().toString(),
                        HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        "Cannot update!" ));
            }
            menu.setId(id);
            typeRepository.saveAll(menu.getType());
            return new SuccessDetail(HttpStatus.OK.value(),
                    HttpStatus.OK.getReasonPhrase(),
                    "Update success!",
                    menuRepository.save(menu));
        }
    }

    public Object deleteMenu(UUID id) {
        SuccessDetail successDetail = new SuccessDetail(HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase()
                ,"Delete success!",
                null);
        if(menuRepository.existsById(id)){
            Menu menu = menuRepository.findById(id).get();
            successDetail.setData(menu.toString());
            menuRepository.deleteById(id);
        }
        else{
            throw new RMValidateException(new ErrorDetail(
                    new Date().toString(),
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    "Please check the id!" ));
        }
        return successDetail;
    }
}