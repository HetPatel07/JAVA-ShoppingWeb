/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package com.isimtl.shoppingweb.services;

/**
 *
 * @author Lenovo
 */
public class UserException extends Exception{

    /**
     * Creates a new instance of <code>UserException</code> without detail
     * message.
     */
    public UserException() {
    }

    /**
     * Constructs an instance of <code>UserException</code> with the specified
     * detail message.
     *
     * @param msg the detail message.
     */
    public UserException(String msg) {
        super(msg);
    }
}
