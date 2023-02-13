/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Exception.java to edit this template
 */
package com.isimtl.shoppingweb.services;

/**
 *
 * @author Lenovo
 */
public class ValidationException extends Exception{

    /**
     * Creates a new instance of <code>ValidationException</code> without detail
     * message.
     */
    public ValidationException() {
    }

    /**
     * Constructs an instance of <code>ValidationException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public ValidationException(String msg) {
        super(msg);
    }
}
