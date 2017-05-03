<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form action="/enterNewPassword" method="post">
    <table>
        <tr>
            <td><input type="password" placeholder="Enter password" required name="password" id="password"/></td>
        </tr>
        <tr>
            <td><input type="password" placeholder="Enter password confirmation" required name="passwordConfirmation"/>
            </td>
        </tr>
        <tr>
            <td>
                <input type="submit" name="enter" value="Go"/>
            </td>
        </tr>
    </table>
</form>
<c:out value='${error}'/>
</body>
</html>
