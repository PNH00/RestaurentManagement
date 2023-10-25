package com.restapi.services;

import com.restapi.exceptions.ErrorDetail;
import com.restapi.exceptions.RMValidateException;
import com.restapi.models.Menu;
import com.restapi.models.Type;
import com.restapi.repositories.MenuRepository;
import com.restapi.repositories.TypeRepository;
import com.restapi.utils.RMUtils;
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

    public Menu createMenu(Menu menu) {
        if (menu.getType().isEmpty()) {
            throw new RMValidateException(new ErrorDetail(
                    new Date().toString(),
                    HttpStatus.BAD_REQUEST.getReasonPhrase(),
                    HttpStatus.BAD_REQUEST.value(),
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
        return menuRepository.save(menu);
    }

    public List<Menu> getAllMenusPaged(int page, int size, String sortBy) {
        int pageReadjust = RMUtils.setPage(page,size, (int) menuRepository.count());
        if(size >= 0)
            size = 10;
        Pageable pageable = PageRequest.of(pageReadjust, size, Sort.by(Sort.Order.desc(sortBy)));
        Page<Menu> pagedResult = menuRepository.findAll(pageable);
        if (pagedResult.hasContent())
            return pagedResult.getContent();
        else
            return new ArrayList<Menu>();
    }

    public Optional<Menu> getMenuById(UUID id){
        if (!menuRepository.existsById(id))
            throw new RMValidateException(new ErrorDetail(
                    new Date().toString(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    HttpStatus.NOT_FOUND.value(),
                    "Please check the id!" ));
        return menuRepository.findById(id);
    }

    public Menu updateMenu(UUID id, Menu menu) {
        if(!menuRepository.existsById(id))
            throw new RMValidateException(new ErrorDetail(
                    new Date().toString(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    HttpStatus.NOT_FOUND.value(),
                    "Please check the id!" ));
        else {
            if (menu.getType().isEmpty()) {
                throw new RMValidateException(new ErrorDetail(
                        new Date().toString(),
                        HttpStatus.BAD_REQUEST.getReasonPhrase(),
                        HttpStatus.BAD_REQUEST.value(),
                        "Cannot update!" ));
            }
            menu.setId(id);
            typeRepository.saveAll(menu.getType());
            return menuRepository.save(menu);
        }
    }

    public void deleteMenu(UUID id) {
        if(menuRepository.existsById(id))
            menuRepository.deleteById(id);
        else
            throw new RMValidateException(new ErrorDetail(
                    new Date().toString(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    HttpStatus.NOT_FOUND.value(),
                    "Please check the id!" ));
    }
}