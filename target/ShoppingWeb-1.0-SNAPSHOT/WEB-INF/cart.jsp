<%-- 
    Document   : portal
    Created on : 12-Jul-2022, 5:33:14 pm
    Author     : Lenovo
--%>

<%@page import="com.isimtl.shoppingweb.models.CartItem"%>
<%@page import="com.isimtl.shoppingweb.models.Product"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.isimtl.shoppingweb.models.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    User user = (User) session.getAttribute("user");
    ArrayList<CartItem> cartItems = (ArrayList<CartItem>) session.getAttribute("cartItems");
    String userName = user.getName();
    String orderMsg = (String)request.getAttribute("orderMsg");
    if(orderMsg == null){
        orderMsg = "";
    }
    int cartItemsCount = (int) session.getAttribute("cartItemsCount");
    double cartTotal = (double)session.getAttribute("cartTotal");
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
                
                <li><a href="/ShoppingWeb/orderHistory">Order History</a></li>
                <li class="userName">Hello <span><%= userName%></span></li>
                <li><a class="cart" href="/ShoppingWeb/cartController">
                        <i class="fa-solid fa-cart-shopping"></i><span class="cartItemNumber"><%= cartItemsCount%></span></li>
                <li><a href="/ShoppingWeb/loginController?action='logout'">Log Out</a></li>
            </ul>
        </header>                
        <table class="cartItems" >
            <thead>
            <td>Image</td>
            <td>Product Name</td>
            <td>Price</td>
            <td>Qty</td>
            <td></td>
            <td></td>            
        </thead>
        <%if(cartItems.size() > 0){
             for(CartItem cartItem :cartItems){ %>
         <tr>
             <td><img src="images/<%= cartItem.getProduct().getImageUrl() %>" alt="alt"/></td><!-- comment -->
             <td><%= cartItem.getProduct().getProductName() %></td>
             <td><%= cartItem.getTotalPrice() %></td>
         <form action="/ShoppingWeb/cartController" method="post">
             <td><input type="number" value="<%= cartItem.getQty()%>" name="qty"/></td>
             <td><input type="hidden" name="productId" value="<%= cartItem.getProduct().getId()%>"/></td>
             <td><button type="submit" name="action" value="update">update</button></td>
             <td><button type="submit" name="action" value="remove">remove</button></td>
             </form>
        </tr>
          <% }} %>
    </table>
    <div>
        <p class="orderMsg"><%= orderMsg%></p>
        <p>Total Price :<%= cartTotal%> $ </p>
    </div>
    <a href="/ShoppingWeb/cartController?action=placeOrder" class="placeOrder">Place Order</a>
</body>
</html>
