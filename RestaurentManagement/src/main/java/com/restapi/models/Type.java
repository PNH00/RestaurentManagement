package com.restapi.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "types")
@Data
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false,columnDefinition = "nvarchar(50)")
    private String type;

    public Type(UUID id, String type) {
        this.id = id;
        this.type = type;
    }

    public Type() {
    }
}