<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="/static/css/newPassword.css" rel="stylesheet">
    <script src="/static/js/jquery-3.1.1.min.js"></script>
    <script src="/static/js/jquery.validate.min.js"></script>
    <script src="/static/js/validation.js"></script>
</head>
<body>
<div class="wall">
    <form id="form" action="/enterNewPassword" method="post">
        <label for='password'></label>
        <input type="password" placeholder="Password" id="password" name="password"/>
        <label for='passwordConfirmation'></label>
        <input type="password" placeholder="Password" id="passwordConfirmation" name="passwordConfirmation"/>
        <input type="submit" name="enter" value="Go"/>
    </form>
</div>
<c:out value='${error}'/>
</body>
</html>
