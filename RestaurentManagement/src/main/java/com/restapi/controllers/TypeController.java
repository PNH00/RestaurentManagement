package com.restapi.controllers;

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
        return type.isPresent() ?
                new ResponseEntity<>(type.get(),HttpStatus.OK):
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public Type createType(@RequestBody Type type) {
        return typeService.createType(type);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateType(@PathVariable UUID id, @RequestBody Type type) {
        return new ResponseEntity<>(typeService.updateType(id,type),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteType(@PathVariable UUID id) {
        typeService.deleteType(id);
        return new ResponseEntity<>("Delete success!", HttpStatus.OK);
    }
}