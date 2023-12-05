package com.ntu.edu.group5.ecommerce.service;

import java.util.ArrayList;

import com.ntu.edu.group5.ecommerce.entity.Customer;

public interface CustomerService {

    Customer createCustomer(Customer customer);

    Customer getCustomer(Long id);

    ArrayList<Customer> getAllCustomers();

    Customer updateCustomer(Long id, Customer customer);

    void deleteCustomer(Long id);

    //Interaction addInteractionToCustomer(Long id, Interaction interaction);

    ArrayList<Customer> searchCustomers(String firstName);
}
