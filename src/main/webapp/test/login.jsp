<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/loginstyle.css">
    <title>Login Cliente</title>
    <!-- Aqui você pode colar todo o seu <style> que você mandou -->
</head>
<body>
<div class="login-container">
    <div class="background-layer bg-cliente"></div>
    <div class="background-layer bg-funcionario"></div>

    <div class="welcome-section">
        <div class="logo">
            <img src="../img/image.svg" alt="Logo" class="logo-image">
        </div>
        <h1 class="welcome-title">Bem-vindo!</h1>
        <h2 class="welcome-subtitle" id="welcome-subtitle">Área do Cliente</h2>
        <p class="welcome-description">Acesse sua conta para continuar.</p>
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
                <input type="text" id="cpf-cliente" name="cpf" class="form-input" required>
            </div>

            <div class="form-group password-container">
                <label class="form-label" for="senha-cliente">Senha</label>
                <input type="password" name="senha" id="senha-cliente" class="form-input" required>
                <button type="button" class="password-toggle">👁️</button>
            </div>

            <button type="submit" class="login-btn">Entrar</button>

            <div class="forgot-password">
                <a href="#">Esqueceu a senha?</a>
            </div>

            <div class="toggle-section">
                <p class="toggle-text">É funcionário?</p>
                <button type="button" class="toggle-btn" onclick="toggleForm('funcionario')">
                    Ir para funcionário
                </button>
            </div>
        </form>
    </div>

    <!-- Formulário do Funcionário -->
    <div class="form-container" id="funcionario-form" style="display: none;">
        <form class="login-form" action="${pageContext.request.contextPath}/loginCliente" method="post">
            <h2 class="form-title">Área do Funcionário</h2>
            <input type="hidden" name="tipo_usuario" value="FUNCIONARIO">

            <div class="form-group">
                <label class="form-label" for="cpf-func">CPF</label>
                <input type="text" id="cpf-func" name="cpf" class="form-input" required>
            </div>

            <div class="form-group password-container">
                <label class="form-label" for="senha-func">Senha</label>
                <input type="password" name="senha" id="senha-func" class="form-input" required>
                <button type="button" class="password-toggle">👁️</button>
            </div>

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

<script src="js/loginscript.js"></script>
</body>
</html>