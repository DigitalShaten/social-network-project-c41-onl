<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/WEB-INF/views/common/_header.jsp"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<h4 class="mb-3">Поиск людей</h4>

<form class="mb-4" method="get" action="${ctx}/users" role="search">
    <div class="input-group">
        <input name="q" type="search" class="form-control" placeholder="Имя или username..."
               value="${param.q}" aria-label="Search">
        <button class="btn btn-outline-primary" type="submit">Искать</button>
    </div>
</form>

<c:if test="${empty users}">
    <div class="text-secondary">Никого не нашлось.</div>
</c:if>

<c:forEach var="user" items="${users}">
    <div class="card mb-2 shadow-sm">
        <div class="card-body d-flex align-items-center gap-3">
            <c:choose>
                <c:when test="${user.currentFileId == null}">
                    <img src="${ctx}/resources/img/icons/userDefaultImage.png"
                         class="rounded-circle border" style="width:48px;height:48px;object-fit:cover;" alt="">
                </c:when>
                <c:otherwise>
                    <img src="${ctx}/files/${user.currentFileId}"
                         class="rounded-circle border" style="width:48px;height:48px;object-fit:cover;" alt="">
                </c:otherwise>
            </c:choose>
            <a class="text-decoration-none fw-semibold text-dark" href="${ctx}/profile?userId=${user.userId}">
                ${user.firstName} ${user.lastName}
            </a>
            <div class="ms-auto">
                <form action="${ctx}/subscription" method="post">
                    <input type="hidden" name="targetUserId" value="${user.userId}">
                    <c:choose>
                        <c:when test="${user.subscribed}">
                            <input type="hidden" name="action" value="unsubscribe">
                            <button type="submit" class="btn btn-sm btn-outline-secondary">Отписаться</button>
                        </c:when>
                        <c:otherwise>
                            <input type="hidden" name="action" value="subscribe">
                            <button type="submit" class="btn btn-sm text-white" style="background-color:#5C7CFA;">Подписаться</button>
                        </c:otherwise>
                    </c:choose>
                </form>
            </div>
        </div>
    </div>
</c:forEach>

<jsp:include page="/WEB-INF/views/common/_footer.jsp"/>
