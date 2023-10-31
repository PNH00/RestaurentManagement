package com.restapi.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "bills")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Bill {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @OneToMany
    private List<Menu> menus;
    @Column(nullable = false,columnDefinition = "int")
    private int quantities;
    @Column(name = "total_price",nullable = false,columnDefinition = "float")
    private double totalPrice;
}
