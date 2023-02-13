/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.isimtl.shoppingweb.repositories;

import com.isimtl.authentication.HashedPassword;
import com.isimtl.authentication.PasswordHasher;
import com.isimtl.shoppingweb.models.CartItem;
import com.isimtl.shoppingweb.models.Category;
import com.isimtl.shoppingweb.models.OrderHistory;
import com.isimtl.shoppingweb.models.Product;
import com.isimtl.shoppingweb.models.User;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import javax.xml.catalog.Catalog;

/**
 *
 * @author Lenovo
 */
public class ShoppingWebRepository {    
    private final String connectionUrl;
    private final String username;
    private final String password; 
    public ShoppingWebRepository() {
        String databseName = "shoppingweb_db";
        connectionUrl = "jdbc:mariadb://localhost:3315/" + databseName;        
        username = "root";
        password = "admin";
    }
    
    
    private User readNextUser(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String userName = resultSet.getString("user_name");        
        String email = resultSet.getString("email");
        String name = resultSet.getString("name");        
        LocalDate dateOfBirth =   resultSet.getDate("date_of_birth").toLocalDate();        
        HashedPassword hashedPassword = new HashedPassword(resultSet.getBytes("salt"),resultSet.getBytes("hash"));
        
        return new User(id, userName, hashedPassword, name, dateOfBirth, email);
        
    }
    public ArrayList<User> getUsers() throws SQLException, ClassNotFoundException{
        Class.forName("org.mariadb.jdbc.Driver");
        try(Connection connection = DriverManager.getConnection(connectionUrl,username,password)){
            String query = "select * from Users";
            PreparedStatement statement = connection.prepareStatement(query);
            
            ResultSet resultSet = statement.executeQuery();
            ArrayList<User> users = new ArrayList<>();
            while(resultSet.next()){
                users.add(readNextUser(resultSet));
            }
            return users;
        }
    }
    public User addUser(User user) throws ClassNotFoundException, SQLException{
        Class.forName("org.mariadb.jdbc.Driver");
        try(Connection connection = DriverManager.getConnection(connectionUrl,username,password)){
            String query = "Insert into Users(user_name,email,name,date_of_birth,salt,hash) values (?,?,?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUserName());            
            statement.setString(2, user.getEmail());
            statement.setString(3, user.getName());
            statement.setDate(4, Date.valueOf(user.getDateOfBirth()));
            statement.setBytes(5, user.getPassword().getSalt());
            statement.setBytes(6, user.getPassword().getHash());
            int rowsAffected = statement.executeUpdate();
            if(rowsAffected > 0){                 
                return new User(getGenereatedId(statement), user);
            }
            throw new IllegalArgumentException("Cannot create User");
        }
    }
    private int getGenereatedId(PreparedStatement statement) throws SQLException {
        try(ResultSet generatedKeys = statement.getGeneratedKeys()){
            if(generatedKeys.next())
                return generatedKeys.getInt(1);
            throw new SQLException("No ids");
        }
    }
    
    public User getUser(String userName) throws SQLException, ClassNotFoundException{
        Class.forName("org.mariadb.jdbc.Driver");
        try(Connection connection = DriverManager.getConnection(connectionUrl,username,password)){
            String query = "select * from users where user_name = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, userName);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return readNextUser(resultSet);
            }
        }
        return null;
    }
    
    private Product readNextProduct(ResultSet resultSet) throws SQLException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("product_name");        
        double price = resultSet.getDouble("price");
        int category_id = resultSet.getInt("category_id");        
        String imgUrl = resultSet.getString("image_url");              
        
        return new Product(id, name, price, category_id, imgUrl);
        
    }
    public ArrayList<Product> getProducts() throws SQLException, ClassNotFoundException{
        Class.forName("org.mariadb.jdbc.Driver");
        try(Connection connection = DriverManager.getConnection(connectionUrl,username,password)){
            String query = "select * from Products";
            PreparedStatement statement = connection.prepareStatement(query);
            
            ResultSet resultSet = statement.executeQuery();
            ArrayList<Product> products = new ArrayList<>();
            while(resultSet.next()){
                products.add(readNextProduct(resultSet));
            }
            return products;
        }
    }
    public Product getProduct(int id) throws ClassNotFoundException, SQLException{
        Class.forName("org.mariadb.jdbc.Driver");
        try(Connection connection = DriverManager.getConnection(connectionUrl,username,password)){
            String query = "select * from products where id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return readNextProduct(resultSet);
            }
        }
        return null;
    }
    public ArrayList<Product> getProductsByCategory(int categoryId) throws SQLException, ClassNotFoundException{
        Class.forName("org.mariadb.jdbc.Driver");
        try(Connection connection = DriverManager.getConnection(connectionUrl,username,password)){
            String query = "SELECT id, product_name, price, category_id, image_url FROM products WHERE category_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, categoryId);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<Product> products = new ArrayList<>();
            while(resultSet.next()){
                products.add(readNextProduct(resultSet));
            }
            return products;
        }
        
    }    
    public int createCart(int userId) throws SQLException, ClassNotFoundException{
        Class.forName("org.mariadb.jdbc.Driver");
        try(Connection connection = DriverManager.getConnection(connectionUrl,username,password)){
            String query = "Insert into carts(user_id) values (?)";
            PreparedStatement statement = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, userId);                        
            int rowsAffected = statement.executeUpdate();
            if(rowsAffected > 0){                 
                return getGenereatedId(statement);
            }
            throw new IllegalArgumentException("Cannot create User");
        }
    }
    public int getAvailableCartId(int userId) throws ClassNotFoundException, SQLException{
        Class.forName("org.mariadb.jdbc.Driver");
        try(Connection connection = DriverManager.getConnection(connectionUrl,username,password)){
            String query = "select c2.id as cart_id from carts c2 where user_id = ? and id not in (select o.cart_id  from orders o);";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt("cart_id");
            }
        }        
        return createCart(userId);
    }
    public CartItem addCartItem(CartItem cartItem) throws SQLException, ClassNotFoundException{
        Class.forName("org.mariadb.jdbc.Driver");
        try(Connection connection = DriverManager.getConnection(connectionUrl,username,password)){
            String query = "Insert into cartItems(cart_id,product_id,qty,total_price) values (?,?,?,?)";
            PreparedStatement statement = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, cartItem.getcartId());            
            statement.setInt(2, cartItem.getProduct().getId());
            statement.setInt(3, cartItem.getQty());
            statement.setDouble(4, cartItem.getTotalPrice());
            
            int rowsAffected = statement.executeUpdate();
            if(rowsAffected > 0){                 
                return cartItem;
            }
            throw new IllegalArgumentException("Cannot create User");
        }
    }
    public CartItem getAvailableCartItem(CartItem cartItem) throws SQLException, ClassNotFoundException{
        Class.forName("org.mariadb.jdbc.Driver");
        try(Connection connection = DriverManager.getConnection(connectionUrl,username,password)){
            String query = "select * from cartitems c where cart_id = ? and product_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, cartItem.getcartId());
            statement.setInt(2, cartItem.getProduct().getId());
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return new CartItem(cartItem.getcartId(), cartItem.getProduct(), resultSet.getInt("qty"), resultSet.getDouble("total_price"));
            }
        }        
       
        return null;
    }
    public boolean updateCartItem(CartItem cartItem) throws ClassNotFoundException, SQLException{
        Class.forName("org.mariadb.jdbc.Driver");
        try(Connection connection = DriverManager.getConnection(connectionUrl,username,password)){
            String query = "update cartitems set qty = ? , total_price = ? where cart_id = ? and product_id = ?";
            PreparedStatement statement = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, cartItem.getQty());            
            statement.setDouble(2, cartItem.getTotalPrice());
            statement.setInt(3, cartItem.getcartId());
            statement.setDouble(4, cartItem.getProduct().getId());            
            int rowsAffected = statement.executeUpdate();
            if(rowsAffected > 0){                 
                return true;
            }
            throw new IllegalArgumentException("Cannot Update Cart");
        }
    }
    public int getCartItemsCount(int cartId) throws ClassNotFoundException, SQLException{
        Class.forName("org.mariadb.jdbc.Driver");
        try(Connection connection = DriverManager.getConnection(connectionUrl,username,password)){
            String query = "select sum(qty) as itemsCount from cartitems c where cart_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, cartId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return resultSet.getInt("itemsCount");
            }
        }
        return 0;
    }
    public ArrayList<CartItem> getCartItems(int cartId) throws ClassNotFoundException, SQLException{
        Class.forName("org.mariadb.jdbc.Driver");
        try(Connection connection = DriverManager.getConnection(connectionUrl,username,password)){
            String query = "select * from cartItems where cart_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);            
            statement.setInt(1, cartId);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<CartItem> cartItems = new ArrayList<>();
            while(resultSet.next()){
                cartItems.add(readNextCartItem(resultSet));
            }
            return cartItems;
        }
    }
    private CartItem readNextCartItem(ResultSet resultSet) throws SQLException, ClassNotFoundException {
        int cart_id = resultSet.getInt("cart_id");
        Product product = getProduct(resultSet.getInt("product_id"));
        int qty = resultSet.getInt("qty");
        double totalPrice  = resultSet.getDouble("total_price");        
        return new CartItem(cart_id, product, qty, totalPrice);        
    }
    public boolean deleteCartItem(int cartId,int productId) throws ClassNotFoundException, SQLException{
        Class.forName("org.mariadb.jdbc.Driver");
        try(Connection connection = DriverManager.getConnection(connectionUrl,username,password)){
            String query = "Delete FROM cartItems where cart_id = ? and product_id = ?";
            PreparedStatement statement = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, cartId);            
            statement.setInt(2, productId);
            
            int rowsAffected = statement.executeUpdate();
            if(rowsAffected > 0){                 
                return true;
            }
            throw new IllegalArgumentException("Cannot delete cart item");
        }
    }
    public double getCartTotal(int cartId) throws ClassNotFoundException, SQLException{
        Class.forName("org.mariadb.jdbc.Driver");
        try(Connection connection = DriverManager.getConnection(connectionUrl,username,password)){
            String query = "select sum(total_price) as totalPrice from cartitems c where cart_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, cartId);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){
                return resultSet.getDouble("totalPrice");
            }
        }
        return 0;
    }    
    public boolean createOrder(int cartId,double orderTotal) throws ClassNotFoundException, SQLException{
        Class.forName("org.mariadb.jdbc.Driver");
        try(Connection connection = DriverManager.getConnection(connectionUrl,username,password)){
            String query = "Insert into orders(cart_id,order_total) values (?,?)";
            PreparedStatement statement = connection.prepareStatement(query,Statement.RETURN_GENERATED_KEYS);
            statement.setInt(1, cartId);            
            statement.setDouble(2, orderTotal);
            int rowsAffected = statement.executeUpdate();
            if(rowsAffected > 0){                 
                return true;
            }
            throw new IllegalArgumentException("Cannot create User");
        }
    }
    public ArrayList<OrderHistory> getOrderHistory(int userId)throws SQLException, ClassNotFoundException{
        Class.forName("org.mariadb.jdbc.Driver");
        try(Connection connection = DriverManager.getConnection(connectionUrl,username,password)){
            String query = "select o.id as orderId,c.id as cartId from orders o \n" +
                           " inner join carts c on c.id = o.cart_id \n" +
                           " where c.user_id  = ?";
            PreparedStatement statement = connection.prepareStatement(query);            
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<OrderHistory> orderHistorys = new ArrayList<>();
            while(resultSet.next()){
                orderHistorys.add(readNextOrderHistory(resultSet));
            }
            return orderHistorys;
        }
    }
    private OrderHistory readNextOrderHistory(ResultSet resultSet) throws SQLException, ClassNotFoundException {
        int id = resultSet.getInt("orderId");
        int cartId = resultSet.getInt("cartId");
        double orderTotal = getCartTotal(cartId);
//        String productName = resultSet.getString("productName");
//        int cartProductQty = resultSet.getInt("cartProductQty");
        ArrayList<CartItem> cartItems = getCartItems(cartId);        
        return new OrderHistory(id, cartItems,orderTotal);        
    }    
    public ArrayList<Category> getCategories() throws ClassNotFoundException, SQLException{
        Class.forName("org.mariadb.jdbc.Driver");
        try(Connection connection = DriverManager.getConnection(connectionUrl,username,password)){
            String query = "select * from categories";
            PreparedStatement statement = connection.prepareStatement(query);            
//            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            ArrayList<Category> categories = new ArrayList<>();
            while(resultSet.next()){
                categories.add(readNextCategory(resultSet));
            }
            return categories;
        }
    }
    private Category readNextCategory(ResultSet resultSet) throws SQLException, ClassNotFoundException {
        int id = resultSet.getInt("id");
        String name = resultSet.getString("name");
        String pluralName = resultSet.getString("plural_name");
//        String productName = resultSet.getString("productName");
//        int cartProductQty = resultSet.getInt("cartProductQty");
//        ArrayList<CartItem> cartItems = getCartItems(cartId);        
        return new Category(id, name, pluralName);        
    }    
}
