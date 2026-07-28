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
            <h5 class="mb-3">Recover password</h5>
            <div class="border border-success text-success rounded p-3 mb-3">
                If an account exists, a reset link is prepared below.
            </div>
            <c:if test="${not empty resetLink}">
                <p class="text-secondary small">Писем мы не отправляем — скопируйте ссылку и откройте её, чтобы задать новый пароль:</p>
                <div class="bg-light border rounded p-2 mb-3 small" style="word-break:break-all;">${resetLink}</div>
                <a href="${resetLink}" class="btn text-white w-100 mb-2" style="background-color:#5C7CFA;">Задать новый пароль</a>
            </c:if>
            <a href="${ctx}/login" class="btn btn-outline-primary w-100">BACK TO SIGN IN</a>
        </div>
    </div>
</div>
</body>
</html>
