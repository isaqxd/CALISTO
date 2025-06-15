<%@ page contentType="text/html;charset=UTF-8" %>

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Cliente</title>
    <link rel="stylesheet" href="css/loginstyle.css">
</head>

<body class="cliente-mode">
<div class="background-layer bg-cliente"></div>
<div class="background-layer bg-funcionario"></div>

<div class="login-container">
    <div class="welcome-section">
        <div class="logo">
            <img src="../img/image.svg" alt="Logo" class="logo-image">
        </div>
        <h1 class="welcome-title">Bem-vindo!</h1>
        <h2 class="welcome-subtitle" id="welcome-subtitle">Área do Cliente</h2>
        <p class="welcome-description">Acesse sua conta para continuar.</p>

        <div class="toggle-section">
            <p class="toggle-text">É funcionário?</p>
            <button type="button" class="toggle-btn" onclick="toggleForm('funcionario')">
                Ir para funcionário
            </button>
        </div>
    </div>

    <!-- Formulário do Cliente -->
    <div class="form-container" id="cliente-form">
        <form class="login-form" action="${pageContext.request.contextPath}/loginCliente" method="post">
            <h2 class="form-title">Área do Cliente</h2>
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

            <div class="form-group password-container">
                <label class="form-label" for="senha-cliente">Senha</label>
                <input type="password" name="senha" id="senha-cliente" class="form-input" placeholder="Digite sua senha" required>
                <button type="button" class="password-toggle">👁️</button>
            </div>

            <p class="form-note">Use pelo menos 8 caracteres com 1 número e um caractere especial.</p>

            <button type="submit" class="login-btn">Entrar</button>

            <div class="forgot-password">
                <a href="#">Esqueceu a senha?</a>
            </div>
        </form>
    </div>

    <!-- Formulário do Funcionário -->
    <div class="form-container" id="funcionario-form" style="display: none;">
        <form class="login-form" action="${pageContext.request.contextPath}/loginFuncionario" method="post">
            <h2 class="form-title">Área do Funcionário</h2>
            <input type="hidden" name="tipo_usuario" value="FUNCIONARIO">

            <div class="form-group">
                <label class="form-label" for="cpf-func">CPF</label>
                <input type="text" id="cpf-func" name="cpf" class="form-input" placeholder="000.000.000-00" required>
            </div>

            <div class="form-group password-container">
                <label class="form-label" for="senha-func">Senha</label>
                <input type="password" name="senha" id="senha-func" class="form-input" placeholder="Digite sua senha" required>
                <button type="button" class="password-toggle">👁️</button>
            </div>

            <p class="form-note">Use pelo menos 8 caracteres com 1 número e um caractere especial.</p>

            <button type="submit" class="login-btn">Entrar</button>

            <div class="forgot-password">
                <a href="#">Esqueceu a senha?</a>
            </div>

            <div class="toggle-section">
                <p class="toggle-text">É cliente?</p>
                <button type="button" class="toggle-btn" onclick="toggleForm('cliente')">
                    Ir para cliente
                </button>
            </div>
        </form>
    </div>
</div>

<%
    String otpParam = request.getParameter("otp_true");
    boolean mostrarFormularioOtp = "true".equals(otpParam);
%>

<% if (mostrarFormularioOtp) { %>
<script>
    // Esconde os formulários de login
    document.addEventListener("DOMContentLoaded", function () {
        document.getElementById("cliente-form").style.display = "none";
        document.getElementById("funcionario-form").style.display = "none";
        document.getElementById("otp-form").style.display = "block";
    });
</script>

<div class="form-container" id="otp-form" style="display: block;">
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

        <button type="submit" class="login-btn">Validar Código</button>
    </form>
</div>
<% } %>

<script src="js/loginscript.js"></script>

<script>
    // Fade-in animation
    window.addEventListener('load', function () {
        document.body.style.opacity = '0';
        setTimeout(() => {
            document.body.style.transition = 'opacity 0.5s ease';
            document.body.style.opacity = '1';
        }, 100);
    });

    // Setup OTP inputs if they exist
    document.addEventListener('DOMContentLoaded', function() {
        const otpInputs = document.querySelectorAll('.otp-input');
        if (otpInputs.length > 0) {
            setupOTPInputs();
        }
    });

    function setupOTPInputs() {
        const otpInputs = document.querySelectorAll('.otp-input');
        otpInputs.forEach((input, index) => {
            input.addEventListener('input', (e) => {
                // Only allow numbers
                e.target.value = e.target.value.replace(/[^0-9]/g, '');

                // Move to next input
                if (e.target.value.length === 1 && index < otpInputs.length - 1) {
                    otpInputs[index + 1].focus();
                }

                // Update hidden field
                updateOTPHiddenField();
            });

            input.addEventListener('keydown', (e) => {
                if (e.key === 'Backspace' && e.target.value.length === 0 && index > 0) {
                    otpInputs[index - 1].focus();
                }
            });
        });
    }

    function updateOTPHiddenField() {
        const otpInputs = document.querySelectorAll('.otp-input');
        const hiddenField = document.getElementById('otp');
        if (hiddenField) {
            let otp = '';
            otpInputs.forEach(input => {
                otp += input.value;
            });
            hiddenField.value = otp;
        }
    }
</script>
</body>
</html>