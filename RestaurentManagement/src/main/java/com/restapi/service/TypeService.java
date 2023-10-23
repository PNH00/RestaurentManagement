package com.restapi.service;

import com.restapi.exceptions.ErrorDetail;
import com.restapi.exceptions.ResourceException;
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

    public ErrorDetail createNotFoundErrorDetail(UUID id) {
        ErrorDetail errorDetail = new ErrorDetail();
        errorDetail.setTimestamp(new Date().toString());
        errorDetail.setStatus("Not found");
        errorDetail.setCode(HttpStatus.NOT_FOUND.value());
        errorDetail.setMessage("Check your id '" + id + "' and try again");
        errorDetail.setData(null);
        return errorDetail;
    }

    public List<Type> getAllTypes() {
        return typeRepository.findAll();
    }

    public Optional<Type> getTypeById(UUID id) {
        if (!typeRepository.existsById(id)) {
            ErrorDetail errorDetail = createNotFoundErrorDetail(id);
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
        ErrorDetail errorDetail = createNotFoundErrorDetail(id);
        throw new ResourceException(errorDetail);
    }

    public void deleteType(UUID id) {
        if (!typeRepository.existsById(id)) {
            ErrorDetail errorDetail = createNotFoundErrorDetail(id);
            throw new ResourceException(errorDetail);
        }
        typeRepository.deleteById(id);
    }
}
