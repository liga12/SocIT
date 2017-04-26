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


    <script src="/static/js/jquery.bxslider.js"></script>
    <link href="/static/css/jquery.bxslider.css" rel="stylesheet"/>
    <link rel="stylesheet" type="text/css" href="/static/css/wall.css"/>
    <script>

        $(document).ready(function () {
            var myArray = $('.bxslider');
            $.each(myArray, function () {
                $('#' + $(this).attr('id')).bxSlider({});
            });
        });

    </script>
    <style>

    </style>
</head>
<body>
<div class="header">
    <a class="name_company" href="">SocIT</a>
    <a class="exit" href="/logout">EXIT</a>
</div>
<div class="table">
    <div class="left">
        <a class="menu" href="">avatar</a>
        <a class="menu" href="">bookmark</a>
        <a class="menu" href="">friends</a>
        <a class="menu" href="">setting</a>
        <a class="menu" href="">news</a>
    </div>
    <div class="right">
        <div class="wall">
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
                            <img src="../css/images.jpg">
                        </div>
                        <div class="post_data">
                            <div class="name">${userName}</div>
                            <div class="date"><c:out value="${lis.date}"/></div>
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
                    <%--<c:forEach items="${lis.commentList}" var="liss">--%>
                        <%--<div class="post_comments">--%>
                            <%--<div class="post_comments_header">--%>
                                <%--<div class="post_comments_avatar">--%>
                                    <%--<img src="images.jpg">--%>
                                <%--</div>--%>
                                <%--<div class="post_comments_data">--%>
                                    <%--<div class="name">Jack</div>--%>
                                    <%--<div class="date">--%>
                                        <%--23 2017"/>--%>
                                    <%--</div>--%>
                                <%--</div>--%>
                                <%--<div class="post_comments_function" id="function${lis.id}">--%>
                                    <%--<li class="ranksSubmenuItem" style="list-style: none">--%>
                                        <%--<a class="sub" href="/post?id=${lis.id}" id="B">Delete</a>--%>
                                    <%--</li>--%>
                                <%--</div>--%>
                            <%--</div>--%>
                            <%--<div class="comments">${liss.text}</div>--%>
                        <%--</div>--%>
                    <%--</c:forEach>--%>
                    <%--<div class="commentsAdd">--%>
                    <%--<textarea class='autoExpand' rows='2' data-min-rows='2'--%>
                              <%--placeholder='Enter text'></textarea>--%>
                        <%--<input type="button" value="Send">--%>
                    <%--</div>--%>
                </div>
            </c:forEach>
        </div>

        <div class="avatar">
            <img src="../css/images.jpg">
        </div>
    </div>
</div>
</body>
</html>