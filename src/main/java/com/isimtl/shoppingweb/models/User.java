/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.isimtl.shoppingweb.models;

import com.isimtl.authentication.HashedPassword;
import java.time.LocalDate;

public class User { 
    private final int id;
    private final String userName;
    private  final HashedPassword password;
    private String email;
    private String name;
    private LocalDate dateOfBirth;

    public int getId() {
        return id;
    }

    
    public String getEmail() {
        return email;
    }

    public String getUserName() {
        return userName;
    }

    public HashedPassword getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public int getAge() {
        return dateOfBirth.until(LocalDate.now()).getYears();
    }

    public void setName(String name) {
        if(name != null)
            this.name = name;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        if(dateOfBirth != null)
            this.dateOfBirth = dateOfBirth;
    }

    public void setEmail(String email) {
        if(email != null)
        this.email = email;
    }
       
    public User(int id, String userName, HashedPassword password, String name, LocalDate dateOfBirth,String email) {
        if(userName == null)
            throw new IllegalArgumentException("Please enter User Name");
        if(password == null)
            throw new IllegalArgumentException("Please enter password");        
        if(name == null)
            throw new IllegalArgumentException("Please enter name");
        if(email == null)
            throw new IllegalArgumentException("Please enter email");    
        if(dateOfBirth == null || dateOfBirth.isAfter(LocalDate.now()))
            throw new IllegalArgumentException("Please enter date of birth");
        this.userName = userName;
        this.password = password;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.email = email;
        this.id = id;
    }
    public User(String userName, HashedPassword password, String name, LocalDate dateOfBirth,String email) {
        this(0, userName, password, name, dateOfBirth, email);
    }
    
    public User(int id ,User user){
        this(id, user.getUserName(),user.getPassword(),user.getName(),user.getDateOfBirth(),user.getEmail());
    }

    @Override
    public String toString() {
        return "User : " + userName; // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
    
    
    
    
    
           
}
