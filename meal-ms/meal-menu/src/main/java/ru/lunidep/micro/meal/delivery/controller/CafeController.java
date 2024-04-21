package ru.lunidep.micro.meal.menu.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/cafes")
public class CafeController {
    @Autowired
    private CafeService cafeService;

    @GetMapping
    public ResponseEntity<List<Cafe>> getAllCafes() {
        List<Cafe> cafes = cafeService.getAllCafes();
        return new ResponseEntity<>(cafes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cafe> getCafeById(@PathVariable UUID id) {
        Cafe cafe = cafeService.getCafeById(id);
        if (cafe != null) {
            return new ResponseEntity<>(cafe, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Cafe> createCafe(@RequestBody Cafe cafe) {
        Cafe createdCafe = cafeService.createCafe(cafe);
        return new ResponseEntity<>(createdCafe, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cafe> updateCafe(@PathVariable UUID id, @RequestBody Cafe cafe) {
        Cafe updatedCafe = cafeService.updateCafe(id, cafe);
        return new ResponseEntity<>(updatedCafe, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCafe(@PathVariable UUID id) {
        cafeService.deleteCafe(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
