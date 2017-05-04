<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>$Title$</title>
    <link href="/static/css/registration.css" rel="stylesheet">
</head>
<div class="header">
    SocIT
</div>
<div class="wall">
    <form action="/registration" modelattribute="user" method="post">
        <input type="text" placeholder="First Name" required name="firstName"/>
        <input type="text" placeholder="Last Name" required name="lastName"/>
        <input type="text" placeholder="Email" required name="email"/>
        <input type="text" placeholder="Login" required name="login"/>
        <input type="password" placeholder="Password" required name="password"/>
        <input type="password" placeholder="Password" required name="passwordConfirmation"/>
        <input class="bottom" type="submit" name="registration" value="Registration"/>
    </form>
    <p><c:out value="${data}"/></p>
</div>
</body>
</html>
