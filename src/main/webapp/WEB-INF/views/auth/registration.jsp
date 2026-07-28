<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Qwerty! — регистрация</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
    <link rel="stylesheet" href="${ctx}/resources/css/style.css">
</head>
<body class="bg-body-tertiary">
<div class="container" style="max-width:960px;">
    <div class="d-flex align-items-center justify-content-center gap-2 mt-4 mb-4">
        <h2 class="m-0 text-secondary">Qwerty!</h2>
        <img src="${ctx}/resources/img/icons/logo.png" alt="logo" style="height:44px;">
    </div>

    <div class="card shadow-sm">
        <div class="card-body p-4">
            <form action="${ctx}/registration" method="POST">
                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label class="form-label">First name</label>
                        <input type="text" name="firstname" class="form-control" value="${user.firstName}"
                               placeholder="Alexandra" required>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Last name</label>
                        <input type="text" name="lastname" class="form-control" value="${user.lastName}"
                               placeholder="Bogatko">
                    </div>
                </div>

                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Username</label>
                        <div class="input-group">
                            <span class="input-group-text">@</span>
                            <input type="text" name="username" class="form-control ${not empty errors.username ? 'is-invalid' : ''}"
                                   value="${user.userName}" placeholder="alex.bgtk" required>
                            <div class="invalid-feedback">${errors.username}</div>
                        </div>
                    </div>
                    <div class="col-md-3 mb-3">
                        <label class="form-label">Sex</label>
                        <select name="gender" class="form-select">
                            <option value="FEMALE">Female</option>
                            <option value="MALE">Male</option>
                        </select>
                    </div>
                    <div class="col-md-3 mb-3">
                        <label class="form-label">Date Of Birth</label>
                        <input type="date" name="birthday" class="form-control" value="${user.birthday}" required>
                    </div>
                </div>

                <div class="mb-3">
                    <label class="form-label">About</label>
                    <input type="text" name="about" class="form-control" value="${user.about}"
                           placeholder="Junior Senior Pomidor">
                </div>

                <div class="row">
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Email</label>
                        <input type="email" name="email" class="form-control ${not empty errors.email ? 'is-invalid' : ''}"
                               value="${user.email}" required>
                        <div class="invalid-feedback">${errors.email}</div>
                    </div>
                    <div class="col-md-6 mb-3">
                        <label class="form-label">Password</label>
                        <div class="input-group">
                            <input type="password" name="password" id="pwd"
                                   class="form-control ${not empty errors.password ? 'is-invalid' : ''}" required>
                            <button class="btn btn-outline-secondary" type="button" onclick="togglePwd('pwd')">&#128065;</button>
                            <div class="invalid-feedback">${errors.password}</div>
                        </div>
                        <div class="form-text">Your password must be 8-20 characters long, contain letters, numbers
                            and special characters and must not contain spaces or emoji</div>
                    </div>
                </div>

                <div class="form-check mb-3">
                    <input class="form-check-input" type="checkbox" id="agree" required>
                    <label class="form-check-label" for="agree">Agree to term and conditions</label>
                </div>

                <div class="d-flex align-items-center gap-3">
                    <button type="submit" class="btn text-white" style="background-color:#5C7CFA;">Sign in</button>
                    <a href="${ctx}/login" class="text-secondary small">Уже есть аккаунт? Войти</a>
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
