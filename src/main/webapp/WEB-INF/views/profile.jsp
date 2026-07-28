<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/WEB-INF/views/common/_header.jsp"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<div class="card shadow-sm">
    <div class="card-body">
        <div class="row g-3 align-items-center">
            <div class="col-auto">
                <c:choose>
                    <c:when test="${profileDto.avatarFileId > 0}">
                        <img src="${ctx}/files/${profileDto.avatarFileId}" class="rounded-circle border"
                             style="width:96px;height:96px;object-fit:cover;" alt="avatar">
                    </c:when>
                    <c:otherwise>
                        <img src="${ctx}/resources/img/icons/userDefaultImage.png" class="rounded-circle border"
                             style="width:96px;height:96px;object-fit:cover;" alt="avatar">
                    </c:otherwise>
                </c:choose>
            </div>
            <div class="col">
                <h4 class="mb-0">${profileDto.userName}</h4>
                <div class="text-secondary">${profileDto.firstName} ${profileDto.lastName}</div>
                <c:if test="${not empty profileDto.birthDay}">
                    <div class="text-secondary small">${profileDto.birthDay}</div>
                </c:if>
                <div class="mt-2">${profileDto.about}</div>
            </div>
            <div class="col-auto">
                <div class="d-flex gap-4 text-center">
                    <div><div class="fs-5 fw-semibold">${profileDto.postsCounter}</div><div class="text-secondary small">Posts</div></div>
                    <div><div class="fs-5 fw-semibold">${profileDto.followersCounter}</div><div class="text-secondary small">Followers</div></div>
                    <div><div class="fs-5 fw-semibold">${profileDto.subscriptionsCounter}</div><div class="text-secondary small">Following</div></div>
                </div>
            </div>
        </div>

        <!-- Кнопка подписки только для чужого профиля -->
        <c:if test="${currentUser == false}">
            <div class="mt-3">
                <form action="${ctx}/subscription" method="post">
                    <input type="hidden" name="targetUserId" value="${profileDto.userId}">
                    <c:choose>
                        <c:when test="${profileDto.subscribed}">
                            <input type="hidden" name="action" value="unsubscribe">
                            <button type="submit" class="btn btn-outline-secondary">Отписаться</button>
                        </c:when>
                        <c:otherwise>
                            <input type="hidden" name="action" value="subscribe">
                            <button type="submit" class="btn text-white" style="background-color:#5C7CFA;">Подписаться</button>
                        </c:otherwise>
                    </c:choose>
                </form>
            </div>
        </c:if>
        <c:if test="${currentUser == true}">
            <div class="mt-3">
                <a href="${ctx}/profile/edit" class="btn btn-outline-secondary">Редактировать профиль</a>
            </div>
        </c:if>
    </div>
</div>

<h5 class="mt-4 mb-3">Посты</h5>
<c:if test="${empty profileDto.posts}">
    <div class="text-secondary">Постов пока нет.</div>
</c:if>
<c:forEach var="p" items="${profileDto.posts}">
    <%@ include file="/WEB-INF/views/common/post-card.jspf" %>
</c:forEach>

<jsp:include page="/WEB-INF/views/common/_footer.jsp"/>
