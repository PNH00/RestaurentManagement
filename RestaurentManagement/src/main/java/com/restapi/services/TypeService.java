package com.restapi.services;

import com.restapi.dto.TypeDTO;
import com.restapi.exceptions.ErrorDetail;
import com.restapi.exceptions.RMValidateException;
import com.restapi.exceptions.SuccessfulResponse;
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
        for (Type type : typeRepository.findAll()) {
            typeDTOs.add(new TypeDTO(type.getType()));
        }
        return typeDTOs;
    }

    public SuccessfulResponse getTypeById(UUID id) {
        if (typeRepository.findById(id).isEmpty())
            throw new RMValidateException(new ErrorDetail(
                    new Date().toString(),
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    RMConstant.TYPE_NOT_FOUND));
        return new SuccessfulResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                RMConstant.TYPE_OK_FOR_ID,
                new TypeDTO(typeRepository.findById(id).get().getType()));
    }

    public SuccessfulResponse createType(Type type) {
        Type typeCreated = typeRepository.save(type);
        TypeDTO typeDTO = new TypeDTO(typeCreated.getType());
        return new SuccessfulResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                RMConstant.TYPE_OK_FOR_CREATE,
                typeDTO);
    }

    public SuccessfulResponse updateType(UUID id, Type type) {
        if (typeRepository.existsById(id)) {
            type.setId(id);
            return new SuccessfulResponse(
                    HttpStatus.OK.value(),
                    HttpStatus.OK.getReasonPhrase(),
                    RMConstant.TYPE_OK_FOR_UPDATE,
                    new TypeDTO(typeRepository.save(type).getType()));
        }
        throw new RMValidateException(new ErrorDetail(
                new Date().toString(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                RMConstant.TYPE_NOT_FOUND));
    }

    public SuccessfulResponse deleteType(UUID id){
        if (typeRepository.findById(id).isEmpty())
            throw new RMValidateException(new ErrorDetail(
                    new Date().toString(),
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    RMConstant.TYPE_NOT_FOUND));
        try {
            TypeDTO typeDTO = RMUtils.typeMapper(typeRepository.findById(id).get());
            typeRepository.deleteById(id);
            return new SuccessfulResponse(
                    HttpStatus.OK.value(),
                    HttpStatus.OK.getReasonPhrase(),
                    RMConstant.TYPE_OK_FOR_DELETE,
                    typeDTO);
        }catch (Exception e){
            throw new RMValidateException(new ErrorDetail(
                    new Date().toString(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                    RMConstant.INTERNAL_SERVER_ERROR));
        }
    }
}