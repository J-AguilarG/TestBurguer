package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/burgers")
public class BurgerController {

    @Autowired
    private BurgerRepository repository;

    @PostMapping
    public Burger create(@RequestBody Burger burger) {
        return repository.save(burger);
    }

    @GetMapping
    public List<Burger> getAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Burger> getById(@PathVariable UUID id) {
        return repository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Burger> update(@PathVariable UUID id, @RequestBody Burger details) {
        return repository.findById(id).map(burger -> {
            burger.setNom(details.getNom());
            burger.setDescription(details.getDescription());
            burger.setPrix(details.getPrix());
            burger.setDisponible(details.getDisponible());
            return ResponseEntity.ok(repository.save(burger));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        return ResponseEntity.notFound().build();
    }
}