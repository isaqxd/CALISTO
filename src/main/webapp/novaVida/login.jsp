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
        <%-- ... (seu código da welcome-section continua o mesmo) ... --%>
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

    <%
        // Verifica se o parâmetro 'otp_true' está presente na URL
        String otpParam = request.getParameter("otp_true");
        boolean mostrarFormularioOtp = "true".equals(otpParam);

        // Pega o parâmetro de erro da URL
        String errorParam = request.getParameter("error");
    %>

    <div class="otp-overlay" id="otp-overlay" style="display: <%= mostrarFormularioOtp ? "flex" : "none" %>;">
        <form class="otp-form" action="${pageContext.request.contextPath}/loginOtp" method="post">
            <h2 class="form-title">🔐 Verificação de Segurança</h2>

            <% if ("otp_invalido".equals(errorParam)) { %>
            <div class="error-message" style="display: block;">
                OTP inválido. Tente novamente.
            </div>
            <% } %>
            <div class="form-group">
                <label class="form-label" for="otp">Digite o código de 6 dígitos enviado para seu dispositivo</label>
                <div class="otp-container">
                    <input type="text" class="otp-input" name="otp1" maxlength="1" pattern="[0-9]" autocomplete="one-time-code" inputmode="numeric">
                    <input type="text" class="otp-input" name="otp2" maxlength="1" pattern="[0-9]" autocomplete="one-time-code" inputmode="numeric">
                    <input type="text" class="otp-input" name="otp3" maxlength="1" pattern="[0-9]" autocomplete="one-time-code" inputmode="numeric">
                    <input type="text" class="otp-input" name="otp4" maxlength="1" pattern="[0-9]" autocomplete="one-time-code" inputmode="numeric">
                    <input type="text" class="otp-input" name="otp5" maxlength="1" pattern="[0-9]" autocomplete="one-time-code" inputmode="numeric">
                    <input type="text" class="otp-input" name="otp6" maxlength="1" pattern="[0-9]" autocomplete="one-time-code" inputmode="numeric">
                </div>
                <input type="hidden" id="otp" name="otp" required>
            </div>

            <p class="form-note">Este código expira em 5 minutos por segurança</p>
            <button type="submit" class="login-btn">✓ Verificar e Continuar</button>
            <div class="forgot-password">
                <a href="#" onclick="resendOTP()">Não recebeu o código? Reenviar</a>
            </div>
        </form>
    </div>

    <div class="form-container" id="cliente-form">

        <div class="otp-waiting-message" style="display: block;">
            <div class="security-icon">🛡️</div>
            <h3>Verificação em Andamento</h3>
            <p>Por favor, complete a verificação de segurança com o código OTP enviado para seu dispositivo.</p>
            <p><strong>Sua segurança é nossa prioridade!</strong></p>
        </div>
        <form class="login-form" action="/calistobank_war_exploded/loginCliente" method="post" style="display: none;">

            <h2 class="form-title">Login Cliente</h2>
            <input type="hidden" name="tipo_usuario" value="CLIENTE">


            <div class="form-group">
                <label class="form-label" for="cpf-cliente">CPF</label>
                <input type="text" id="cpf-cliente" name="cpf" class="form-input" placeholder="000.000.000-00"
                       required="" autocomplete="username">
            </div>

            <div class="form-group">
                <label class="form-label" for="senha-cliente">Senha</label>
                <div class="password-container">
                    <input type="password" name="senha" id="senha-cliente" class="form-input"
                           placeholder="Digite sua senha" required="" autocomplete="current-password">
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

    <div class="form-container" id="funcionario-form">

        <div class="otp-waiting-message" style="display: block;">
            <div class="security-icon">🛡️</div>
            <h3>Verificação em Andamento</h3>
            <p>Por favor, complete a verificação de segurança com o código OTP enviado para seu dispositivo.</p>
            <p><strong>Acesso restrito - Procedimento obrigatório!</strong></p>
        </div>
        <form class="login-form" action="/calistobank_war_exploded/loginFuncionario" method="post"
              style="display: none;">

            <h2 class="form-title">Login Funcionário</h2>
            <input type="hidden" name="tipo_usuario" value="FUNCIONARIO">

            <div class="form-group">
                <label class="form-label" for="cpf-func">CPF do Funcionário</label>
                <input type="text" id="cpf-func" name="cpf" class="form-input" placeholder="000.000.000-00" required=""
                       autocomplete="username">
            </div>

            <div class="form-group">
                <label class="form-label" for="senha-func">Senha</label>
                <div class="password-container">
                    <input type="password" name="senha" id="senha-func" class="form-input"
                           placeholder="Digite sua senha" required="" autocomplete="current-password">
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

</div>
<script src="../js/login.js"></script>

</body>
</html>