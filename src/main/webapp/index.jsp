<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>JSP - Hello World</title>
</head>
<body>
<jsp:include page="/WEB-INF/views/common/_header.jsp"/>

<h1><%= "Hello World!" %>
</h1>
<a href="hello-servlet">Hello Servlet</a>

<jsp:include page="/WEB-INF/views/common/_footer.jsp"/>
<br/>
</body>
</html>