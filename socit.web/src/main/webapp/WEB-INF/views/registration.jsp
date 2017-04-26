<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>$Title$</title>
</head>
<body>
<form action="/registration"  modelattribute="user" method="post">
    <table>
        <tr>
            <td><input type="text" placeholder="Enter firstName" required name="firstName"/></td>
        </tr>
        <tr>
            <td><input type="text" placeholder="Enter lastName" required name="lastName"/></td>
        </tr>
        <tr>
            <td><input type="text" placeholder="Enter email" required name="email"/></td>
        </tr>
        <tr>
            <td><input type="text" placeholder="Enter login" required name="login"/></td>
        </tr>
        <tr>
            <td><input type="password" placeholder="Enter password" required name="password"/></td>
        </tr>
        <tr>
            <td><input type="password" placeholder="Enter password confirmation" required name="passwordConfirmation"/></td>
        </tr>
        <tr>
            <td>
                <input type="submit" name="registration" value="Registration"/>
            </td>
        </tr>
    </table>
</form>
<c:out value="${data}"/>
</body>
</html>
