<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>$Title$</title>
</head>
<body>
<%--<form action="/admin/home" method="post">--%>
<form action="/login" method="post">
    <table>
        <tr>
            <td><label for="login">Login</label></td>
            <td><input type="text" placeholder="Enter login" required name="login" id="login"/></td>
        </tr>

        <tr>
            <td><label for="password">Password</label></td>
            <td><input type="password" placeholder="Enter password" required name="password" id="password"/></td>
        </tr>
        <tr>
            <td>
                <input type="submit" name="enter" value="Login"/>
            </td>
        </tr>
    </table>
</form>
<form action="/registrationPage" method="get">
    <input type="submit" name="registration" value="Registration"/>
</form>
<%--<img src="/st/1.jpg"/>--%>
<img src="/st/userId_37/post_36/1Screenshot from 2017-02-20 19-04-53.png"/>
<c:out value='${error}'/>
</body>
</html>
