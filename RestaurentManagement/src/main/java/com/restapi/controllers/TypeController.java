package com.restapi.controllers;

import com.restapi.dto.TypeDTO;
import com.restapi.exceptions.SuccessfulResponse;
import com.restapi.models.Type;
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
    public ResponseEntity<List<TypeDTO>> getAllTypes() {
        List<TypeDTO> types = typeService.getAllTypes();
        return new ResponseEntity<>(types, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTypeById(@PathVariable UUID id) {
        SuccessfulResponse type = typeService.getTypeById(id);
        return new ResponseEntity<>(type,HttpStatus.OK);
    }

    @PostMapping
    public SuccessfulResponse createType(@RequestBody Type type) {
        return typeService.createType(type);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateType(@PathVariable UUID id, @RequestBody Type type) {
        return new ResponseEntity<>(typeService.updateType(id,type),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteType(@PathVariable UUID id) {
        return new ResponseEntity<>(typeService.deleteType(id), HttpStatus.OK);
    }
}