<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/restorePassword" modelattribute="user" method="post">
    <table>
        <tr>
            <td><input type="text" placeholder="Enter email" required name="email"/></td>
        </tr>
        <tr>
            <td>
                <input type="submit" name="registration" value="Go"/>
            </td>
        </tr>
    </table>
</form>
<c:out value="${data}"/>
</body>
</html>
