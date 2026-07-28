<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head><title>Reset Password - Step 2</title></head>
<body>
<h2>Recovery Link Generated</h2>
<p>If this email exists in our system, a password reset link has been generated.</p>

<% if (request.getAttribute("resetLink") != null) { %>
<div style="background: #eee; padding: 15px; margin: 15px 0;">
    <p><strong>[Development Mode] Simulated Email Link:</strong></p>
    <a href="${resetLink}">${resetLink}</a>
</div>
<% } %>
<p><a href="${pageContext.request.contextPath}/login">Back to Login</a></p>
</body>
</html>
