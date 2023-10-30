package com.restapi.controllers;

<<<<<<< HEAD
import com.restapi.dto.TypeDTO;
import com.restapi.exceptions.SuccessfulResponse;
=======
>>>>>>> 391ec39174d202c8b9d04559ed7dd8a39d05c4a5
import com.restapi.models.Type;
import com.restapi.services.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
<<<<<<< HEAD
=======
import java.util.Optional;
>>>>>>> 391ec39174d202c8b9d04559ed7dd8a39d05c4a5
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
<<<<<<< HEAD
    public ResponseEntity<List<TypeDTO>> getAllTypes() {
        List<TypeDTO> types = typeService.getAllTypes();
=======
    public ResponseEntity<List<Type>> getAllTypes() {
        List<Type> types = typeService.getAllTypes();
>>>>>>> 391ec39174d202c8b9d04559ed7dd8a39d05c4a5
        return new ResponseEntity<>(types, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTypeById(@PathVariable UUID id) {
<<<<<<< HEAD
        SuccessfulResponse type = typeService.getTypeById(id);
        return new ResponseEntity<>(type,HttpStatus.OK);
    }

    @PostMapping
    public SuccessfulResponse createType(@RequestBody Type type) {
=======
        Optional<Type> type = typeService.getTypeById(id);
        return type.isPresent() ?
                new ResponseEntity<>(type.get(),HttpStatus.OK):
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping
    public Type createType(@RequestBody Type type) {
>>>>>>> 391ec39174d202c8b9d04559ed7dd8a39d05c4a5
        return typeService.createType(type);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateType(@PathVariable UUID id, @RequestBody Type type) {
        return new ResponseEntity<>(typeService.updateType(id,type),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteType(@PathVariable UUID id) {
<<<<<<< HEAD
        return new ResponseEntity<>(typeService.deleteType(id), HttpStatus.OK);
=======
        typeService.deleteType(id);
        return new ResponseEntity<>("Delete success!", HttpStatus.OK);
>>>>>>> 391ec39174d202c8b9d04559ed7dd8a39d05c4a5
    }
}