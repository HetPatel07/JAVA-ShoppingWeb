/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.isimtl.shoppingweb.controllers;

import com.isimtl.authentication.PasswordHasher;
import com.isimtl.shoppingweb.models.User;
import com.isimtl.shoppingweb.services.ShoppingWebService;
import com.isimtl.shoppingweb.services.ValidationException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Lenovo
 */
//@WebServlet(name = "signupController", urlPatterns = {"/signupController"})
public class signupController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */


    private void validateIsNull(String str,String strName) throws ValidationException{
        if(str == null || str.isEmpty()){
            throw  new ValidationException("Please enter " + strName);
        }
    }
    private void validatePassword(String pass,String confirmPass) throws ValidationException{
        if(pass.length() < 3 || !pass.equals(confirmPass))
            throw new ValidationException("Password error");
    }
    private void validateDatefBirth(LocalDate dateOfBirth) throws ValidationException{
        if(dateOfBirth.isAfter(LocalDate.now()))
            throw new ValidationException("Date of birth not valid");
    }
    private void validateEmail(String email) throws ValidationException{
       String emailValidationRegex = "^(.+)@(.+)$";  
        //Compile regular expression to get the pattern  
       Pattern pattern = Pattern.compile(emailValidationRegex);  
        Matcher matcher = pattern.matcher(email);
        if(!matcher.matches())
            throw new ValidationException("Invalid Email address");
    }
    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/signup.jsp").forward(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String userName = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");
        String name = request.getParameter("name");
        String dateOfBirth = request.getParameter("dateOfBirth");
        String email = request.getParameter("email");
        try {
            validateIsNull(userName,"Username");
            validateIsNull(password, "Password");
            validateIsNull(confirmPassword,"Confirm Password");
            validateIsNull(name, "Name");
            validateIsNull(dateOfBirth,"Date Of Birth");        
            validateIsNull(email, "Email");
            validateEmail(email);
            validatePassword(password, confirmPassword);
        } catch (ValidationException e) {
            request.setAttribute("message", e.getMessage());
            
        }
        
        ShoppingWebService service = new ShoppingWebService();
        
        try {
            User user = service.SignUp(new User(userName, PasswordHasher.hashPassword(password), name, LocalDate.parse(dateOfBirth), email));
            response.setStatus(HttpServletResponse.SC_CREATED);
            request.setAttribute("user", user);
            request.getRequestDispatcher("/WEB-INF/welcome.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("message", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/signup.jsp").forward(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>
    

}
