<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <title>Search</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/_header.jsp"/>
<div class="container">
    <form class="d-flex mb-3" role="search">
        <div class="input-group">
            <input name="q" type="search" class="form-control" placeholder="Search..." aria-label="Search">
            <button class="btn btn-outline-primary" type="submit">Search</button>
        </div>
    </form>
    <c:forEach var="user" items="${users}">
        <div class="card text-center">
            <div class="card-body">
                <div class="row">
                    <div class="col-4">
                        <c:choose>
                            <c:when test="${user.currentFileId == null}">
                                <img src="${pageContext.request.contextPath}/resources/img/icons/userDefaultImage.png"
                                     alt="Default Photo" class="img-fluid h-50">
                            </c:when>
                            <c:otherwise>
                                <img src="${pageContext.request.contextPath}/files/${user.currentFileId}"
                                     alt="User Photo" class="img-fluid h-50">
                            </c:otherwise>
                        </c:choose>
                    </div>
                    <div class="col-4">
                        <h5 class="card-title">${user.firstName} ${user.lastName}</h5>
                    </div>
                    <c:if test="${!user.subscribed}">
                        <div class="col-4">
                            <button class="btn btn-primary">Subscribe</button>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </c:forEach>

</div>
<jsp:include page="/WEB-INF/views/common/_footer.jsp"/>
<br/>
</body>
</html>
