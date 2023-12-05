package com.ntu.edu.group5.ecommerce.entity;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class Product {
    private String productId;
    private String name;
    private String description;
    private double price;

    public Product() {
        this.productId = UUID.randomUUID().toString();
    }

    public Product(String name, String description, double price) {
        this();
        this.name = name;
        this.description = description;
        this.price = price;
    }
}
