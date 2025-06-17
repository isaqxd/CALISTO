<%@ page contentType="text/html;charset=UTF-8" %>
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
        <h1 class="welcome-title">Bem-vindo!</h1>
        <h2 class="welcome-subtitle" id="welcome-subtitle">Acesso do Cliente</h2>
        <p class="welcome-description" id="welcome-description">Gerencie suas finanças com segurança</p>

        <div class="toggle-section">
            <p class="toggle-text" id="toggle-text">Acesso para funcionários?</p>
            <button type="button" class="toggle-btn" id="toggle-btn" onclick="toggleMode()">Portal do Funcionário</button>
        </div>
    </div>

    <!-- Formulário do Cliente -->
    <div class="form-container" id="cliente-form">
        <form class="login-form" action="${pageContext.request.contextPath}/loginCliente" method="post">
            <h2 class="form-title">Login Cliente</h2>
            <input type="hidden" name="tipo_usuario" value="CLIENTE">

            <%-- Mensagem de erro --%>
            <% if (request.getParameter("msg") != null && request.getParameter("msg").equals("erro")) { %>
            <div class="error-message" style="display: block;">
                CPF ou senha incorretos!
            </div>
            <% } %>

            <div class="form-group">
                <label class="form-label" for="cpf-cliente">CPF</label>
                <input type="text" id="cpf-cliente" name="cpf" class="form-input" placeholder="000.000.000-00" required>
            </div>

            <div class="form-group">
                <label class="form-label" for="senha-cliente">Senha</label>
                <div class="password-container">
                    <input type="password" name="senha" id="senha-cliente" class="form-input" placeholder="Digite sua senha" required>
                    <button type="button" class="password-toggle" onclick="togglePassword('senha-cliente')">
                        <img id="icon-senha-cliente" src="../img/iconeyeopen.png" alt="Mostrar senha" width="20">
                    </button>
                </div>
            </div>

            <p class="form-note">Use pelo menos 8 caracteres com 1 número e um caractere especial.</p>

            <button type="submit" class="login-btn">Entrar como Cliente</button>

            <div class="forgot-password">
                <a href="#forgot">Esqueceu a senha?</a>
            </div>

            <div class="toggle-section">
                <button type="button" class="exit-btn" onclick="window.location.href='../index.jsp'">Sair</button>
            </div>
        </form>
    </div>

    <!-- Formulário do Funcionário -->
    <div class="form-container" id="funcionario-form">
        <form class="login-form" action="${pageContext.request.contextPath}/loginFuncionario" method="post">
            <h2 class="form-title">Login Funcionário</h2>
            <input type="hidden" name="tipo_usuario" value="FUNCIONARIO">

            <div class="form-group">
                <label class="form-label" for="cpf-func">CPF do Funcionário</label>
                <input type="text" id="cpf-func" name="cpf" class="form-input" placeholder="000.000.000-00" required>
            </div>

            <div class="form-group">
                <label class="form-label" for="senha-func">Senha</label>
                <div class="password-container">
                    <input type="password" name="senha" id="senha-func" class="form-input" placeholder="Digite sua senha" required>
                    <button type="button" class="password-toggle" onclick="togglePassword('senha-func')">
                        <img id="icon-senha-func" src="../img/iconeyeopen.png" alt="Mostrar senha" width="20">
                    </button>
                </div>
            </div>

            <p class="form-note">Use pelo menos 8 caracteres com 1 número e um caractere especial.</p>

            <button type="submit" class="login-btn">Entrar como Funcionário</button>

            <div class="forgot-password">
                <a href="#forgot">Esqueceu a senha?</a>
            </div>

            <div class="toggle-section">
                <button type="button" class="exit-btn" onclick="window.location.href='../index.jsp'">Sair</button>
            </div>
        </form>
    </div>

    <!-- Formulário OTP -->
    <%
        String otpParam = request.getParameter("otp_true");
        boolean mostrarFormularioOtp = "true".equals(otpParam);
    %>

    <div class="form-container" id="otp-form" style="display: <%= mostrarFormularioOtp ? "flex" : "none" %>;">
        <form class="login-form" action="${pageContext.request.contextPath}/loginOtp" method="post">
            <h2 class="form-title">Verificação OTP</h2>

            <div class="form-group">
                <label class="form-label" for="otp">Digite o código OTP</label>
                <div class="otp-container">
                    <input type="text" class="otp-input" name="otp1" maxlength="1" pattern="[0-9]">
                    <input type="text" class="otp-input" name="otp2" maxlength="1" pattern="[0-9]">
                    <input type="text" class="otp-input" name="otp3" maxlength="1" pattern="[0-9]">
                    <input type="text" class="otp-input" name="otp4" maxlength="1" pattern="[0-9]">
                    <input type="text" class="otp-input" name="otp5" maxlength="1" pattern="[0-9]">
                    <input type="text" class="otp-input" name="otp6" maxlength="1" pattern="[0-9]">
                </div>
                <input type="hidden" id="otp" name="otp" required>
            </div>

            <button type="submit" class="login-btn">Verificar OTP </button>
        </form>
    </div>
</div>
<script src="../js/login.js"></script>
</body>
</html>