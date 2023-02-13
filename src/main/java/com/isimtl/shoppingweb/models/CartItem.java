/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.isimtl.shoppingweb.models;

/**
 *
 * @author Lenovo
 */
public class CartItem {
    private int cartId;
    private Product product;
    private int qty;
    private double totalPrice;

    public CartItem(int cartId, Product product, int qty, double totalPrice) {        
        this.cartId = cartId;
        this.product = product;
        this.qty = qty;
        this.totalPrice = totalPrice;
    }

    public int getcartId() {
        return cartId;
    }

    public Product getProduct() {
        return product;
    }

    public int getQty() {
        return qty;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

}
