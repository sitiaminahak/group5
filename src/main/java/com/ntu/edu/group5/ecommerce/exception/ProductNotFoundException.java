package com.ntu.edu.group5.ecommerce.exception;

public class ProductNotFoundException extends RuntimeException {
    ProductNotFoundException(String id) {
        super("Could not find product with id: " + id + ".");
    }
}
