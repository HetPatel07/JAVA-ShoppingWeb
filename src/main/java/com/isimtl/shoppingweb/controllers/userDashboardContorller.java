/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.isimtl.shoppingweb.controllers;

import com.isimtl.shoppingweb.models.CartItem;
import com.isimtl.shoppingweb.models.Product;
import com.isimtl.shoppingweb.models.User;
import com.isimtl.shoppingweb.services.ShoppingWebService;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
@WebServlet(name = "userDashboardContorller", urlPatterns = {"/userDashboard"})
public class userDashboardContorller extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException, ClassNotFoundException {
        ShoppingWebService service = new ShoppingWebService();        
        HttpSession session = request.getSession(true);        
        User user = (User)session.getAttribute("user");                
        String action = request.getParameter("action");
        
        if(user == null){
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }else{
        request.setAttribute("products", service.getProducts());
        request.setAttribute("categories", service.getCategories());
        String idString= request.getParameter("id");
        int availableCartId = service.getAvailableCart(user.getId());
        session.setAttribute("cartItemsCount", service.getCartItemsCount(availableCartId));
        
        if("viewCart".equals(action)){              
            session.setAttribute("cartId", availableCartId);
            request.getRequestDispatcher("/cartController").forward(request, response);
        }
        if("sports".equals(action) || "clothing".equals(action) || "furniture".equals(action)){
            request.setAttribute("products", service.getProductsByCategory(Integer.parseInt(request.getParameter("categoryId"))));
        }
//        if("clothing".equals(action)){
//            request.setAttribute("products", service.getProductsByCategory(Integer.parseInt(request.getParameter("categoryId"))));
//        }
        if(idString == null){
            request.getRequestDispatcher("/WEB-INF/portal.jsp").forward(request, response);            
        }
        
        int id = Integer.parseInt(idString);
        String qtyString = request.getParameter("qty");
        
        if(qtyString == null || qtyString.isEmpty()){
            request.getRequestDispatcher("/WEB-INF/portal.jsp").forward(request, response);
        }
        int qty = Integer.parseInt(qtyString);
        
        if(qty == 0){
            request.getRequestDispatcher("/WEB-INF/portal.jsp").forward(request, response);
        }
        else if( qty > 0){
        request.setAttribute("products", service.getProducts());
        
        Product product = service.getProduct(id);
        double totalPrice = product.getProductPrice() * qty;        
        CartItem cartItem = new CartItem(availableCartId, product, qty, totalPrice);
        CartItem availableCartItem = service.getAvailableCartItem(cartItem);        
        boolean cartUpdated;
        if(availableCartItem != null){ 
            CartItem updateCartItem = new CartItem(cartItem.getcartId(), cartItem.getProduct(), cartItem.getQty() + availableCartItem.getQty(),
                                        cartItem.getTotalPrice() + availableCartItem.getTotalPrice());
             cartUpdated = service.updateCartItem(updateCartItem);
        }else{            
            cartItem = service.addCartItem(cartItem);
        }
//        if(availableCartId == 0){
//            service.createCart(id);
//        }
        }
        session.setAttribute("cartItemsCount", service.getCartItemsCount(availableCartId));
        request.getRequestDispatcher("/WEB-INF/portal.jsp").forward(request, response);
        }
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
        try {
            processRequest(request, response);
        } catch (SQLException | ClassNotFoundException ex) {
            request.setAttribute("message", "Some error happened");
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        }
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
        try {
            processRequest(request, response);
        } catch (SQLException | ClassNotFoundException ex) {
            request.setAttribute("message", "Some error happened");
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
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
