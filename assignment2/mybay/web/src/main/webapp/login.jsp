<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>MyBay - Login</title>
    <s:include value="template/scripts.jsp" />
    <link rel="stylesheet" type="text/css" href="css/login.css">
</head>
<body> u

<form action="LoginServlet" method="post">
    <label>
        <p class="label-txt">ENTER YOUR EMAIL</p>
        <input type="text" class="input" name="email">
        <div class="line-box">
            <div class="line"></div><
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