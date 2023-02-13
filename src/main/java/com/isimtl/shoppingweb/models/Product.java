/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.isimtl.shoppingweb.models;

import com.isimtl.shoppingweb.services.ValidationException;

/**
 *
 * @author Lenovo
 */
public class Product {
    private final int id;
    private String productName;
    private double productPrice;
    private int categoryId;
    private String imageUrl;

    
    public Product(int id, String productName, double productPrice, int categoryId,String imageUrl){       
        if(productName == null){
            throw new IllegalArgumentException("Product Name can not be null");
        }
        if(productPrice == 0){
            throw new IllegalArgumentException("Product price can not be zero");
        }
//        if(category == null){
//            throw new IllegalArgumentException("category can not be null");
//        }
        this.id = id;
        this.productName = productName;
        this.productPrice = productPrice;
        this.categoryId = categoryId;
        this.imageUrl = imageUrl;
    }

    public Product(String productName, double productPrice, int categoryId,String imageUrl)
    {
        this(0,productName,productPrice,categoryId,imageUrl);
    }

    public Product(int id,Product product) {
        this(id,product.getProductName(),product.getProductPrice(),product.getCategoryId(),product.getImageUrl());
    }
    
    
    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    
    public void setCategoryId(int categoryId) {                
        this.categoryId = categoryId;
    }

    public void setProductName(String productName) {
        if(productName != null)
            this.productName = productName;
    }

    public void setProductPrice(double productPrice) {                
        this.productPrice = productPrice;
    } 
   
    public int getId() {
        return id;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public String getProductName() {
        return productName;
    }

    public double getProductPrice() {
        return productPrice;
    }

    

}
