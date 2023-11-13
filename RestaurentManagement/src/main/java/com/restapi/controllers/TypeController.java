package com.restapi.controllers;

import com.restapi.constants.RMConstant;
import com.restapi.dto.TypeDTO;
import com.restapi.dto.SuccessResponse;
import com.restapi.services.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/types")
public class TypeController {

    private final TypeService typeService;

    @Autowired
    public TypeController(TypeService typeService) {
        this.typeService = typeService;
    }

    @GetMapping
    public ResponseEntity<SuccessResponse> getAllTypes() {
        List<TypeDTO> types = typeService.getAllTypes();
        SuccessResponse  successResponse = new SuccessResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                RMConstant.GET_TYPES_SUCCESSFULLY,
                types);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse> getTypeById(@PathVariable UUID id) {
        SuccessResponse  successResponse = new SuccessResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                RMConstant.GET_TYPE_SUCCESSFULLY,
                typeService.getTypeById(id));
        return new ResponseEntity<>(successResponse,HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<SuccessResponse> createType(@RequestBody TypeDTO type) {
        SuccessResponse  successResponse = new SuccessResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                RMConstant.CREATE_TYPE_SUCCESSFULLY,
                typeService.createType(type));
        return new ResponseEntity<>(successResponse,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse> updateType(@PathVariable UUID id, @RequestBody TypeDTO type) {
        SuccessResponse successResponse = new SuccessResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                RMConstant.UPDATE_TYPE_SUCCESSFULLY,
                typeService.updateType(id,type));
        return new ResponseEntity<>(successResponse,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> deleteType(@PathVariable UUID id) {
        typeService.deleteType(id);
        SuccessResponse successResponse = new SuccessResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                RMConstant.DELETE_MESSAGE,
                RMConstant.NO_DATA_MESSAGE);
        return new ResponseEntity<>(successResponse,HttpStatus.OK);
    }
}