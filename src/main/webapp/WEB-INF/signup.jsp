
<%
    String msg;
    if(request.getAttribute("message") != null){
        msg = String.valueOf(request.getAttribute("message")) ;    
    }
    else{
        msg = "";
    }  
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Start Page</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        
        <style>
            button{
                background-color: #6bad6a;
                width: 101%;
                height: 45px;
                border: #7aeb34;
                border-radius: 5px;
            }
            
            input{
                background-color: #e1e3e1;
                width: 100%;
                height: 45px;
                border: #babfba;
                border-radius: 5px;
            }
            
            form{
                
                width: 400px;
                margin:auto;
                
            }
            a{
                color:black;
            }
            
        </style>
    </head>
    <body>
        <br><br><br><br>
        <form method=post action="signupController" >
            <fieldset>
            <legend>Sign up</legend>
            <br>
            Username<br>
            <input type="text" name="username" id="username"><br><br>
            Password<br>
            <input type="password" name="password" id="password"><br><br>
            Confirm Password<br>
            <input type="password" name="confirmPassword" id="confirmPassword"><br><br>
            Name<br>
            <input type="text" name="name" id="name"><br><br>
            Email<br>
            <input type="text" name="email" /><br><br>
            Date of birth<br>
            <input type="date" name="dateOfBirth" id="dateOfBirth"><br><br>     
            <button type="submit">Sign up</button>
            </fieldset>
        </form>
        <p style="text-align:center"><%= msg %></p>
        <p style="text-align:center">Already have an Account <a href='/ShoppingWeb/loginController'>Log in</a></p>
        
    </body>
</html>