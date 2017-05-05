<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>$Title$</title>
    <link href="/static/css/registration.css" rel="stylesheet">

    <script src="/static/js/jquery-3.1.1.min.js"></script>
    <script src="/static/js/jquery.validate.min.js"></script>
    <script src="/static/js/validation.js"></script>
</head>
<body>
<%@include file="header.jsp"%>
<div class="wall">
    <form id="form" action="/registration"  method="post">
        <label for='firstName'></label>
        <input type="text" placeholder="First Name" id="firstName" name="firstName"/>
        <label for='lastName'></label>
        <input type="text" placeholder="Last Name" id="lastName" name="lastName"/>
        <label for='email'></label>
        <input type="text" placeholder="Email" id="email" name="email"/>
        <label for='login'></label>
        <input type="text" placeholder="Login" id="login" name="login"/>
        <label for='password'></label>
        <input type="password" placeholder="Password" id="password" name="password"/>
        <label for='passwordConfirmation'></label>
        <input type="password" placeholder="Password" id="passwordConfirmation" name="passwordConfirmation"/>
        <input class="bottom" type="submit" name="registration" value="Registration"/>
    </form>
    <p><c:out value="${data}"/></p>
</div>
</body>
</html>
