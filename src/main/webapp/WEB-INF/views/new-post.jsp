<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/WEB-INF/views/common/_header.jsp" />
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="me" value="${sessionScope.currentUser}" />

<div class="card">
  <div style="display:flex;align-items:center;justify-content:space-between;">
    <a href="${ctx}/feed" class="hint">Cancel</a>
    <b>New post</b>
    <span></span>
  </div>
  <hr style="border:none;border-top:1px solid var(--line);margin:14px 0;">
  <c:if test="${not empty error}">
    <div class="alert alert-error">${error}</div>
  </c:if>
  <div class="post-head">
    <span class="avatar-sm"></span>
    <span class="name">${me.userName}</span>
  </div>
  <form method="post" action="${ctx}/posts" enctype="multipart/form-data">
    <textarea name="text" placeholder="Type here what's new"></textarea>
    <label>Фото (можно несколько)</label>
    <input type="file" name="photos" accept="image/*" multiple>
    <div class="btn-row" style="justify-content:flex-end;">
      <button type="submit" class="btn">Post</button>
    </div>
  </form>
</div>

<jsp:include page="/WEB-INF/views/common/_footer.jsp" />
