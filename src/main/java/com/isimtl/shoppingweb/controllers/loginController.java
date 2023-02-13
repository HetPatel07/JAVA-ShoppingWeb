/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.isimtl.shoppingweb.controllers;

import com.isimtl.shoppingweb.models.User;
import com.isimtl.shoppingweb.services.ShoppingWebService;
import com.isimtl.shoppingweb.services.ValidationException;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.websocket.Session;

/**
 *
 * @author Lenovo
 */
@WebServlet(name = "loginController", urlPatterns = {"/loginController"})
public class loginController extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    

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
        String action = request.getParameter("action");
        if("logout".equals(action)){
            HttpSession session = request.getSession();
            session.removeAttribute("user");
        }
        request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        
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
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        ShoppingWebService service = new ShoppingWebService();
        try{
            validateIsNull(username, "user name"); 
            validateIsNull(password, "password");                   
            User user  = service.Login(username, password);                        
            HttpSession session = request.getSession(true);
            session.setAttribute("user", user);            
            request.getRequestDispatcher("/userDashboard").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("message", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
    }
    private void validateIsNull(String str,String strName) throws ValidationException{
        if(str == null || str.isEmpty()){
            throw  new ValidationException("Please enter " + strName);
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
