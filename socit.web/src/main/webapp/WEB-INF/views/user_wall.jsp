<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>

<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script src="/static/js/jquery-3.1.1.min.js"></script>
    <script src="/static/js/texarea_autosize_add_post.js"></script>
    <script src="/static/js/textarea_posts.js"></script>
    <script src="/static/js/edit_post.js"></script>
    <script src="/static/js/dropdown_menu.js"></script>
    <script src="/static/js/bsSliserMulti.js"></script>
    <script src="/static/js/jquery.bxslider.js"></script>
    <script src="/static/js/limitUpload.js"></script>

    <link href="/static/css/jquery.bxslider.css" rel="stylesheet"/>
    <link rel="stylesheet" type="text/css" href="/static/css/wall.css"/>
</head>
<body>
<%@include file="headerWithLogout.jsp" %>
<div class="wall">
    <%@include file="menu.jsp" %>
    <div class="right">
        <div class="main_wall">
            <div class="information">${user.firstName} ${user.lastName}<br><br>
                City: ${user.city}<br>
                Date Of Birth: ${date}<br>
            </div>
            <c:if test="${userWall==true||friendStatus==true}">
                <div class="post">
                    <form action="/uploadFile" method="post" id="send"
                          enctype="multipart/form-data">
                        <div class="post_avatar"></div>
                        <input type="checkbox" name="all" value="al">By all
                        <textarea class='autoExpand' rows='2' data-min-rows='2'
                                  placeholder='Enter text' name="description"></textarea>
                        <p><input type="file" name="file" multiple accept="image/*,image/jpeg">
                            <input type="submit" value="Send"></p>
                    </form>
                </div>
            </c:if>
            <c:forEach items="${requestScope.list}" var="lis">
                <c:choose>
                    <c:when test="${userWall==true||friendStatus==true}">
                        <div class="posts" id="${lis.id}">
                            <div class="post_header">
                                <div class="post_avatar">
                                    <img src="${user.avatar}">
                                </div>
                                <div class="post_data">
                                    <div class="name">${userName}</div>
                                    <div class="date">
                                        <c:out value="${lis.date}"/>
                                    </div>
                                </div>
                                <c:if test="${userWall==true}">
                                    <div class="function" id="function${lis.id}">
                                        <li class="ranksSubmenuItem" style="list-style: none">
                                            <a class="sub" href="/post/delete?id=${lis.id}" id="A">Delete</a>
                                        </li>
                                        <li class="ranksSubmenuItem">
                                            <a class="sub" href="#5" onclick="editPost(${lis.id})">Edit</a>
                                        </li>
                                    </div>
                                </c:if>
                            </div>
                            <div id="main${lis.id}" class="main">
                                <div class="comment" id="comment${lis.id}">${lis.comment}</div>
                            </div>
                            <c:set var="images" value="${lis.photoPostList}"/>
                            <c:if test="${fn:length(images) > 0 }">
                                <div class="images">
                                    <div class='container' style="height: 320px">
                                        <div class="ism-slider" id="my-slider">
                                            <ul class="bxslider" id="${lis.id}com">
                                                <c:forEach items="${lis.photoPostList}" var="liss">
                                                    <li><img src="${liss.location}"/></li>
                                                </c:forEach>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </c:if>
                        </div>
                    </c:when>
                    <c:otherwise>
                        <c:if test="${lis.allUser==true}">
                            <div class="posts" id="${lis.id}">
                                <div class="post_header">
                                    <div class="post_avatar">
                                        <img src="${user.avatar}">
                                    </div>
                                    <div class="post_data">
                                        <div class="name">${userName}</div>
                                        <div class="date">
                                            <c:out value="${lis.date}"/>
                                        </div>
                                    </div>
                                    <c:if test="${userWall==true}">
                                        <div class="function" id="function${lis.id}">
                                            <li class="ranksSubmenuItem" style="list-style: none">
                                                <a class="sub" href="/post/delete?id=${lis.id}" id="A">Delete</a>
                                            </li>
                                            <li class="ranksSubmenuItem">
                                                <a class="sub" href="#5" onclick="editPost(${lis.id})">Edit</a>
                                            </li>
                                        </div>
                                    </c:if>
                                </div>
                                <div id="main${lis.id}" class="main">
                                    <div class="comment" id="comment${lis.id}">${lis.comment}</div>
                                </div>
                                <c:set var="images" value="${lis.photoPostList}"/>
                                <c:if test="${fn:length(images) > 0 }">
                                    <div class="images">
                                        <div class='container' style="height: 320px">
                                            <div class="ism-slider" id="my-slider">
                                                <ul class="bxslider" id="${lis.id}com">
                                                    <c:forEach items="${lis.photoPostList}" var="liss">
                                                        <li><img src="${liss.location}"/></li>
                                                    </c:forEach>
                                                </ul>
                                            </div>
                                        </div>
                                    </div>
                                </c:if>
                            </div>
                        </c:if>
                    </c:otherwise>
                </c:choose>
            </c:forEach>
        </div>
    </div>
    <%@include file="avatar.jsp" %>
</div>
</body>
</html>