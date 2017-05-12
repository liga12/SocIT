<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="/static/css/restorePassword.css" rel="stylesheet">
    <script src="/static/js/jquery-3.1.1.min.js"></script>
    <script src="/static/js/jquery.validate.min.js"></script>
    <script src="/static/js/validation.js"></script>
</head>
<body>
<%@ include file="header.jsp" %>
<div class="wall">
    <form id="form" action="/restorePassword" method="post">
        <label for='email'></label>
        <input type="text" placeholder="Email" id="email" name="email" value="${email}"/></td>
        <input class="bottom" type="submit" name="registration" value="Go"/>
    </form>
    <c:out value="${data}"/>
</div>
</body>
</html>
