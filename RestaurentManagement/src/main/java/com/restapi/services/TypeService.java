package com.restapi.services;

import com.restapi.dto.TypeDTO;
import com.restapi.exceptions.ErrorDetail;
import com.restapi.exceptions.RMValidateException;
import com.restapi.mapper.TypeMapper;
import com.restapi.models.Type;
import com.restapi.repositories.TypeRepository;
import com.restapi.constants.RMConstant;
import com.restapi.utils.RMUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class TypeService {

    private final TypeRepository typeRepository;

    @Autowired
    public TypeService(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

    public List<TypeDTO> getAllTypes() {
        List<TypeDTO> typeDTOs = new ArrayList<TypeDTO>();
        for (Type type: typeRepository.findAll()) {
            typeDTOs.add(TypeMapper.typeMapper(type));
        }
        return typeDTOs;
    }

    public TypeDTO getTypeById(UUID id) {
        if (typeRepository.findById(id).isEmpty())
            throw new RMValidateException(new ErrorDetail(
                    new Date().toString(),
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    RMConstant.TYPE_NOT_FOUND));
        return TypeMapper.typeMapper(typeRepository.findById(id).get());
    }

    public TypeDTO createType(Type type) {
        return TypeMapper.typeMapper(typeRepository.save(type));
    }

    public TypeDTO updateType(UUID id, Type type) {
        if (typeRepository.existsById(id)) {
            type.setId(id);
            Type typeUpdated = typeRepository.save(type);
            return TypeMapper.typeMapper(typeUpdated);
        }
        throw new RMValidateException(new ErrorDetail(
                new Date().toString(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                RMConstant.TYPE_NOT_FOUND));
    }

    public void deleteType(UUID id){
        if (!typeRepository.existsById(id))
            throw new RMValidateException(new ErrorDetail(
                    new Date().toString(),
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    RMConstant.TYPE_NOT_FOUND));
        try {
            typeRepository.deleteById(id);
        }catch (Exception e){
            throw new RMValidateException(new ErrorDetail(
                    new Date().toString(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                    RMConstant.INTERNAL_SERVER_ERROR));
        }
    }
}