<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Qwerty!</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.8/dist/css/bootstrap.min.css" rel="stylesheet"
          integrity="sha384-sRIl4kxILFvY47J16cr9ZwB07vP4J8+LH7qKQnuqkuIAvNWLzeN8tE5YBujZqJLB" crossorigin="anonymous">
    <link rel="stylesheet" href="${ctx}/resources/css/style.css">
</head>
<body class="bg-body-tertiary">

<nav class="navbar bg-white border-bottom sticky-top">
    <div class="container-fluid px-4 d-flex align-items-center">
        <a class="navbar-brand d-flex align-items-center gap-2" href="${ctx}/feed">
            <img src="${ctx}/resources/img/icons/logo.png" alt="logo" style="max-width:34px;">
            <span class="fw-semibold">Qwerty!</span>
        </a>

        <div class="d-flex align-items-center gap-4 ms-auto">
            <a href="${ctx}/feed" title="Лента" class="nav-icon">
                <img src="${ctx}/resources/img/icons/home.png" alt="Home" style="height:26px;">
            </a>
            <a href="${ctx}/posts?new=1" title="Новый пост" class="nav-icon">
                <img src="${ctx}/resources/img/icons/plus.png" alt="Add" style="height:24px;">
            </a>
            <span class="nav-icon text-secondary" title="Сообщения (скоро)"
                  style="opacity:.4;cursor:default;font-size:22px;">&#9993;</span>
            <a href="${ctx}/profile" title="Профиль" class="nav-icon">
                <img src="${ctx}/resources/img/icons/userDefaultImage.png" alt="Profile"
                     class="rounded-circle border" style="height:30px;width:30px;object-fit:cover;">
            </a>
            <a href="${ctx}/logout" class="btn btn-sm text-white px-3" style="background-color:#5C7CFA;">Выйти</a>
        </div>
    </div>
</nav>

<main class="container py-4" style="max-width:1000px;"/>