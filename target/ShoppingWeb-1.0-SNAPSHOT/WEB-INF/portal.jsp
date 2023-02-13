<%-- 
    Document   : portal
    Created on : 12-Jul-2022, 5:33:14 pm
    Author     : Lenovo
--%>

<%@page import="com.isimtl.shoppingweb.models.Category"%>
<%@page import="com.isimtl.shoppingweb.models.Product"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.isimtl.shoppingweb.models.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<%
    User user = (User) session.getAttribute("user");
    ArrayList<Product> products = (ArrayList<Product>) request.getAttribute("products");
    ArrayList<Category> categories = (ArrayList<Category>) request.getAttribute("categories");
    String userName = user.getName();
    int cartItemsCount = (int)session.getAttribute("cartItemsCount");
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
                <li><a href="/ShoppingWeb/userDashboard?action=all">All</a></li>
                <% for(Category category : categories) { %>
                    <li><a href="/ShoppingWeb/userDashboard?action=<%= category.getName() %>&categoryId=<%= category.getId() %>">
                            <%= category.getPluralName()%></a></li>
                <% } %>
                                
                <li><a href="/ShoppingWeb/orderHistory">Order History</a></li>
                <li class="userName">Hello <span><%= userName%></span></li>
                <li><a class="cart" href="/ShoppingWeb/userDashboard?action=viewCart">
                        <i class="fa-solid fa-cart-shopping"></i><span class="cartItemNumber"><%= cartItemsCount %></span></li>
                        <li><a href="/ShoppingWeb/loginController?action='logout'">Log Out</a></li>
            </ul>
        </header>                
        <div class="products">  
            <% for (Product product : products) {%>
            <div class="product"> 
                <img src="images/<%= product.getImageUrl()%>" alt="alt"/>
                <p><%= product.getProductName()%></p>
                <p><%= product.getProductPrice()%> $</p>    
                <form action="/ShoppingWeb/userDashboard" method="post"> 
                    Qty
                    <input type="number" name="qty"/> <br><!-- comment -->
                    <button type="submit" value="<%= product.getId()%>" name="id">Add to cart</button>
                </form>
            </div>
            <% }
            %>

        </div>
    </body>
</html>
