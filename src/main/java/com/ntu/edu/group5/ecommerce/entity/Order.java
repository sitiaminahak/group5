package com.ntu.edu.group5.ecommerce.entity;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;

import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long orderId;

    @Column(name="order_date", columnDefinition = "TIMESTAMP WITHOUT TIME ZONE")
    @CreationTimestamp
    private Timestamp orderDate;

    @JsonBackReference
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer orderingCustomer;

    //CartItem (inverse side / Many) <--> Order (owner side / One)
    @OneToMany(mappedBy = "order",cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    List <CartItem> orderedItems;

    @Enumerated(EnumType.STRING)
    @Column(name="order_status")
    private OrderStatus orderStatus;

    @Column(name = "total")
    private double total;

    //TODO
    //add in Address class interface with Sariha

    public Order (){
        this.orderStatus= OrderStatus.PENDING;
    }

    public Order(Customer orderingCustomer, List<CartItem> orderedItems) {
        this.orderStatus= OrderStatus.PENDING;
        this.orderingCustomer = orderingCustomer;
        this.orderedItems = orderedItems;
    }

    public double countTotal(){
        List <CartItem> cartItem = this.orderedItems;
        double countTotal = 0;
        for(int i=0 ; i< cartItem.size(); i++){
            CartItem currCart = cartItem.get(i);
            double unitPrice = currCart.getProduct().getPrice() ;
            double unitQuantity = currCart.getCartItemQuantity();
            double subTotal = unitPrice * unitQuantity;
            System.out.println("🔵🔵 cartItem: " + i + " price " + unitPrice + " subTotal: " + unitQuantity);
            countTotal += subTotal;
        }
        return countTotal;
    }

    @Override
    public String toString() {
        return "Order [orderId=" + orderId + ", orderDate=" + orderDate + ", orderingCustomer=" + orderingCustomer
                + ", orderedItems=" + orderedItems + ", total=" + total + "]";
    }

    public enum OrderStatus{
        PENDING,
        CONFIRMED,
        DELIVERED,
        CLOSED
    }

}



