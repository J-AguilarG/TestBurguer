package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.UUID;
import java.util.Map;
import java.util.Optional;

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
public ResponseEntity<?> getById(@PathVariable UUID id) {
    Optional<Burger> burger = repository.findById(id);

    if (burger.isPresent()) {
        return ResponseEntity.ok(burger.get());
    } else {
        return ResponseEntity.status(404).body(Map.of("error", "Burger no encontrada con ese ID"));
    }
}

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody Burger details) {
        Optional<Burger> burgerOptional = repository.findById(id);

        if (burgerOptional.isPresent()) {
            Burger burger = burgerOptional.get();
            burger.setNom(details.getNom());
            burger.setDescription(details.getDescription());
            burger.setPrix(details.getPrix());
            burger.setDisponible(details.getDisponible());
            
            return ResponseEntity.ok(repository.save(burger));
        } else {
            return ResponseEntity.status(404).body(Map.of("error", "No se puede actualizar: Burger no encontrada"));
        }
}

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return ResponseEntity.ok(Map.of("mensaje", "Burger eliminada correctamente"));
        } else {
            return ResponseEntity.status(404).body(Map.of("error", "No se puede eliminar: Burger no encontrada"));
        }
}
}