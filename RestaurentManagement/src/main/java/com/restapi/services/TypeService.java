package com.restapi.services;

<<<<<<< HEAD
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
=======
import com.restapi.exceptions.ErrorDetail;
import com.restapi.exceptions.RMValidateException;
import com.restapi.models.Type;
import com.restapi.repositories.TypeRepository;
import com.restapi.constants.RMConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
>>>>>>> 391ec39174d202c8b9d04559ed7dd8a39d05c4a5

@Service
public class TypeService {

    private final TypeRepository typeRepository;

    @Autowired
    public TypeService(TypeRepository typeRepository) {
        this.typeRepository = typeRepository;
    }

<<<<<<< HEAD
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
=======
    public List<Type> getAllTypes() {
        return typeRepository.findAll();
    }

    public Optional<Type> getTypeById(UUID id) {
        if (!typeRepository.existsById(id))
            throw new RMValidateException(new ErrorDetail(
                    new Date().toString(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    HttpStatus.NOT_FOUND.value(),
                    RMConstant.TYPE_NOT_FOUND));
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
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                HttpStatus.NOT_FOUND.value(),
                RMConstant.TYPE_NOT_FOUND));
    }

    public void deleteType(UUID id){
        if (!typeRepository.existsById(id))
            throw new RMValidateException(new ErrorDetail(
                    new Date().toString(),
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    HttpStatus.NOT_FOUND.value(),
                    RMConstant.TYPE_NOT_FOUND));
        try {
            typeRepository.deleteById(id);
        }catch (Exception e){
            throw new RMValidateException(new ErrorDetail(
                    new Date().toString(),
                    HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(),
                    HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    RMConstant.TYPE_INTERNAL_SERVER_ERROR));
>>>>>>> 391ec39174d202c8b9d04559ed7dd8a39d05c4a5
        }
    }
}