<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/WEB-INF/views/common/_header.jsp" />
<c:set var="ctx" value="${pageContext.request.contextPath}" />

<div style="display:flex;align-items:center;justify-content:space-between;margin-bottom:14px;">
  <h2 style="margin:0;">Лента</h2>
  <a class="btn" href="${ctx}/posts?new=1">+ Новый пост</a>
</div>

<c:if test="${empty posts}">
  <div class="card hint">Постов пока нет. Создайте первый!</div>
</c:if>

<c:forEach var="p" items="${posts}">
  <%@ include file="/WEB-INF/views/common/post-card.jspf" %>
</c:forEach>

<jsp:include page="/WEB-INF/views/common/_footer.jsp" />
