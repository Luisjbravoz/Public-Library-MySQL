<%-- 
    PROJECT: PUBLIC LIBRARY.
    LUIS J. BRAVO ZÚÑIGA.
    INDEX PAGE (LOGIN PAGE).
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>

    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Newton PL login</title>
        <link href="boostrap/bootstrap.min.css" rel="stylesheet" type="text/css"/>
        <link href="tools/font/ionicons.min.css" rel="stylesheet" type="text/css"/>
        <link href="tools/css/login/Google-Style-Login.css" rel="stylesheet" type="text/css"/>
        <link href="tools/css/login/styles.css" rel="stylesheet" type="text/css"/>
        <link href="tools/css/modal/style.css" rel="stylesheet" type="text/css"/>
        <link href="tools/css/login/LoginForm-Dark.css" rel="stylesheet" type="text/css"/>
    </head>
    <body>
         <!-- The Message Modal -->
        <div id="myModal" class="modal">
            <div class="modal-content" style=" width: 50%;">
                <span class="close">&times;</span>
                <p><h4 class="info-error">Your username or password are incorrect.</h4></p>
                <p class="info-error">Check your info and try again, please!</p>
            </div>
        </div>
        <div class="login-dark">
            <form action="javascript:login()" method="POST">
                <h2 class="sr-only">Login Form</h2>
                <div class="form-group" style="text-align: center; font-size: 20px;">Login to continue</div>
                <div class="form-group"><input class="form-control" type="email" id="username" name="email" placeholder="Username" autocomplete="off" required></div>
                <div class="form-group"><input class="form-control" type="password" id="password" name="password" placeholder="Password" autocomplete="off" required></div>
                <div class="form-group"><button class="btn btn-primary btn-block" type="submit">Log In</button></div><a href="#" class="forgot">Forgot your email or password?</a></form>
        </div>
        <script src="tools/js/jquery.min.js" type="text/javascript"></script>
        <script src="boostrap/bootstrap.min.js" type="text/javascript"></script>
        <script src="tools/js/login/admin_login.js" type="text/javascript"></script>
    </body>

</html>
