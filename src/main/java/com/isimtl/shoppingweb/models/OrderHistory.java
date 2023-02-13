/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.isimtl.shoppingweb.models;

import java.util.ArrayList;

/**
 *
 * @author Lenovo
 */
public class OrderHistory {
    private int id;
    private ArrayList<CartItem> cartItems;
    private double orderTotal;
    
    public OrderHistory(int id, ArrayList<CartItem> cartItem,double orderTotal) {
        this.id = id;
        this.cartItems = cartItem;
        this.orderTotal = orderTotal;
    }

    public ArrayList<CartItem> getCartItems() {
        return cartItems;
    }

    public int getId() {
        return id;
    }

    public double getOrderTotal() {
        return orderTotal;
    }


    
}
