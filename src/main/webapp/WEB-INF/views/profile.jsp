<%--
  Created by IntelliJ IDEA.
  User: Aleksei Borzetsov
  Date: 25.07.2026
  Time: 11:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%
    response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate"); // HTTP 1.1
    response.setHeader("Pragma", "no-cache"); // HTTP 1.0
    response.setDateHeader("Expires", 0); // Прокси-серверы
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/_header.jsp"/>
<div class="container ms-1">
    <div class="row">
        <div class="col-6">
            <div class="card">
                <div class="row g-0">
                    <div class="col-2">
                        <img src="/resources/img/icons/userDefaultImage.png" class="img-fluid rounded-start" alt="...">
                    </div>
                    <div class="col">
                        <div class="card-body">
                            <h5 class="card-title">${profileDto.firstName} ${profileDto.lastName}</h5>
                            <p class="card-text"><small class="text-body-secondary">${profileDto.birthDay}</small></p>
                            <p class="card-text"><small class="text-body-secondary">About</small></p>
                            <p class="card-text">${profileDto.about}</p>
                        </div>
                    </div>
                    <div class="col-5">
                        <div class="row mt-4">
                            <div class="col-6" style="min-width: 100px;">
                                <p>Posts</p>
                            </div>
                            <div class="col">
                                <span class="badge rounded-pill text-bg-primary">${profileDto.postsCounter}</span>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-6" style="min-width: 100px;">
                                <p>Followers</p>
                            </div>
                            <div class="col">
                                <span class="badge rounded-pill text-bg-primary">${profileDto.subscriptionsCounter}</span>
                            </div>
                        </div>
                        <div class="row">
                            <div class="col-6" style="min-width: 100px;">
                                <p>Following</p>
                            </div>
                            <div class="col">
                                <span class="badge rounded-pill text-bg-primary">${profileDto.followersCounter}</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <!-- Если зашли на страницу профиля другого пользователя -->
    <c:if test="${currentUser == false}">
        <div class="row">
            <!-- Кнопка подписаться/отписаться -->
            <div class="col-6">
                <form action="${pageContext.request.contextPath}" method="post">
                    <c:if test="${profileDto.subscribed == true}">
                        <button type="submit" name="subscribe" value="false" class="btn btn-primary">Отписаться</button>
                    </c:if>
                    <c:if test="${profileDto.subscribed == false}">
                        <button type="submit" name="subscribe" value="true" class="btn btn-primary">Подписаться</button>
                    </c:if>
                </form>
            </div>
        </div>
    </c:if>
</div>
<jsp:include page="/WEB-INF/views/common/_footer.jsp"/>
<br/>
</body>
</html>
