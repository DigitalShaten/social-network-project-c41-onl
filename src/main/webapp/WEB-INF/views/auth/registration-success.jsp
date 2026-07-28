<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Registration success</title>
</head>
<body style="background-color: #e8eaed;">
<h3>Регистрация почти завершена!</h3>
<!-- Поставить ссылку правильно -->
<p>Чтобы активировать аккаунт, перейдите по ссылке:
    <a id="activationLink" href="${pageContext.request.contextPath}/registration/confirm?token=${tokenUuid}">
        http://localhost:8080/registration/confirm?token=${tokenUuid}</a></p>
<button style="background-color: #2b85ff;
            color: #ffffff;
            font-size: 15px;
            border: none;
            border-radius: 6px;
            padding: 12px 24px;
            cursor: pointer;
            width: max-content;
            margin-top: 10px;
            transition: background-color 0.2s;" onclick="copyText()">Скопировать</button>
<script>
    function copyText() {
        const link = document.getElementById("activationLink").href;
        navigator.clipboard.writeText(link);
    }
</script>
</body>
</html>
