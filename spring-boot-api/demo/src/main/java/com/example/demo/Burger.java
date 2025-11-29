package com.example.demo; 
import jakarta.persistence.*;
import lombok.Data;
import java.util.UUID;

@Entity
@Data // Lombok 
public class Burger {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String nom;
    private String description;
    private Double prix;
    private Boolean disponible;
}