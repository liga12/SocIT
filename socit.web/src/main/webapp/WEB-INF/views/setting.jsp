<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script src="/static/js/jquery-3.1.1.min.js"></script>
    <script src="/static/js/limitUpload.js"></script>
    <script src="/static/js/jquery.validate.min.js"></script>
    <script src="/static/js/validation.js"></script>

    <link rel="stylesheet" type="text/css" href="/static/css/setting.css"/>
</head>
<body>
<%@include file="headerWithLogout.jsp" %>
<div class="wall">
    <%@include file="menu.jsp" %>
    <div class="right">
        <div class="change_avatar"> Change Avatar
            <form action="/user/setting/saveAvatar" enctype="multipart/form-data" method="post" id="send">
                <p><input type="file" name="file"  accept="image/*,image/jpeg" />
                    <input type="submit" value="Send"></p>
            </form>
        </div>
        <div class="information">Personal Data
            <form action="/user/setting/save" class="date">
                <c:out value="${errorSetting}"/>
                <label for='firstName'></label>
                <input type="text" placeholder="First Name" id="firstName" name="firstName" value="${user.firstName}"/>
                <label for='lastName'></label>
                <input type="text" placeholder="Last Name" id="lastName" name="lastName" value="${user.lastName}"/>
                <label for='login'></label>
                <input type="text" placeholder="Login" id="login" name="login" value="${user.login}"/>
                <label for='city'></label>
                <input type="text" placeholder="City" id="city" name="city" value="${user.city}"/>Date Of Birth
                <div class="select">
                    <select name="day">
                        <c:if test="${user.date != null}">
                            <option value="${requestScope.day}" selected="selected">${requestScope.day}</option>
                        </c:if>
                        <option disabled>Day</option>
                        <c:forEach items="${days}" var="day">
                            <option value="${day}">${day}</option>
                        </c:forEach>
                    </select>
                    <select name="month">
                        <c:if test="${user.date != null}">
                            <option value="${requestScope.month}" selected="selected">${requestScope.month}</option>
                        </c:if>
                        <option disabled>Month</option>
                        <c:forEach items="${months}" var="month">
                            <option value="${month}">${month}</option>
                        </c:forEach>
                    </select>
                    <select name="year">
                        <c:if test="${user.date != null}">
                            <option value="${requestScope.year}" selected="selected">${requestScope.year}</option>
                        </c:if>
                        <option disabled>Year</option>
                        <c:forEach items="${years}" var="year">
                            <option value="${year}">${year}</option>
                        </c:forEach>
                    </select>
                </div>
                <div class="gander">
                    <input type="radio" name="gender" value="MALE"
                           <c:if test="${gender==0}">checked="checked"</c:if> >Male<br>
                    <input type="radio" name="gender" value="FEMALE"
                           <c:if test="${gender==1}">checked="checked"</c:if>>Female<br>
                    <input type="radio" name="gender" value="OTHER"
                           <c:if test="${gender==2}">checked="checked"</c:if>>Other<br>
                </div>
                <input class="bottom" type="submit" value="Save">
            </form>
        </div>
        <div class="change_password">Change Password
            <form id="form" action="/user/setting/changePassword" class="date">
                <c:out value="${error}"/>
                <label for='old_password'></label>
                <input type="password" placeholder="Old Password" id="old_password" name="old_password"/>
                <label for='password'></label>
                <input type="password" placeholder="Password" id="password" name="password"/>
                <label for='lastName'></label>
                <label for='passwordConfirmation'></label>
                <input type="password" placeholder="Password" id="passwordConfirmation" name="passwordConfirmation"/>
                <label for='lastName'></label>
                <input class="bottom" type="submit" value="Save">
            </form>
        </div>
    </div>
    <%@include file="avatar.jsp" %>
</div>
</body>
</html>
