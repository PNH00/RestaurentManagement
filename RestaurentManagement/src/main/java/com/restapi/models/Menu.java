package com.restapi.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "menus")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false,columnDefinition = "nvarchar(50)",unique = true)
    private String name;
    @Column(nullable = false,columnDefinition = "nvarchar(50)")
    private String description;
    @Column(nullable = false,columnDefinition = "nvarchar(150)")
    private String image;
    @Column(nullable = false)
    private double price;
    @OneToMany
    private List<Type> type;
}