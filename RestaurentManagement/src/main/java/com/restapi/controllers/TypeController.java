package com.restapi.controllers;

import com.restapi.dto.TypeDTO;
import com.restapi.response.SuccessResponse;
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
                "Get type successfully!",
                types);
        return new ResponseEntity<>(successResponse, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SuccessResponse> getTypeById(@PathVariable UUID id) {
        SuccessResponse  successResponse = new SuccessResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                "Get type successfully!",
                typeService.getTypeById(id));
        return new ResponseEntity<>(successResponse,HttpStatus.OK);

    }

    @PostMapping
    public ResponseEntity<SuccessResponse> createType(@RequestBody TypeDTO type) {
        SuccessResponse  successResponse = new SuccessResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                "Create type successfully!",
                typeService.createType(type));
        return new ResponseEntity<>(successResponse,HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SuccessResponse> updateType(@PathVariable UUID id, @RequestBody TypeDTO type) {
        SuccessResponse successResponse = new SuccessResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                "Update type successfully!",
                typeService.updateType(id,type));
        return new ResponseEntity<>(successResponse,HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SuccessResponse> deleteType(@PathVariable UUID id) {
        typeService.deleteType(id);
        SuccessResponse successResponse = new SuccessResponse(
                HttpStatus.OK.value(),
                HttpStatus.OK.getReasonPhrase(),
                "Delete type successfully!",
                "No data response");
        return new ResponseEntity<>(successResponse,HttpStatus.OK);
    }
}