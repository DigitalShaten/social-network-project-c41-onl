<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="ru">
<head>
    <meta charset="UTF-8">
    <title>404 — страница не найдена</title>
    <style>
        body { margin: 0; background: #000; color: #fff; height: 100vh;
            display: flex; flex-direction: column; align-items: center; justify-content: center;
            font-family: -apple-system, "Segoe UI", Roboto, Arial, sans-serif; text-align: center; }
        .code { font-size: 72px; font-weight: 700; letter-spacing: 8px; }
        .face { font-size: 64px; margin: 10px 0; }
        h1 { font-size: 22px; margin: 14px 0 8px; }
        p { color: #b9c0c7; font-style: italic; max-width: 480px; }
        a { display: inline-block; margin-top: 24px; color: #4c8dff; border: 1px solid #4c8dff;
            border-radius: 8px; padding: 10px 20px; text-decoration: none; }
    </style>
</head>
<body>
<div class="code">4 <span class="face">&#128128;</span> 4</div>
<h1>Looks like this page got lost:(</h1>
<p>It may have been moved, deleted, or simply decided to leave the social network before you did.</p>
<a href="${pageContext.request.contextPath}/feed">BACK TO HOMEPAGE</a>
</body>
</html>
