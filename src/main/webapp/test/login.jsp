<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login Cliente</title>
    <!-- Aqui você pode colar todo o seu <style> que você mandou -->
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Arial', sans-serif;
            min-height: 100vh;
            overflow: hidden;
            position: relative;
            transition: all 0.8s cubic-bezier(0.4, 0, 0.2, 1);
        }

        /* Background Transitions */
        .background-layer {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            transition: all 0.8s cubic-bezier(0.4, 0, 0.2, 1);
            z-index: -1;
        }

        .bg-cliente {
            background: linear-gradient(135deg,
            rgba(242, 185, 80, 0.1) 0%,
            rgba(191, 122, 36, 0.15) 50%,
            rgba(140, 72, 13, 0.1) 100%),
            #f8f6f3;
            opacity: 1;
        }

        .bg-funcionario {
            background: linear-gradient(135deg,
            rgba(89, 83, 72, 0.1) 0%,
            rgba(38, 10, 4, 0.15) 50%,
            rgba(0, 0, 0, 0.05) 100%),
            #f5f5f2;
            opacity: 0;
        }

        body.funcionario-mode .bg-cliente {
            opacity: 0;
        }

        body.funcionario-mode .bg-funcionario {
            opacity: 1;
        }

        /* Animated Background Elements */
        .bg-cliente::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background: url('data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 100 100"><circle cx="20" cy="20" r="2" fill="%23F2B950" opacity="0.2"><animate attributeName="r" values="2;4;2" dur="6s" repeatCount="indefinite"/></circle><circle cx="80" cy="30" r="1.5" fill="%23BF7A24" opacity="0.3"><animate attributeName="r" values="1.5;3;1.5" dur="8s" repeatCount="indefinite"/></circle><circle cx="40" cy="70" r="2.5" fill="%238C480D" opacity="0.2"><animate attributeName="r" values="2.5;4.5;2.5" dur="7s" repeatCount="indefinite"/></circle></svg>');
            animation: float 25s infinite linear;
        }

        .bg-funcionario::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background: url('data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 100 100"><circle cx="15" cy="25" r="1.5" fill="%23595348" opacity="0.3"><animate attributeName="r" values="1.5;3;1.5" dur="5s" repeatCount="indefinite"/></circle><circle cx="85" cy="40" r="2" fill="%23260A04" opacity="0.2"><animate attributeName="r" values="2;3.5;2" dur="7s" repeatCount="indefinite"/></circle><circle cx="50" cy="80" r="1.8" fill="%23000000" opacity="0.1"><animate attributeName="r" values="1.8;3.2;1.8" dur="6s" repeatCount="indefinite"/></circle></svg>');
            animation: float 30s infinite linear reverse;
        }

        @keyframes float {
            0% {
                transform: translateX(-50px) translateY(-20px);
            }
            50% {
                transform: translateX(50px) translateY(20px);
            }
            100% {
                transform: translateX(-50px) translateY(-20px);
            }
        }

        /* Main Container */
        .login-container {
            display: flex;
            min-height: 100vh;
            position: relative;
            overflow: hidden;
            width: 100vw;
        }

        /* Welcome Section */
        .welcome-section {
            position: absolute;
            width: 50%;
            height: 100vh;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            padding: 2rem;
            text-align: center;
            z-index: 1;
            transition: all 0.8s cubic-bezier(0.4, 0, 0.2, 1);
        }

        body:not(.funcionario-mode) .welcome-section {
            left: 0;
        }

        body.funcionario-mode .welcome-section {
            left: 50%;
        }

        .logo {
            margin-bottom: 2rem;
        }

        .logo-image {
            height: 80px;
            width: auto;
            object-fit: contain;
            filter: drop-shadow(0 4px 12px rgba(0, 0, 0, 0.15));
        }

        .welcome-title {
            font-size: 2.5rem;
            font-weight: bold;
            margin-bottom: 1rem;
            text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.1);
            transition: color 0.8s ease;
        }

        .welcome-subtitle {
            font-size: 1.5rem;
            font-weight: 300;
            margin-bottom: 0.5rem;
            transition: color 0.8s ease;
        }

        .welcome-description {
            font-size: 1rem;
            opacity: 0.8;
            margin-bottom: 2rem;
            transition: color 0.8s ease;
        }

        /* Cliente Mode Styling */
        body:not(.funcionario-mode) .welcome-title {
            color: #BF7A24;
        }

        body:not(.funcionario-mode) .welcome-subtitle {
            color: #8C480D;
        }

        body:not(.funcionario-mode) .welcome-description {
            color: #595348;
        }

        /* Funcionário Mode Styling */
        body.funcionario-mode .welcome-title {
            color: #260A04;
        }

        body.funcionario-mode .welcome-subtitle {
            color: #595348;
        }

        body.funcionario-mode .welcome-description {
            color: #595348;
        }

        /* Form Container */
        .form-container {
            position: absolute;
            top: 0;
            width: 50%;
            height: 100vh;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            padding: 2rem;
            transition: all 0.8s cubic-bezier(0.4, 0, 0.2, 1);
            z-index: 2;
        }

        body:not(.funcionario-mode) .form-container {
            left: 50%;
            background: linear-gradient(135deg, rgba(0, 0, 0, 0.6) 0%, rgba(0, 0, 0, 0.2) 100%),
            linear-gradient(45deg, #f8f6f3 0%, #f2e8d5 100%);
        }

        body.funcionario-mode .form-container {
            left: 0;
            background: linear-gradient(135deg, rgba(0, 0, 0, 0.6) 0%, rgba(0, 0, 0, 0.2) 100%),
            linear-gradient(45deg, #f5f5f2 0%, #e8e6e1 100%);
        }

        .form-container::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background: rgba(255, 255, 255, 0.85);
            filter: blur(15px);
            z-index: -1;
        }

        .login-form {
            background: rgba(255, 255, 255, 0.95);
            padding: 2rem;
            border-radius: 20px;
            box-shadow: 0 20px 40px rgba(0, 0, 0, 0.12);
            width: 100%;
            max-width: 400px;
            position: relative;
            z-index: 2;
            backdrop-filter: blur(10px);
            border: 1px solid rgba(255, 255, 255, 0.2);
        }

        .form-title {
            font-size: 1.8rem;
            font-weight: bold;
            margin-bottom: 1.5rem;
            text-align: center;
            transition: color 0.8s ease;
        }

        body:not(.funcionario-mode) .form-title {
            color: #BF7A24;
        }

        body.funcionario-mode .form-title {
            color: #260A04;
        }

        .form-group {
            margin-bottom: 1.2rem;
        }

        .form-label {
            display: block;
            margin-bottom: 0.5rem;
            font-weight: 500;
            font-size: 0.9rem;
            color: #333;
        }

        .form-input {
            width: 100%;
            padding: 0.8rem;
            border: 2px solid #e0e0e0;
            border-radius: 10px;
            font-size: 1rem;
            transition: all 0.3s ease;
            background: rgba(255, 255, 255, 0.9);
        }

        .form-input:focus {
            outline: none;
            border-color: #BF7A24;
            box-shadow: 0 0 0 3px rgba(191, 122, 36, 0.1);
        }

        body.funcionario-mode .form-input:focus {
            border-color: #260A04;
            box-shadow: 0 0 0 3px rgba(38, 10, 4, 0.1);
        }

        .password-container {
            position: relative;
        }

        .password-toggle {
            position: absolute;
            right: 0.8rem;
            top: 50%;
            transform: translateY(-50%);
            background: none;
            border: none;
            cursor: pointer;
            color: #666;
            font-size: 1rem;
        }

        .otp-container {
            display: flex;
            gap: 0.5rem;
            justify-content: center;
        }

        .otp-input {
            width: 45px;
            height: 45px;
            text-align: center;
            font-size: 1.2rem;
            font-weight: bold;
            border: 2px solid #e0e0e0;
            border-radius: 8px;
            transition: all 0.3s ease;
        }

        .otp-input:focus {
            outline: none;
            border-color: #BF7A24;
            box-shadow: 0 0 0 2px rgba(191, 122, 36, 0.2);
        }

        body.funcionario-mode .otp-input:focus {
            border-color: #260A04;
            box-shadow: 0 0 0 2px rgba(38, 10, 4, 0.2);
        }

        .form-note {
            font-size: 0.8rem;
            color: #666;
            text-align: center;
            margin-bottom: 1.5rem;
        }

        .login-btn {
            width: 100%;
            padding: 1rem;
            border: none;
            border-radius: 25px;
            font-size: 1rem;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            margin-bottom: 1rem;
        }

        body:not(.funcionario-mode) .login-btn {
            background: linear-gradient(135deg, #F2B950 0%, #BF7A24 100%);
            color: white;
        }

        body:not(.funcionario-mode) .login-btn:hover {
            background: linear-gradient(135deg, #BF7A24 0%, #8C480D 100%);
            transform: translateY(-2px);
            box-shadow: 0 8px 25px rgba(191, 122, 36, 0.3);
        }

        body.funcionario-mode .login-btn {
            background: linear-gradient(135deg, #595348 0%, #260A04 100%);
            color: white;
        }

        body.funcionario-mode .login-btn:hover {
            background: linear-gradient(135deg, #260A04 0%, #000000 100%);
            transform: translateY(-2px);
            box-shadow: 0 8px 25px rgba(38, 10, 4, 0.3);
        }

        .forgot-password {
            text-align: center;
            margin-bottom: 1.5rem;
        }

        .forgot-password a {
            color: #BF7A24;
            text-decoration: none;
            font-size: 0.9rem;
            transition: color 0.3s ease;
        }

        body.funcionario-mode .forgot-password a {
            color: #260A04;
        }

        /* Toggle Section */
        .toggle-section {
            text-align: center;
            padding: 1rem;
        }

        .toggle-text {
            margin-bottom: 1rem;
            color: #666;
            font-size: 0.9rem;
        }

        .toggle-btn {
            background: transparent;
            border: 2px solid #BF7A24;
            color: #BF7A24;
            padding: 0.7rem 1.5rem;
            border-radius: 20px;
            cursor: pointer;
            font-weight: 600;
            font-size: 0.9rem;
            transition: all 0.3s ease;
            margin: 0 0.5rem;
        }

        .toggle-btn:hover {
            background: #BF7A24;
            color: white;
            transform: translateY(-2px);
        }

        body.funcionario-mode .toggle-btn {
            border-color: #260A04;
            color: #260A04;
        }

        body.funcionario-mode .toggle-btn:hover {
            background: #260A04;
            color: white;
        }

        .exit-btn {
            background: transparent;
            border: 2px solid #666;
            color: #666;
            padding: 0.7rem 1.5rem;
            border-radius: 20px;
            cursor: pointer;
            font-weight: 600;
            font-size: 0.9rem;
            transition: all 0.3s ease;
            margin: 0 0.5rem;
        }

        .exit-btn:hover {
            background: #666;
            color: white;
            transform: translateY(-2px);
        }

        .error-message, .success-message {
            font-size: 0.85rem;
            margin-bottom: 1rem;
            padding: 0.5rem;
            border-radius: 5px;
            text-align: center;
            display: none;
        }

        .error-message {
            background: #fed7d7;
            color: #c53030;
        }

        .success-message {
            background: #c6f6d5;
            color: #38a169;
        }

        .otp-section {
            display: none;
        }

        /* Responsive Design */
        @media (max-width: 768px) {
            .login-container {
                flex-direction: column;
                height: auto;
                min-height: 100vh;
            }

            .welcome-section {
                position: relative;
                width: 100%;
                height: auto;
                flex: 0 0 auto;
                padding: 1.5rem 1rem;
                left: 0 !important;
            }

            .form-container {
                position: relative;
                width: 100%;
                height: auto;
                padding: 1rem;
                left: 0 !important;
            }

            .welcome-title {
                font-size: 2rem;
            }

            .welcome-subtitle {
                font-size: 1.2rem;
            }

            .logo-image {
                height: 60px;
            }

            .otp-input {
                width: 35px;
                height: 35px;
                font-size: 1rem;
            }
        }
    </style>
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

<script>
    function toggleForm(tipo) {
        const body = document.body;
        const clienteForm = document.getElementById('cliente-form');
        const funcionarioForm = document.getElementById('funcionario-form');
        const welcomeSubtitle = document.getElementById('welcome-subtitle');

        if (tipo === 'funcionario') {
            body.classList.add('funcionario-mode');
            clienteForm.style.display = 'none';
            funcionarioForm.style.display = 'flex';
            welcomeSubtitle.textContent = 'Área do Funcionário';
        } else {
            body.classList.remove('funcionario-mode');
            clienteForm.style.display = 'flex';
            funcionarioForm.style.display = 'none';
            welcomeSubtitle.textContent = 'Área do Cliente';
        }
    }

    // Formatação de CPF
    function formatCPF(input) {
        let value = input.value.replace(/\D/g, '');
        if (value.length > 11) value = value.slice(0, 11);
        value = value.replace(/(\d{3})(\d)/, '$1.$2');
        value = value.replace(/(\d{3})(\d)/, '$1.$2');
        value = value.replace(/(\d{3})(\d{1,2})$/, '$1-$2');
        input.value = value;
    }

    // Adicionar formatação de CPF aos campos
    document.getElementById('cpf-cliente').addEventListener('input', function () {
        formatCPF(this);
    });
    document.getElementById('cpf-func').addEventListener('input', function () {
        formatCPF(this);
    });

    // Toggle de visibilidade da senha
    document.querySelectorAll('.password-toggle').forEach(button => {
        button.addEventListener('click', function () {
            const input = this.previousElementSibling;
            if (input.type === 'password') {
                input.type = 'text';
                this.textContent = '👁️‍🗨️';
            } else {
                input.type = 'password';
                this.textContent = '👁️';
            }
        });
    });
</script>
</body>
</html>