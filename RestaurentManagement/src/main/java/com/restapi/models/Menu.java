package com.restapi.models;

import jakarta.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "menus")
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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Type> getType() {
        return type;
    }

    public void setType(List<Type> type) {
        this.type = type;
    }

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
    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", image='" + image + '\'' +
                ", price=" + price +
                ", type=" + type +
                '}';
    }
}
