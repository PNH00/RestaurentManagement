package com.restapi.services;

import com.restapi.dto.TypeDTO;
import com.restapi.dto.ErrorResponse;
import com.restapi.exceptions.RMValidateException;
import com.restapi.mapper.TypeMapper;
import com.restapi.models.Type;
import com.restapi.repositories.TypeRepository;
import com.restapi.constants.RMConstant;
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
            typeDTOs.add(TypeMapper.typeToTypeDTOMapper(type));
        }
        return typeDTOs;
    }

    public TypeDTO getTypeById(UUID id) {
        if (typeRepository.findById(id).isEmpty())
            throw new RMValidateException(new ErrorResponse(
                    new Date().toString(),
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    RMConstant.TYPE_NOT_FOUND));
        return TypeMapper.typeToTypeDTOMapper(typeRepository.findById(id).get());
    }

    public TypeDTO createType(TypeDTO type) {
        if (searchTypeByType(type.getType())!=null){
            throw new RMValidateException(new ErrorResponse(
                    new Date().toString(),
                    HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST.getReasonPhrase(),
                    RMConstant.NAME_EXISTED));
        }
        Type typeToCreate = new Type();
        typeToCreate.setType(type.getType());
        typeRepository.save(typeToCreate);
        return type;
    }

    public List<Type> saveAllType(List<TypeDTO> typeDTOs){
        List<Type> types = new ArrayList<Type>();
        for (TypeDTO typeDTO : typeDTOs) {
            if (searchTypeByType(typeDTO.getType())!=null){
                types.add(searchTypeByType(typeDTO.getType()));
            }else {
                Type type = new Type();
                type.setType(typeDTO.getType());
                types.add(type);
                typeRepository.save(type);
            }
        }
        return types;
    }

    public TypeDTO updateType(UUID id, TypeDTO type) {
        if (typeRepository.existsById(id)) {
            if (searchTypeByType(type.getType())!=null){
                Type typeCheck = searchTypeByType(type.getType());
                System.out.println(typeCheck);
                if(!typeCheck.getId().equals(id)){
                    throw new RMValidateException(new ErrorResponse(
                            new Date().toString(),
                            HttpStatus.BAD_REQUEST.value(),
                            HttpStatus.BAD_REQUEST.getReasonPhrase(),
                            RMConstant.NAME_EXISTED));
                }
            }
            Type typeToUpdate = new Type();
            typeToUpdate.setId(id);
            typeToUpdate.setType(type.getType());
            typeRepository.save(typeToUpdate);
            return type;
        }
        throw new RMValidateException(new ErrorResponse(
                new Date().toString(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                RMConstant.TYPE_NOT_FOUND));
    }

    public void deleteType(UUID id){
        if (!typeRepository.existsById(id))
            throw new RMValidateException(new ErrorResponse(
                    new Date().toString(),
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    RMConstant.TYPE_NOT_FOUND));
        try {
            typeRepository.deleteById(id);
        }catch (Exception e){
            throw new RMValidateException(new ErrorResponse(
                    new Date().toString(),
                    HttpStatus.BAD_REQUEST.value(),
                    HttpStatus.BAD_REQUEST.getReasonPhrase(),
                    RMConstant.SOME_THING_WRONG));
        }
    }
    public Type searchTypeByType(String type) {
        if (type==null){
            throw new RMValidateException(new ErrorResponse(
                    new Date().toString(),
                    HttpStatus.NOT_FOUND.value(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    RMConstant.TYPE_NOT_FOUND));
        }
        return typeRepository.findByTypeEquals(type);
    }
}