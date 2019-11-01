<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>MyBay - Login</title>
    <link href="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
    <script src="//maxcdn.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.min.js"></script>
    <script src="//cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
    <link rel="stylesheet" type="text/css" href="css/login.css">
</head>
<body>

<form action="LoginServlet" method="post">
    <label>
        <p class="label-txt">ENTER YOUR EMAIL</p>
        <input type="text" class="input" name="email">
        <div class="line-box">
            <div class="line"></div>
        </div>
    </label>
    <label>
        <p class="label-txt">ENTER YOUR PASSWORD</p>
        <input type="password" class="input" name="password">
        <div class="line-box">
            <div class="line"></div>
        </div>
    </label>
    <button type="submit" value="Submit">submit</button>
    <p><br>Not registered yet? <a href="register.jsp">Register here!</a></p>
</form>

</body>
</html>