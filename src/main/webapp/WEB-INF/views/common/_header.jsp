<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link rel="stylesheet" href="/resources/css/style.css">
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
</head>
<body>
<!-- Логотип и название -->
<div class="container-fluid d-flex align-items-center">
    <img src="/resources/img/icons/logo.png" alt="logo" class="img-fluid" style="max-width: 40px;">
    <h5 class="mb-0">Qwerty!</h5>
</div>

<!-- Меню -->
<nav class="navbar navbar-expand-lg bg-light border-top border-bottom border-dark p-0 mt-0 mb-4">
    <div class="container-fluid d-flex justify-content-between align-items-center">

        <!-- Домик -->
        <div class="d-flex align-items-center">
            <a href="#" class="btn btn-light p-0">
                <img src="/resources/img/icons/home.png" alt="Home" class="img-fluid" style="max-width: 60px;">
            </a>
        </div>

        <!-- Иконка "+" -->
        <div class="d-flex align-items-center">
            <a href="#" class="btn btn-light px-5">
                <img src="/resources/img/icons/plus.png" width="30" alt="Add">
            </a>
        </div>


        <div class="d-flex align-items-center gap-3">

            <!-- Профиль -->
            <a href="#" class="btn btn-light p-0">
                <img src="/resources/img/icons/userDefaultImage.png" width="30" alt="Profile">
            </a>

            <!-- Выйти -->
            <a href="/logout" class="btn px-3 py-1 rounded-3"
               style="background-color:#5C7CFA; color:white; border:none;">
                Выйти
            </a>
        </div>

    </div>
</nav>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-FKyoEForCGlyvwx9Hj09JcYn3nv7wiPVlz7YYwJrWVcXK/BmnVDxM+D2scQbITxI"
        crossorigin="anonymous"></script>
</body>
</html>
