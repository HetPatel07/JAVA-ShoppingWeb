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
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Lenovo
 */
@WebServlet(name = "cartController", urlPatterns = {"/cartController"})
public class cartController extends HttpServlet {

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
            throws ServletException, IOException {
        

        try {            
            //        User user = (User)request.getAttribute("user"); 
            HttpSession session = request.getSession();
            request.setAttribute("orderMsg","");
            ShoppingWebService service = new ShoppingWebService();
            int cartId = (int) session.getAttribute("cartId");            
            String action = request.getParameter("action");
            String qtyString = request.getParameter("qty");
            if("update".equals(action)){
                if(qtyString == null || qtyString.isEmpty()){
                    request.getRequestDispatcher("/WEB-INF/cart.jsp").forward(request, response);
                }
                int qty = Integer.parseInt(qtyString);
                if(qty == 0){
                    service.deleteCartItem(cartId, Integer.parseInt(request.getParameter("productId")));
                }else if(qty  > 0){
                    Product product = service.getProduct(Integer.parseInt(request.getParameter("productId")));
                    CartItem cartItem = new CartItem(cartId, product, qty, qty * product.getProductPrice());
                    service.updateCartItem(cartItem);
                }                        
            } else if("remove".equals(action)){
                service.deleteCartItem(cartId, Integer.parseInt(request.getParameter("productId")));
            } else if("placeOrder".equals(action)){
                ArrayList<CartItem> cartItems = service.getCartItems(cartId);
                if(!cartItems.isEmpty()){
                    service.createOrder(cartId, service.getCartTotal(cartId));
                    User user = (User)session.getAttribute("user");                                  
                    int availableCartId = service.getAvailableCart(user.getId());
                    session.setAttribute("cartId", availableCartId);
                    session.setAttribute("cartItemsCount", service.getCartItemsCount(availableCartId));
                    session.setAttribute("cartItems", new ArrayList<>());
                    session.setAttribute("cartTotal", 0.0);
                    session.setAttribute("cartItemsCount", 0);
                    request.setAttribute("orderMsg", "Succefully placed order");
                    request.getRequestDispatcher("/WEB-INF/cart.jsp").forward(request, response);
                }
            }

            session.setAttribute("cartItems", service.getCartItems(cartId));
            session.setAttribute("cartTotal", service.getCartTotal(cartId));
            session.setAttribute("cartItemsCount", service.getCartItemsCount(cartId));
            
            request.getRequestDispatcher("/WEB-INF/cart.jsp").forward(request, response);
        }
         catch (Exception e) {
             request.setAttribute("message", e.getMessage());
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
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
        processRequest(request, response);
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
        processRequest(request, response);
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
