package com.ntu.edu.group5.ecommerce.exception;

public class CustomerNotFoundException extends RuntimeException {
    CustomerNotFoundException(Long id) {
        super("Could not find customer with id: " + id + ".");
    }
}
