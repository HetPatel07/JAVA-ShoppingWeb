

<%
    String msg;
    if(request.getAttribute("message") != null){
        msg = String.valueOf(request.getAttribute("message")) ;    
    }
    else{
        msg = "Dont have an account <a href='/ShoppingWeb/signupController'>Sign up</a>";
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
        <form action="/ShoppingWeb/loginController" method=post>
            <fieldset>
            <legend>Log in</legend>
            <br>
            Username<br>
            <input type="text" name="username" id="username"><br><br>
            Password<br>
            <input type="text" name="password" id="password"><br><br>
            <button type="submit">Log in</button>
            </fieldset>
        </form>
        <p style="text-align:center"><%= msg%></p>
        
    </body>
</html>