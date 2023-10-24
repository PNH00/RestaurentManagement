package com.restapi.models;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "menus")
@Data
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false,columnDefinition = "nvarchar(50)")
    private String name;
    @Column(nullable = false,columnDefinition = "nvarchar(50)")
    private String description;
    @Column(nullable = false,columnDefinition = "nvarchar(150)")
    private String image;
    @Column(nullable = false)
    private double price;
    @OneToMany
    private List<Type> type;

    public Menu(UUID id, String name, String description, String image, double price, List<Type> type) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
        this.price = price;
        this.type = type;
    }

    public Menu() {
    }
}