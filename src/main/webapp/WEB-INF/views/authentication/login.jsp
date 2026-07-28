
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head>
    <title>Login</title>
    <style>.error-box { color: red; background: #fee; border: 1px solid red; padding: 10px; margin-bottom: 15px; }</style>
</head>
<body>
<h2>Sign In</h2>

<% if (request.getAttribute("error") != null) { %>
<div class="error-box">${error}</div>
<% } %>

<form action="${pageContext.request.contextPath}/login" method="POST">
    <div>
        <label>Email:</label>
        <input type="email" name="email" value="${fn:escapeXml(param.email)}" required />
    </div>
    <div>
        <label>Password:</label>
        <input type="password" name="password" required />
    </div>
    <div>
        <label>
            <input type="checkbox" name="rememberMe" value="true" /> Remember me
        </label>
    </div>
    <button type="submit">Sign In</button>
</form>
<p><a href="${pageContext.request.contextPath}/recovery">Forgot password?</a></p>
</body>
</html>
