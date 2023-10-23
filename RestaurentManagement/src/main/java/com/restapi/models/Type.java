package com.restapi.models;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "types")
public class Type {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false,columnDefinition = "nvarchar(50)")
    private String type;
    @ManyToOne
    private Menu menu;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public Type(UUID id, String type) {
        this.id = id;
        this.type = type;
    }

    public Type() {
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", type='" + type + '\'' +
                '}';
    }
}

