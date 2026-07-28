<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Welcome to Qwerty!</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/style.css">
</head>
<body class="bg-body-tertiary">
<div class="container" style="max-width:560px;">
    <div class="d-flex align-items-center justify-content-center gap-2 mt-5 mb-4">
        <h3 class="m-0 text-secondary">Welcome to Qwerty!</h3>
        <img src="${pageContext.request.contextPath}/resources/img/icons/logo.png" alt="logo" style="height:40px;">
    </div>

    <div class="card shadow-sm">
        <div class="card-body p-4">
            <% if (request.getAttribute("message") != null) { %>
                <div class="alert alert-success py-2">${message}</div>
            <% } %>
            <% if (request.getAttribute("error") != null) { %>
                <div class="alert alert-danger py-2">${error}</div>
            <% } %>

            <form action="${pageContext.request.contextPath}/login" method="POST">
                <div class="mb-3">
                    <label class="form-label">Email address</label>
                    <input type="email" name="email" class="form-control" value="${param.email}"
                           placeholder="alex.bgtk@gmail.com" required>
                </div>
                <div class="mb-3">
                    <label class="form-label">Password</label>
                    <div class="input-group">
                        <input type="password" name="password" id="pwd" class="form-control" required>
                        <button class="btn btn-outline-secondary" type="button" onclick="togglePwd('pwd')">&#128065;</button>
                    </div>
                </div>
                <div class="form-check mb-3">
                    <input class="form-check-input" type="checkbox" name="rememberMe" value="true" id="remember">
                    <label class="form-check-label" for="remember">Check me out</label>
                </div>
                <div class="d-flex align-items-center gap-2">
                    <button type="submit" class="btn text-white" style="background-color:#5C7CFA;">Log in</button>
                    <a href="${pageContext.request.contextPath}/registration" class="btn btn-outline-primary">Sign in</a>
                    <a href="${pageContext.request.contextPath}/recovery" class="btn btn-light ms-auto">Forgot your password?</a>
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
