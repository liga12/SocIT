<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>$Title$</title>
    <link href="/static/css/mainPage.css" rel="stylesheet">
</head>
<body>
<div class="header">
    SocIT
</div>
<div class="wall">
    <div class="left">
        <div class="authentication_form">
            <div class="authentication_header">
                <p>Login</p>
                <a class="link" href="/registrationPage">Registration</a>
            </div>
            <div class="authentication_input">
                <form action="/login" method="post">
                    <input type="text" placeholder="Login" required name="login" id="login"/>
                    <input type="password" placeholder="Password" required name="password" id="password"/>
                    <input class="bottom" type="submit" name="enter" value="Sing In"/>
                </form>
                <a class="link" href="/restorePasswordPage">Forgotten Password</a>
            </div>
            <c:out value='${error}'/>
        </div>

    </div>
    <div class="right"><img src="/static/images/main.jpg"></div>
</div>

</body>
</html>
