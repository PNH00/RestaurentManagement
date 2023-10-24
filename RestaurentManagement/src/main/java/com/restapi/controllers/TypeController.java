package com.restapi.controllers;

import com.restapi.exceptions.ErrorDetail;
import com.restapi.exceptions.RMValidateException;
import com.restapi.models.Menu;
import com.restapi.models.Type;
import com.restapi.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;
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
    public ResponseEntity<List<Type>> getAllTypes() {
        List<Type> types = typeService.getAllTypes();
        return new ResponseEntity<>(types, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTypeById(@PathVariable UUID id) {
        Optional<Type> type = typeService.getTypeById(id);
        return type.map(value -> new ResponseEntity<>(value, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping
    public ResponseEntity<Type> createType(@RequestBody Type type) {
        Type createdType = typeService.createType(type);
        return new ResponseEntity<>(createdType, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateType(@PathVariable UUID id, @RequestBody Type type) {
        try {
            Type updatedType = typeService.updateType(id, type);
            return new ResponseEntity<>(updatedType, HttpStatus.OK);
        } catch (RMValidateException e) {
            ErrorDetail errorDetail = e.getErrorDetail();
            return new ResponseEntity<>(errorDetail, HttpStatus.valueOf(errorDetail.getCode()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ErrorDetail> deleteType(@PathVariable UUID id) {
        try {
            typeService.deleteType(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RMValidateException e) {
            return new ResponseEntity<>(e.getErrorDetail(), HttpStatus.valueOf(e.getErrorDetail().getCode()));
        }
    }
}