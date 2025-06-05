<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../css/login.css">
    <title>Callisto Bank - Login</title>

</head>

<body class="cliente-mode">
    <div class="background-layer bg-cliente"></div>
    <div class="background-layer bg-funcionario"></div>

    <div class="login-container">
        <div class="welcome-section">
            <div class="logo">
                <img src="../img/image.svg" alt="Callisto Bank" class="logo-image">
            </div>
            <h1 class="welcome-title">Bem vindo!</h1>
            <h2 class="welcome-subtitle" id="welcome-subtitle">Acesso do Cliente</h2>
            <p class="welcome-description" id="welcome-description">Gerencie suas finanças com segurança</p>
            
            <div class="toggle-section">
                <p class="toggle-text" id="toggle-text">Acesso para funcionários?</p>
                <button type="button" class="toggle-btn" id="toggle-btn" onclick="toggleMode()">Portal do Funcionário</button>
            </div>
        </div>

        <div class="form-container">
            <form class="login-form" id="loginForm">
                <h2 class="form-title" id="form-title">Login Cliente</h2>

                <div class="error-message" id="error-message"></div>
                <div class="success-message" id="success-message"></div>

                <div class="form-group" id="identifier-group">
                    <label class="form-label" id="identifier-label">CPF</label>
                    <input type="text" class="form-input" id="identifier-input" placeholder="000.000.000-00" required>
                </div>

                <div class="form-group">
                    <label class="form-label">Senha</label>
                    <div class="password-container">
                        <input type="password" class="form-input" placeholder="Senha" id="password-input" required>
                        <button type="button" class="password-toggle" onclick="togglePassword()">👁️</button>
                    </div>
                </div>

                <div class="form-group">
                    <label class="form-label">Código OTP (6 dígitos)</label>
                    <div class="otp-container">
                        <input type="text" class="otp-input" maxlength="1" pattern="[0-9]" id="otp1">
                        <input type="text" class="otp-input" maxlength="1" pattern="[0-9]" id="otp2">
                        <input type="text" class="otp-input" maxlength="1" pattern="[0-9]" id="otp3">
                        <input type="text" class="otp-input" maxlength="1" pattern="[0-9]" id="otp4">
                        <input type="text" class="otp-input" maxlength="1" pattern="[0-9]" id="otp5">
                        <input type="text" class="otp-input" maxlength="1" pattern="[0-9]" id="otp6">
                    </div>
                </div>

                <p class="form-note">Use pelo menos 8 caracteres com 1 número e um caractere especial.</p>

                <button type="submit" class="login-btn" id="login-btn">Entrar como Cliente</button>

                <div class="forgot-password">
                    <a href="#forgot">Esqueceu a senha?</a>
                </div>

                <div class="toggle-section">
                    <button type="button" class="exit-btn">Sair</button>
                </div>
            </form>
        </div>
    </div>
    <script src="../js/login.js"></script>
</body>
</html>