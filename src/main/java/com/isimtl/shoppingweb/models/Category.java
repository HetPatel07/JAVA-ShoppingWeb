/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.isimtl.shoppingweb.models;

/**
 *
 * @author Lenovo
 */
public class Category {
    private final int id;
    private final String name;
    private final String pluralName;

    public Category(int id, String name, String pluralName)
    {
        if (name == null)
            throw new IllegalArgumentException("Name must not be null.");
        if (pluralName == null)
            throw new IllegalArgumentException("Plural name must not be null.");
        
        this.id = id;
        this.name = name;
        this.pluralName = pluralName;
    }

    public int getId()
    {
        return id;
    }

    public String getName()
    {
        return name;
    }
    
    public String getPluralName()
    {
        return pluralName;
    }
    
    @Override
    public String toString()
    {
        return "Category " + id + " " + name;
    }

}
