<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="jakarta.tags.functions" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html>
<head>
    <title>Registration</title>
    <style>
        body {
            background-color: #ffffff;
            font-family: Arial, sans-serif;
            display: flex;
            justify-content: center;
            align-items: center;
            min-height: 100vh;
            margin: 0;
            padding: 20px;
            box-sizing: border-box;
        }

        .registration-card {
            background-color: #e8eaed;
            border-radius: 8px;
            box-shadow: 0 4px 12px rgba(0, 0, 0, 0.05);
            width: 100%;
            max-width: 1475px;
            padding: 50px;
            margin: 20px;
            box-sizing: border-box;
        }

        .header-container {
            display: flex;
            justify-content: center;
            align-items: center;
            gap: 15px;
            margin-bottom: 40px;
        }

        .header-title {
            font-size: 42px;
            font-weight: bold;
            color: #333333;
            margin: 0;
        }

        .form-grid {
            display: flex;
            flex-direction: column;
            gap: 20px;
        }


        .form-row {
            display: grid;
            gap: 20px;
            width: 100%;
        }

        .form-row-1 { grid-template-columns: 3fr 4fr; }
        .form-row-2 { grid-template-columns: 2fr 1fr 1fr; }
        .form-row-4 { grid-template-columns: 1fr 1fr; }

        .form-group {
            display: flex;
            flex-direction: column;
            width: 100%;
        }

        .form-label {
            font-size: 14px;
            color: #333333;
            margin-bottom: 8px;
            font-weight: bold;
        }

        .form-control {
            width: 100%;
            height: 44px;
            box-sizing: border-box;
            font-size: 15px;
            color: #495057;
            background-color: #ffffff;
            border: 1px solid #cccccc;
            border-radius: 6px;
            padding: 0 14px;
            outline: none;
            transition: border-color 0.2s;
        }

        .form-control:focus {
            border-color: #a0a0a0;
        }

        .username-group {
            display: flex;
            height: 44px;
            border: 1px solid #cccccc;
            border-radius: 6px;
            overflow: hidden;
            background-color: #ffffff;
        }

        .username-prefix {
            background-color: #f8f9fa;
            border-right: 1px solid #cccccc;
            color: #6c757d;
            display: flex;
            align-items: center;
            padding: 0 14px;
            font-size: 15px;
            user-select: none;
        }

        .username-group .form-control {
            border: none;
            border-radius: 0;
            height: 100%;
        }

        select.form-control {
            appearance: none;
            -webkit-appearance: none;
            -moz-appearance: none;
            background-image: url("data:image/svg+xml;charset=UTF-8,<svg xmlns='http://www.w3.org/2000/svg' width='24' height='24' viewBox='0 0 24 24' fill='none' stroke='gray' stroke-width='2' stroke-linecap='round' stroke-linejoin='round'><path d='m7 15 5 5 5-5M7 9l5-5 5 5'/></svg>");
            background-repeat: no-repeat;
            background-position: right 14px center;
            background-size: 16px;
            cursor: pointer;
            padding-right: 40px;
        }

        input[type="date"].form-control {
            font-family: Arial, sans-serif;
            cursor: pointer;
        }

        .password-wrapper {
            position: relative;
            display: flex;
            align-items: center;
            width: 100%;
        }

        .password-wrapper .form-control {
            padding-right: 45px;
        }

        .toggle-password {
            position: absolute;
            right: 14px;
            background: none;
            border: none;
            padding: 0;
            cursor: pointer;
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .form-hint {
            font-size: 14px;
            color: #999999;
            line-height: 1.4;
            margin-top: 6px;
            max-width: 400px;
        }

        .form-control-gray {
            background-color: #e8eaed !important;
            border: 1px solid #7a7a7a !important;
            color: #333333;
        }

        .checkbox-group {
            display: flex;
            align-items: center;
            gap: 8px;
            margin-top: 10px;
            font-size: 14px;
            color: #333333;
        }

        .checkbox-group input {
            width: 16px;
            height: 16px;
            cursor: pointer;
        }

        .btn-submit {
            background-color: #2b85ff;
            color: #ffffff;
            font-size: 15px;
            border: none;
            border-radius: 6px;
            padding: 12px 24px;
            cursor: pointer;
            width: max-content;
            margin-top: 10px;
            transition: background-color 0.2s;
        }

        .btn-submit:hover {
            background-color: #0069efff;
        }
    </style>
</head>
<body>

<div class="registration-card">

    <!-- Заголовок и логотип.  -->
    <div class="header-container">
        <h1 class="header-title">Qwerty!</h1>
        <img src="/resources/img/icons/logo.png" alt="logo" class="img-fluid" style="max-width: 80px;">
    </div>

    <!-- Форма -->
    <form action="" method="POST" class="form-grid">

        <!-- Ряд 1 -->
        <div class="form-row form-row-1">
            <div class="form-group">
                <label class="form-label" for="first-name">First name</label>
                <input name="firstname" class="form-control" type="text" id="first-name" placeholder="Alexandra"  value="${user.firstName}" required>
            </div>
            <div class="form-group">
                <label class="form-label" for="last-name">Last name</label>
                <input name="lastname" class="form-control" type="text" id="last-name" placeholder="Bogatko" value="${user.lastName}" required>
            </div>
        </div>

        <!-- Ряд 2 -->
        <div class="form-row form-row-2">
            <div class="form-group">
                <label class="form-label" for="username">Username</label>
                <div class="username-group">
                    <span class="username-prefix">@</span>
                    <input name="username"
                           class="form-control ${not empty errors['username'] ? 'is-invalid' : ''}"
                           type="text"
                           id="username"
                           placeholder="alex.bgtk"
                           required
                           value="${fn:escapeXml(param.username)}">
                </div>
                <c:if test="${not empty errors.username}">
                    <div class="error-message" style="color: red; font-size: 0.85em; margin-top: 5px;">
                            ${errors.username}
                    </div>
                </c:if>
            </div>
            <div class="form-group">
                <label class="form-label" for="sex">Sex</label>
                <select name="gender" class="form-control" id="sex">
                    <option>Female</option>
                    <option>Male</option>
                </select>
            </div>
            <div class="form-group">
                <label class="form-label" for="dob">Date Of Birth</label>
                <input name="birthday" class="form-control" type="date" id="dob" value="${user.birthday}" required>
            </div>
        </div>

        <!-- Ряд 3 -->
        <div class="form-group">
            <label class="form-label" for="about">About</label>
            <input name="about" class="form-control" type="text" id="about" placeholder="Junior Senior Pomidor" value="${user.about}">
        </div>

        <!-- Ряд 4 -->
        <div class="form-row form-row-4">
            <div class="form-group">
                <label class="form-label">Email</label>
                <input name="email"
                       class="form-control form-control-gray form-control-gray ${not empty errors.email ? 'is-invalid' : ''}"
                       type="text"
                       value="${fn:escapeXml(param.email)}"
                       required>
                <c:if test="${not empty errors.email}">
                    <div class="error-message" style="color: red; font-size: 0.85em; margin-top: 5px;">
                            ${errors.email}
                    </div>
                </c:if>
            </div>
            <div class="form-group">
                <label class="form-label">Password</label>
                <div class="password-wrapper">
                    <input name="password"
                           class="form-control form-control-gray ${not empty errors.password ? 'is-invalid' : ''}"
                           type="password"
                           id="password-input"
                           placeholder="12345678"
                           required>
                    <button type="button" class="toggle-password" id="toggle-password-btn">
                        <img src="/resources/img/icons/eye-icon.png" alt="Показать пароль" width="50" height="30"></button>
                </div>
                <div>
                    <span class="form-hint"> Your password must be 8-20 characters long, contain letters, numbers and special characters and must not contain spaces or emoji.</span>
                </div>
                <c:if test="${not empty errors['password']}">
                    <div class="error-message" style="color: red; font-size: 0.85em; margin-top: 5px; font-weight: bold;">
                            ${errors['password']}
                    </div>
                </c:if>
            </div>
        </div>

        <div class="checkbox-group">
            <input name="checkbox" class="form-check-input" type="checkbox" value="" id="invalidCheck" required>
            <label class="form-check-label" for="invalidCheck">
                Agree to terms and conditions
            </label>
        </div>

        <!-- Кнопка отправки -->
        <button type="submit" class="btn-submit">Sign in</button>

    </form>
</div>
<script>
    const passwordInput = document.getElementById('password-input');
    const togglePasswordBtn = document.getElementById('toggle-password-btn');

    togglePasswordBtn.addEventListener('click', function () {
        const isPassword = passwordInput.getAttribute('type') === 'password';
        passwordInput.setAttribute('type', isPassword ? 'text' : 'password');
    });
</script>

</body>
</html>

