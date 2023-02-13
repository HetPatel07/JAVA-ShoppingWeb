/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */


package com.isimtl.shoppingweb.services;

import com.isimtl.authentication.PasswordHasher;
import com.isimtl.authentication.PasswordResult;
import com.isimtl.shoppingweb.models.CartItem;
import com.isimtl.shoppingweb.models.Category;
import com.isimtl.shoppingweb.models.OrderHistory;
import com.isimtl.shoppingweb.models.Product;
import com.isimtl.shoppingweb.models.User;
import com.isimtl.shoppingweb.repositories.ShoppingWebRepository;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Lenovo
 */
public final class ShoppingWebService {   
    ShoppingWebRepository repository = new ShoppingWebRepository();
    public User SignUp(User user) throws ValidationException, UserException, ClassNotFoundException, SQLException{
        
//        LocalDate dateOfBirth;
//        try {
//             dateOfBirth = LocalDate.parse(dateOfBirthString);            
//        } catch (Exception e) {
//            throw new ValidationException("Invalid Date");
//        }
//        if(users.containsKey(username))
//            throw new UserException("Username already exists");
//                               
        
        ShoppingWebRepository userRepository = new ShoppingWebRepository();
        UserExists(userRepository.getUsers(),user);
        return userRepository.addUser(user);
    }
    private void UserExists(ArrayList<User> users,User user) throws ValidationException{
        for(User checkUser: users){
            if(checkUser.getUserName().equals(user.getUserName())){
                throw new ValidationException("User name already exists");
            }
        }
    }    
    public User Login(String username,String password) throws ValidationException, UserException, SQLException, ClassNotFoundException{
        ShoppingWebRepository userRepository = new ShoppingWebRepository();
        User user = userRepository.getUser(username);        
        PasswordResult passwordResult = PasswordHasher.checkPassword(password, user.getPassword());                
        if(user == null){
            throw new UserException("Incorrect User Name or Password");        
        }                
        if(passwordResult.equals(passwordResult.Incorrect)){
            throw new UserException("Incorrect User Name or Password");        
        }
        return user;
    }
    public ArrayList<Product> getProducts() throws SQLException, ClassNotFoundException{        
        return repository.getProducts();
    }
    public ArrayList<Product> getProductsByCategory(int categoryId) throws SQLException, ClassNotFoundException{        
        return repository.getProductsByCategory(categoryId);
    }
    public int createCart(int userId) throws SQLException, ClassNotFoundException{
        return repository.createCart(userId);
    }
    public int getAvailableCart(int userId) throws ClassNotFoundException, SQLException{
        return repository.getAvailableCartId(userId);
    }
    public CartItem getAvailableCartItem(CartItem cartItem) throws SQLException, ClassNotFoundException{
        return  repository.getAvailableCartItem(cartItem);
    }
    public CartItem addCartItem(CartItem cartItem) throws SQLException, ClassNotFoundException{
        return repository.addCartItem(cartItem);
    }
    public Product getProduct(int id) throws ClassNotFoundException, SQLException{
        return repository.getProduct(id);
    }
    public boolean updateCartItem(CartItem cartItem) throws ClassNotFoundException, SQLException{
        return repository.updateCartItem(cartItem);
    }
    public int getCartItemsCount(int cartId) throws ClassNotFoundException, SQLException{
        return repository.getCartItemsCount(cartId);
    }
    public ArrayList<CartItem> getCartItems(int cartId) throws ClassNotFoundException, SQLException{
        return repository.getCartItems(cartId);
    }
    public boolean deleteCartItem(int cartId,int productId) throws ClassNotFoundException, SQLException{
        return repository.deleteCartItem(cartId, productId);
    }
    public double getCartTotal(int cartId) throws ClassNotFoundException, SQLException{
        return repository.getCartTotal(cartId);
    }
    public boolean createOrder(int cartId,double orderTotal) throws ClassNotFoundException, SQLException{
        return repository.createOrder(cartId, orderTotal);
    }
    
    public ArrayList<OrderHistory> getOrderHistory(int userId)throws SQLException, ClassNotFoundException{
        return repository.getOrderHistory(userId);
    }
    public ArrayList<Category> getCategories() throws ClassNotFoundException, SQLException{
        return repository.getCategories();
    }
    
}
