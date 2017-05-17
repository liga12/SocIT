<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="/static/css/friends.css"/>
</head>
<body>
<%@include file="headerWithLogout.jsp" %>
<div class="wall">
    <%@include file="menu.jsp" %>
    <div class="right">
        <div class="search">
            <form action="/user/searchFriends">Search people
                <input type="text" name="search"/>
                <input type="submit"/>
            </form>
        </div>
        <c:if test="${fn:length(friends) > 0 }">
            You Friends
        </c:if>
        <c:forEach items="${friends}" var="friend">
            <div class="friend">
                <div class="friends_avatar">
                    <img src="${friend.friend.avatar}">
                </div>
                <div class="name">${friend.friend.firstName} ${friend.friend.lastName}</div>
                <div class="action">
                    <a href="/user/deleteFriends?id=${friend.id}">
                        <input type="submit" value="Delete">
                    </a>
                </div>
            </div>
        </c:forEach>
        <c:if test="${fn:length(users) > 0 }">
            Search Friends
        </c:if>
        <c:forEach items="${users}" var="user">
            <div class="friend">
                <div class="friends_avatar">
                    <img src="${user.avatar}">
                </div>
                <div class="name">${user.firstName} ${user.lastName}</div>
                <div class="action">
                    <a href="/user/addFriends?id=${user.id}">
                        <input type="submit" value="Add">
                    </a>
                </div>
            </div>
        </c:forEach>
    </div>
    <%@include file="avatar.jsp" %>
</div>
</body>
</html>
