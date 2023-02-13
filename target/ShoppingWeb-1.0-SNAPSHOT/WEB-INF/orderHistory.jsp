<%-- 
    Document   : portal
    Created on : 12-Jul-2022, 5:33:14 pm
    Author     : Lenovo
--%>

<%@page import="com.isimtl.shoppingweb.models.CartItem"%>
<%@page import="com.isimtl.shoppingweb.models.OrderHistory"%>
<%@page import="com.isimtl.shoppingweb.models.Product"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.isimtl.shoppingweb.models.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    User user = (User) session.getAttribute("user");
//    ArrayList<Product> products = (ArrayList<Product>) request.getAttribute("products");
    String userName = user.getName();
    int cartItemsCount = (int)session.getAttribute("cartItemsCount");
    ArrayList<OrderHistory> orderHistorys = (ArrayList<OrderHistory>)session.getAttribute("orderHistory"); 
//    String cartIdString  = (String)request.getAttribute("cartId");        
//    int cartId;
//     if(cartIdString != null){
//        cartId = Integer.parseInt(cartIdString);
//     } else{
//     cartId = 0;
//    }
//    int cartItemsCount;
//    if(cartItemsCountString == null){
//        cartItemsCount = 0;
//    }
//    else{cartItemsCount = Integer.parseInt(cartItemsCountString);}
    
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>        

        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.1.1/css/all.min.css"
              integrity="sha512-KfkfwYDsLkIlwQp6LFnl8zNdLGxu9YAA1QvwINks4PhcElQSvqcyVLLD9aMhXd13uQjoXtEKNosOWaZqXgel0g==" crossorigin="anonymous" referrerpolicy="no-referrer" />
        <link rel="stylesheet" href="./css/shopping.css" />
        <style>
            /*            body,html{
                            height:100%;
                            width:100%;
                            margin: 0;
                            padding: 0;
                            font-family: monospace;
                        }*/


        </style>
    </head>

    <body>
        <header class="header">
            <h2>Shopping Web</h2>
            <ul>                  
                <li><a href="/ShoppingWeb/userDashboard">Home</a></li>
                <li><a href="ShoppingWeb/orderHistory">Order History</a></li>
                <li class="userName">Hello <span><%= userName%></span></li>
                <li><a class="cart" href="/ShoppingWeb/userDashboard?action=viewCart">
                        <i class="fa-solid fa-cart-shopping"></i><span class="cartItemNumber"><%= cartItemsCount %></span></li>
                        <li><a href="/ShoppingWeb/loginController?action='logout'">Log Out</a></li>
            </ul>
        </header>                
       <% for(OrderHistory orderHistory : orderHistorys){ %>
            <div class="order"> 
                <p>Order no: <%= orderHistory.getId() %></p>
                <%for(CartItem cartItem: orderHistory.getCartItems()){ %>
                <div class="orderProduct">
                <p>Product name : <%= cartItem.getProduct().getProductName()%></p>
                <p>Product Qty : <%= cartItem.getQty() %></p>
                <p>Product Total : <%= cartItem.getTotalPrice()%></p>
                </div>
             <% } %>
             <p>Order Total : <%= orderHistory.getOrderTotal() %></p>
            </div>
          <% } %>
        </div>
    </body>
</html>
