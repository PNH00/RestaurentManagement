package com.restapi.service;

import com.restapi.exceptions.ErrorDetail;
import com.restapi.exceptions.ErrorDetailHandler;
import com.restapi.exceptions.GlobalExceptionHandler;
import com.restapi.exceptions.RMValidateException;
import com.restapi.models.Type;
import com.restapi.repositories.TypeRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TypeService {

    private final TypeRepository typeRepository;

    @Autowired
    public TypeService(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    public List<Type> getAllTypes() {
        return typeRepository.findAll();
    }

    public Optional<Type> getTypeById(UUID id) {
        return typeRepository.findById(id);
    }

    public Type createType(Type type) {
        return typeRepository.save(type);
    }

    public Type updateType(UUID id, Type type) {
        if (typeRepository.existsById(id)) {
            type.setId(id);
            return typeRepository.save(type);
        }
        throw new RMValidateException(new ErrorDetail(new Date().toString(),"Not found", HttpStatus.NOT_FOUND.value(),"Please check the id!" ));
    }

    public void deleteType(UUID id) {
        if (!typeRepository.existsById(id)) {

            throw new RMValidateException(new ErrorDetail(new Date().toString(),"Not found", HttpStatus.NOT_FOUND.value(),"Please check the id!" ));
        }
        typeRepository.deleteById(id);
    }
}