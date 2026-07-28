<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head><title>Reset Password - Step 3</title></head>
<body>
<h2>Create New Password</h2>

<% if (request.getAttribute("error") != null) { %>
<div style="color: red;">${error}</div>
<% } %>

<form action="${pageContext.request.contextPath}/recovery/reset" method="POST">
    <!-- Скрытое поле для сохранения токена между запросами -->
    <input type="hidden" name="token" value="${param.token != null ? param.token : requestScope.token}" />
    <div>
        <label>New Password:</label>
        <input type="password" name="password" required />
    </div>
    <button type="submit">Save Password</button>
</form>
</body>
</html>
