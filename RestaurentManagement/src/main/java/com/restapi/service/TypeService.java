package com.restapi.service;

import com.restapi.exceptions.ErrorDetail;
import com.restapi.exceptions.ErrorDetailHandler;
import com.restapi.exceptions.ResourceException;
import com.restapi.models.Type;
import com.restapi.repositories.TypeRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TypeService {

    private final TypeRepository typeRepository;
    @Getter
    private final ErrorDetailHandler errorDetailHandler = new ErrorDetailHandler();

    @Autowired
    public TypeService(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    public List<Type> getAllTypes() {
        return typeRepository.findAll();
    }

    public Optional<Type> getTypeById(UUID id) {
        if (!typeRepository.existsById(id)) {
            ErrorDetail errorDetail = errorDetailHandler.createNotFoundErrorDetail(id);
            throw new ResourceException(errorDetail);
        }
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
        ErrorDetail errorDetail = errorDetailHandler.createNotFoundErrorDetail(id);
        throw new ResourceException(errorDetail);
    }

    public void deleteType(UUID id) {
        if (!typeRepository.existsById(id)) {
            ErrorDetail errorDetail = errorDetailHandler.createNotFoundErrorDetail(id);
            throw new ResourceException(errorDetail);
        }
        typeRepository.deleteById(id);
    }
}