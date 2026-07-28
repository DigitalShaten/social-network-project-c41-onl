<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<jsp:include page="/WEB-INF/views/common/_header.jsp" />
<c:set var="ctx" value="${pageContext.request.contextPath}" />
<c:set var="me" value="${sessionScope.currentUser}" />

<div class="card shadow-sm" style="max-width:640px;margin:0 auto;">
  <div class="card-body p-4">
    <div class="d-flex align-items-center justify-content-between border-bottom pb-2 mb-3">
      <a href="${ctx}/feed" class="text-secondary text-decoration-none">Cancel</a>
      <b>New post</b>
      <a href="${ctx}/feed" class="text-secondary text-decoration-none fs-4 lh-1">&times;</a>
    </div>

    <c:if test="${not empty error}">
      <div class="alert alert-danger py-2">${error}</div>
    </c:if>

    <div class="d-flex align-items-center gap-2 mb-2">
      <span class="avatar-sm"></span>
      <b>${me.userName}</b>
    </div>

    <form method="post" action="${ctx}/posts" enctype="multipart/form-data">
      <textarea name="text" class="form-control border-0 px-0" rows="3"
                placeholder="Type here what's new"></textarea>

      <div class="d-flex align-items-center justify-content-between border-top pt-3 mt-2">
        <label class="btn btn-light mb-0" title="Прикрепить фото (до 5)">
          &#128247;
          <input type="file" name="photos" accept="image/*" multiple hidden>
        </label>
        <button type="submit" class="btn text-white px-4" style="background-color:#5C7CFA;">Post</button>
      </div>
      <div class="form-text">Можно прикрепить не более 5 фотографий.</div>
    </form>
  </div>
</div>

<jsp:include page="/WEB-INF/views/common/_footer.jsp" />
