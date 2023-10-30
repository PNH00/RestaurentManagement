package com.restapi.controllers;

<<<<<<< HEAD
import com.restapi.dto.MenuDTO;
import com.restapi.exceptions.SuccessfulResponse;
=======
>>>>>>> 391ec39174d202c8b9d04559ed7dd8a39d05c4a5
import com.restapi.models.Menu;
import com.restapi.services.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
@RequestMapping("/api/menus")
public class MenuController {

    private final MenuService menuService;

    @Autowired
    public MenuController(MenuService menuService) {
        this.menuService = menuService;
    }

    @PostMapping
<<<<<<< HEAD
    public SuccessfulResponse createMenu(@RequestBody Menu menu) {
=======
    public Menu createMenu(@RequestBody Menu menu) {
>>>>>>> 391ec39174d202c8b9d04559ed7dd8a39d05c4a5
        return menuService.createMenu(menu);
    }

    @GetMapping
<<<<<<< HEAD
    public ResponseEntity<List<MenuDTO>> getAllMenusPaged(
=======
    public ResponseEntity<List<Menu>> getAllMenusPaged(
>>>>>>> 391ec39174d202c8b9d04559ed7dd8a39d05c4a5
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "desc") String order)
    {
<<<<<<< HEAD
        List<MenuDTO> list = menuService.getAllMenusPaged(page, size, sortBy,order);
=======
        List<Menu> list = menuService.getAllMenusPaged(page, size, sortBy,order);
>>>>>>> 391ec39174d202c8b9d04559ed7dd8a39d05c4a5
        return new ResponseEntity<>(list,new HttpHeaders(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getMenuById(@PathVariable UUID id) {
<<<<<<< HEAD
        return new ResponseEntity<>(menuService.getMenuById(id),HttpStatus.OK);

=======
        Optional<Menu> menu = menuService.getMenuById(id);
        return menu.isPresent() ?
                new ResponseEntity<>(menu.get(),HttpStatus.OK):
                new ResponseEntity<>(HttpStatus.NOT_FOUND);
>>>>>>> 391ec39174d202c8b9d04559ed7dd8a39d05c4a5
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateMenu(@PathVariable UUID id, @RequestBody Menu menu) {
        return new  ResponseEntity<>(menuService.updateMenu(id, menu),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteMenu(@PathVariable UUID id) {
<<<<<<< HEAD
        return new ResponseEntity<>(menuService.deleteMenu(id), HttpStatus.OK);
=======
        menuService.deleteMenu(id);
        return new ResponseEntity<>("Delete success!", HttpStatus.OK);
>>>>>>> 391ec39174d202c8b9d04559ed7dd8a39d05c4a5
    }
}