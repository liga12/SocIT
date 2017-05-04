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

    <link href="/static/css/jquery.bxslider.css" rel="stylesheet"/>
    <link rel="stylesheet" type="text/css" href="/static/css/wall.css"/>
</head>
<body>
<%@include file="headerWithLogout.jsp"%>
<div class="wall">
    <%@include file="menu.jsp"%>
    <div class="right">
        <div class="main_wall">
            <c:set value="${user.firstName} ${user.lastName}" var="userName"/>
            <div class="information">${userName}</div>
            <div class="post">
                <form action="/uploadFile" method="post"
                      enctype="multipart/form-data">
                    <div class="post_avatar"></div>
                    <input type="checkbox" name="all" value="al">By all
                    <textarea class='autoExpand' rows='2' data-min-rows='2'
                              placeholder='Enter text' name="description"></textarea>
                    <p><input type="file" name="file" multiple accept="image/*,image/jpeg">
                        <input type="submit" value="Send"></p>
                </form>
            </div>
            <c:forEach items="${requestScope.list}" var="lis">
                <div class="posts" id="${lis.id}">
                    <div class="post_header">
                        <div class="post_avatar">
                            <img src="https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcRO9769VCMzOZngytdMBmCJTlyV14UxcnChZ8R3CAnBVIJpAUOT">
                        </div>
                        <div class="post_data">
                            <div class="name">${userName}</div>
                            <div class="date">1
                                <c:out value="${lis.date}"/>
                            </div>
                        </div>
                        <div class="function" id="function${lis.id}">
                            <li class="ranksSubmenuItem" style="list-style: none">
                                <a class="sub" href="/post/delete?id=${lis.id}" id="A">Delete</a>
                            </li>
                            <li class="ranksSubmenuItem">
                                <a class="sub" href="#5" onclick="editPost(${lis.id})">Edit</a>
                            </li>
                        </div>
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
            </c:forEach>
        </div>
    </div>
    <div class="avatar">
        <img src="https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcRO9769VCMzOZngytdMBmCJTlyV14UxcnChZ8R3CAnBVIJpAUOT">
    </div>
</div>
</body>
</html>