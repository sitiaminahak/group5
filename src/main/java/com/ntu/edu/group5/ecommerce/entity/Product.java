package com.ntu.edu.group5.ecommerce.entity;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;

// @Component
// My mistake: @Component is actually not needed here for this project
@Getter
@Setter
public class Product {
    private String id;
    private String name;
    private String description;
    private double price;

    public Product() {
        this.id = UUID.randomUUID().toString();
    }

    public Product(String name, String description, double price) {
        this();
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
