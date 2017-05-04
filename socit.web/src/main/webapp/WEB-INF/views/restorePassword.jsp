<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link href="/static/css/restorePassword.css" rel="stylesheet">
</head>
<body>
<%@ include file="header.jsp" %>
<div class="wall">
    <form action="/restorePassword" modelattribute="user" method="post">
        <input type="text" placeholder="Email" required name="email"/></td>
        <input class="bottom" type="submit" name="registration" value="Go"/>
    </form>
    <c:out value="${data}"/>
</div>

</body>
</html>
