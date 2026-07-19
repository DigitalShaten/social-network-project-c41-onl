<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8">
  <title>500 — ошибка сервера</title>
  <style>
    body { margin: 0; background: #000; color: #fff; height: 100vh;
      display: flex; flex-direction: column; align-items: center; justify-content: center;
      font-family: -apple-system, "Segoe UI", Roboto, Arial, sans-serif; text-align: center; }
    .code { font-size: 72px; font-weight: 700; letter-spacing: 8px; }
    .tool { color: #4c8dff; }
    h1 { font-size: 22px; margin: 18px 0 8px; }
    p { color: #b9c0c7; }
    a { display: inline-block; margin-top: 24px; color: #4c8dff; border: 1px solid #4c8dff;
      border-radius: 8px; padding: 10px 20px; text-decoration: none; }
  </style>
</head>
<body>
<div class="code">5 <span class="tool">&#128736;</span> 5</div>
<h1>Our server needs a little break.</h1>
<p>Something unexpected happened.</p>
<p>Please try again later.</p>
<a href="${pageContext.request.contextPath}/feed">BACK TO HOMEPAGE</a>
</body>
</html>
