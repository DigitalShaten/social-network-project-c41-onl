<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Recover password</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
    <link rel="stylesheet" href="${ctx}/resources/css/style.css">
</head>
<body class="bg-body-tertiary">
<div class="container" style="max-width:440px;">
    <div class="card shadow-sm mt-5">
        <div class="card-body p-4">
            <h5 class="mb-2">Recover password</h5>
            <p class="text-secondary small">Create a new strong password: 8-20 characters, letters, numbers and special
                characters, no spaces or emoji.</p>
            <c:if test="${not empty error}">
                <div class="alert alert-danger py-2">${error}</div>
            </c:if>
            <form action="${ctx}/recovery/reset" method="POST">
                <input type="hidden" name="token" value="${param.token != null ? param.token : requestScope.token}">
                <div class="mb-3">
                    <div class="input-group">
                        <input type="password" name="password" id="pwd" class="form-control" placeholder="New password" required>
                        <button class="btn btn-outline-secondary" type="button" onclick="togglePwd('pwd')">&#128065;</button>
                    </div>
                </div>
                <div class="d-grid">
                    <button type="submit" class="btn text-white" style="background-color:#5C7CFA;">LOGIN WITH A NEW PASSWORD</button>
                </div>
            </form>
        </div>
    </div>
</div>
<script>
    function togglePwd(id) {
        const el = document.getElementById(id);
        el.type = el.type === 'password' ? 'text' : 'password';
    }
</script>
</body>
</html>
