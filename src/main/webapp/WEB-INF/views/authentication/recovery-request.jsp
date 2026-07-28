<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<html>
<head><title>Reset Password - Step 1</title></head>
<body>
<h2>Password Recovery</h2>
<form action="${pageContext.request.contextPath}/recovery" method="POST">
  <p>Enter your email address to receive a reset link.</p>
  <div>
    <label>Email:</label>
    <input type="email" name="email" required />
  </div>
  <button type="submit">Get Reset Link</button>
</form>
</body>
</html>