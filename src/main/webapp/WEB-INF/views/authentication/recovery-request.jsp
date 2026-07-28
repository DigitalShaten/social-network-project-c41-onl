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
            <p class="text-secondary small">Enter the email address you used to register and we'll prepare a reset link for you.</p>
            <form action="${ctx}/recovery" method="POST">
                <div class="mb-3">
                    <input type="email" name="email" class="form-control" placeholder="email@example.com" required>
                </div>
                <div class="d-grid gap-2">
                    <button type="submit" class="btn text-white" style="background-color:#5C7CFA;">RESET PASSWORD</button>
                    <a href="${ctx}/login" class="btn btn-outline-primary">BACK TO SIGN IN</a>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>
