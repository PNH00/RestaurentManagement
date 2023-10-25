package com.restapi.service;

import com.restapi.exceptions.ErrorDetail;
import com.restapi.exceptions.RMValidateException;
import com.restapi.models.Type;
import com.restapi.repositories.TypeRepository;
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
        if (!typeRepository.existsById(id))
            throw new RMValidateException(new ErrorDetail(
                    new Date().toString(),
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    "Please check the id!" ));
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
        throw new RMValidateException(new ErrorDetail(
                new Date().toString(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                "Please check the id!" ));
    }

    public void deleteType(UUID id){
        if (!typeRepository.existsById(id))
            throw new RMValidateException(new ErrorDetail(
                    new Date().toString(),
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    "Please check the id!" ));
        try {
            typeRepository.deleteById(id);
        }catch (Exception e){
            throw new RMValidateException(new ErrorDetail(
                    new Date().toString(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                    "Cannot delete this type because this type exists in a menu. " +
                            "If you want to delete this type, " +
                            "please delete a menu contain this type first!" ));
        }
    }
}