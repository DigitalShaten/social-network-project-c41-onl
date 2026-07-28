<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/WEB-INF/views/common/_header.jsp" />
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<div class="d-flex align-items-center justify-content-between mb-3">
  <h2 class="m-0">Лента</h2>
  <a class="btn text-white" style="background-color:#5C7CFA;" href="${ctx}/posts?new=1">+ Новый пост</a>
</div>

<ul class="nav nav-pills mb-3">
  <li class="nav-item">
    <a class="nav-link ${filter == 'subscriptions' ? '' : 'active'}" href="${ctx}/feed?filter=all">Все</a>
  </li>
  <li class="nav-item">
    <a class="nav-link ${filter == 'subscriptions' ? 'active' : ''}" href="${ctx}/feed?filter=subscriptions">Подписки</a>
  </li>
</ul>

<c:if test="${empty posts}">
  <c:choose>
    <c:when test="${filter == 'subscriptions'}">
      <div class="card hint">В ленте подписок пока пусто — подпишитесь на кого-нибудь.</div>
    </c:when>
    <c:otherwise>
      <div class="card hint">Постов пока нет. Создайте первый!</div>
    </c:otherwise>
  </c:choose>
</c:if>

<c:forEach var="p" items="${posts}">
  <%@ include file="/WEB-INF/views/common/post-card.jspf" %>
</c:forEach>

<jsp:include page="/WEB-INF/views/common/_footer.jsp" />
