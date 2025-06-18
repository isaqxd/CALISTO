

<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Callisto Bank - Login</title>
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

<body class="cliente-mode">
<div class="background-layer bg-cliente"></div>
<div class="background-layer bg-funcionario"></div>

<div class="login-container">
    <div class="welcome-section">
        <div class="logo">
            <img src="../img/image.svg" alt="Callisto Bank" class="logo-image"> <!-- Path ajustado para ser relativo -->
        </div>
        <h1 class="welcome-title">Bem vindo!</h1>
        <h2 class="welcome-subtitle" id="welcome-subtitle">Acesso do Cliente</h2>
        <p class="welcome-description" id="welcome-description">Gerencie suas finanças com segurança</p>

        <div class="toggle-section">
            <p class="toggle-text" id="toggle-text">Acesso para funcionários?</p>
            <button type="button" class="toggle-btn" id="toggle-btn" onclick="toggleMode()">Portal do Funcionário
            </button>
        </div>
    </div>

    <div class="form-container">
        <form class="login-form" id="loginForm"> <!-- Removido method e action -->
            <h2 class="form-title" id="form-title">Login Cliente</h2>

            <div class="error-message" id="error-message"></div>
            <div class="success-message" id="success-message"></div>

            <!-- <input type="hidden" name="action" id="form-action" value="verifyCredentials"> Removido, não mais necessário -->
            <input type="hidden" name="tipo_usuario" value="CLIENTE">

            <div class="form-group" id="identifier-group">
                <label class="form-label" id="identifier-label">CPF</label>
                <input type="text" name="cpf" class="form-input" id="identifier-input" placeholder="000.000.000-00"
                       required>
            </div>

            <div class="form-group" id="password-group">
                <label class="form-label">Senha</label>
                <div class="password-container">
                    <input type="password" name="senha" class="form-input" placeholder="Senha" id="password-input"
                           required>
                    <button type="button" class="password-toggle" onclick="togglePassword()">👁️</button>
                </div>
            </div>

            <div class="form-group otp-section" id="otp-section">
                <label class="form-label">Código OTP (6 dígitos)</label>
                <div class="otp-container">
                    <input type="text" name="otp1" class="otp-input" maxlength="1" pattern="[0-9]" id="otp1">
                    <input type="text" name="otp2" class="otp-input" maxlength="1" pattern="[0-9]" id="otp2">
                    <input type="text" name="otp3" class="otp-input" maxlength="1" pattern="[0-9]" id="otp3">
                    <input type="text" name="otp4" class="otp-input" maxlength="1" pattern="[0-9]" id="otp4">
                    <input type="text" name="otp5" class="otp-input" maxlength="1" pattern="[0-9]" id="otp5">
                    <input type="text" name="otp6" class="otp-input" maxlength="1" pattern="[0-9]" id="otp6">
                </div>
            </div>

            <p class="form-note" id="form-note">Use pelo menos 8 caracteres com 1 número e um caractere especial.</p>

            <button type="submit" class="login-btn" id="login-btn">Entrar como Cliente</button>

            <div class="forgot-password">
                <a href="#forgot">Esqueceu a senha?</a>
            </div>

            <div class="toggle-section">
                <button type="button" class="exit-btn" onclick="goToLoginSelect()">Sair
                </button>
            </div>
        </form>
    </div>
</div>

<script>
    // --- INÍCIO: LÓGICA DE AUTENTICAÇÃO TOTALMENTE NO CLIENT-SIDE (JAVASCRIPT) ---
    // ATENÇÃO: Extremamente inseguro para produção. Apenas para fins de demonstração.

    const mockClientDatabase = {
        '123.456.789-00': { password: 'Cliente@123', name: 'Cliente Teste Um', requiresOtp: true, otp: '111111' },
        '111.222.333-44': { password: 'OutroCliente@1', name: 'Cliente Teste Dois', requiresOtp: false }
    };

    const mockEmployeeDatabase = {
        'FUN12345': { password: 'Funcionario@123', name: 'Funcionário Alfa', requiresOtp: false },
        'FUN67890': { password: 'OutroFunc@1', name: 'Funcionário Beta', requiresOtp: true, otp: '222222' }
    };

    let currentUserForOtp = null; // Armazena dados do usuário se OTP estiver pendente

    // --- FIM: LÓGICA DE AUTENTICAÇÃO TOTALMENTE NO CLIENT-SIDE ---

    let isFuncionarioMode = false;
    let isOTPPhase = false;

    function goToLoginSelect() {
        // Se você tiver um contextPath definido em um ambiente JSP, poderia usá-lo.
        // Para um ambiente puramente estático, um caminho relativo ou absoluto é necessário.
        // Assumindo que loginselect.jsp está no mesmo diretório 'views'
        window.location.href = 'loginselect.jsp'; // ou '../views/loginselect.jsp' dependendo da estrutura
    }

    function toggleMode() {
        const body = document.body;
        isFuncionarioMode = !isFuncionarioMode;

        // Reset to initial state before toggling
        resetFormState();

        if (isFuncionarioMode) {
            body.classList.add('funcionario-mode');
            updateContentForFuncionario();
        } else {
            body.classList.remove('funcionario-mode');
            updateContentForCliente();
        }
    }

    function updateContentForFuncionario() {
        document.getElementById('welcome-subtitle').textContent = 'Portal do Funcionário';
        document.getElementById('welcome-description').textContent = 'Acesso restrito para colaboradores';
        document.getElementById('toggle-text').textContent = 'Acesso para clientes?';
        document.getElementById('toggle-btn').textContent = 'Acesso do Cliente';
        document.getElementById('form-title').textContent = 'Login Funcionário';
        document.getElementById('identifier-label').textContent = 'Código do Funcionário';
        document.getElementById('identifier-input').placeholder = 'FUN00000';
        document.getElementById('login-btn').textContent = 'Entrar como Funcionário';
        // document.getElementById('loginForm').action = ''; // Ação do formulário não é mais usada
        document.querySelector('input[name="tipo_usuario"]').value = 'FUNCIONARIO';
        // Pre-fill test employee credentials
        document.getElementById('identifier-input').value = formatFuncionarioCode('FUN12345'); // Test Employee ID
        document.getElementById('password-input').value = 'Funcionario@123'; // Test Employee Password
    }

    function updateContentForCliente() {
        document.getElementById('welcome-subtitle').textContent = 'Acesso do Cliente';
        document.getElementById('welcome-description').textContent = 'Gerencie suas finanças com segurança';
        document.getElementById('toggle-text').textContent = 'Acesso para funcionários?';
        document.getElementById('toggle-btn').textContent = 'Portal do Funcionário';
        document.getElementById('form-title').textContent = 'Login Cliente';
        document.getElementById('identifier-label').textContent = 'CPF';
        document.getElementById('identifier-input').placeholder = '000.000.000-00';
        document.getElementById('login-btn').textContent = 'Entrar como Cliente';
        // document.getElementById('loginForm').action = ''; // Ação do formulário não é mais usada
        document.querySelector('input[name="tipo_usuario"]').value = 'CLIENTE';
        // Pre-fill test client credentials
        document.getElementById('identifier-input').value = formatCPF('12345678900'); // Test CPF "123.456.789-00"
        document.getElementById('password-input').value = 'Cliente@123';   // Test Client Password
    }

    function resetFormState() {
        document.getElementById('loginForm').reset();
        hideMessages();
        hideOTPSection();
        currentUserForOtp = null;

        // Re-enable fields that might be disabled
        document.getElementById('identifier-input').readOnly = false;
        document.getElementById('password-input').disabled = false;
        document.getElementById('password-group').style.display = 'block';
        document.getElementById('form-note').style.display = 'block';

        // Reset button and action
        // document.getElementById('form-action').value = 'verifyCredentials'; // Não mais necessário
        document.getElementById('login-btn').textContent = isFuncionarioMode ? 'Entrar como Funcionário' : 'Entrar como Cliente';
        isOTPPhase = false;
    }

    function togglePassword() {
        const field = document.getElementById('password-input');
        const icon = field.nextElementSibling;

        if (field.type === 'password') {
            field.type = 'text';
            icon.textContent = '🙈';
        } else {
            field.type = 'password';
            icon.textContent = '👁️';
        }
    }

    function formatCPF(cpf) {
        cpf = cpf.replace(/\D/g, '').substring(0, 11);
        if (cpf.length > 3) cpf = cpf.replace(/(\d{3})(\d)/, '$1.$2');
        if (cpf.length > 6) cpf = cpf.replace(/(\d{3})\.(\d{3})(\d)/, '$1.$2.$3');
        if (cpf.length > 9) cpf = cpf.replace(/(\d{3})\.(\d{3})\.(\d{3})(\d{1,2})$/, '$1.$2.$3-$4');
        return cpf;
    }

    function formatFuncionarioCode(code) {
        // Only convert to uppercase and remove special characters
        // without forcing the FUN prefix
        let value = code.toUpperCase().replace(/[^A-Z0-9]/g, '');
        return value.substring(0, 8);
    }


    document.getElementById('identifier-input').addEventListener('input', function (e) {
        if (!isFuncionarioMode) {
            e.target.value = formatCPF(e.target.value);
        } else {
            e.target.value = formatFuncionarioCode(e.target.value);
        }
    });

    function hideOTPSection() {
        document.getElementById('otp-section').style.display = 'none';
    }

    function showOTPSection(identifier) {
        isOTPPhase = true;
        // Hide password, show OTP
        document.getElementById('password-group').style.display = 'none';
        document.getElementById('form-note').style.display = 'none';
        document.getElementById('otp-section').style.display = 'block';

        // Pre-fill and lock identifier
        document.getElementById('identifier-input').value = identifier;
        document.getElementById('identifier-input').readOnly = true;

        // Update form action and button
        // document.getElementById('form-action').value = 'verifyOTP'; // Não mais necessário
        document.getElementById('login-btn').textContent = 'Verificar OTP';

        // Focus first OTP input and setup handlers
        const firstOtpInput = document.getElementById('otp1');
        if (firstOtpInput) firstOtpInput.focus();
        setupOTPInputs();
    }

    function setupOTPInputs() {
        const otpInputs = document.querySelectorAll('.otp-input');
        otpInputs.forEach((input, index) => {
            input.addEventListener('input', (e) => handleOTPInput(e, index, otpInputs));
            input.addEventListener('keydown', (e) => handleOTPKeydown(e, index, otpInputs));
        });
    }

    function handleOTPInput(e, index, otpInputs) {
        const input = e.target;
        input.value = input.value.replace(/[^0-9]/g, '');
        if (input.value.length === 1 && index < otpInputs.length - 1) {
            otpInputs[index + 1].focus();
        }
    }

    function handleOTPKeydown(e, index, otpInputs) {
        if (e.key === 'Backspace' && e.target.value.length === 0 && index > 0) {
            otpInputs[index - 1].focus();
        }
    }

    function showMessage(message, isError = false) {
        const errorDiv = document.getElementById('error-message');
        const successDiv = document.getElementById('success-message');
        if (isError) {
            errorDiv.textContent = message;
            errorDiv.style.display = 'block';
            successDiv.style.display = 'none';
        } else {
            successDiv.textContent = message;
            successDiv.style.display = 'block';
            errorDiv.style.display = 'none';
        }
    }

    function hideMessages() {
        document.getElementById('error-message').style.display = 'none';
        document.getElementById('success-message').style.display = 'none';
    }

    function processLogin() {
        hideMessages();
        const identifier = document.getElementById('identifier-input').value;
        const password = document.getElementById('password-input').value;

        if (isOTPPhase) {
            // Coletar OTP
            const otpInputs = document.querySelectorAll('.otp-input');
            let otp = '';
            otpInputs.forEach(input => { otp += input.value; });

            if (otp.length !== 6 || !/^\d{6}$/.test(otp)) {
                showMessage('Por favor, insira um código OTP válido de 6 dígitos.', true);
                return;
            }
            verifyOtp(otp);
        } else {
            verifyCredentials(identifier, password);
        }
    }

    function verifyCredentials(identifier, password) {
        const db = isFuncionarioMode ? mockEmployeeDatabase : mockClientDatabase;
        const user = db[identifier];

        if (user && user.password === password) {
            if (user.requiresOtp) {
                currentUserForOtp = user; // Armazena o usuário para a fase OTP
                currentUserForOtp.identifier = identifier; // Adiciona o identificador para referência
                showOTPSection(identifier);
                showMessage('Credenciais válidas. Por favor, insira o código OTP.', false);
            } else {
                loginSuccess(user, identifier);
            }
        } else {
            showMessage('Credenciais inválidas. Verifique seu ' + (isFuncionarioMode ? 'código' : 'CPF') + ' e senha.', true);
        }
    }

    function verifyOtp(otp) {
        console.log("Verificando OTP:", otp);
        console.log("CurrentUserForOtp:", currentUserForOtp);
        if (currentUserForOtp && currentUserForOtp.otp === otp) {
            console.log("OTP válido. Chamando loginSuccess.");
            loginSuccess(currentUserForOtp, currentUserForOtp.identifier);
        } else {
            console.error("OTP inválido ou currentUserForOtp não definido corretamente.");
            showMessage('Código OTP inválido.', true);
            // Opcional: resetar para a tela de login inicial após falha de OTP
            // setTimeout(() => {
            //     resetFormState();
            //     if (isFuncionarioMode) updateContentForFuncionario(); else updateContentForCliente();
            // }, 2000);
        }
    }

    function loginSuccess(user, identifier) {
        console.log("loginSuccess chamado para:", user, "Modo Funcionário:", isFuncionarioMode);
        showMessage(`Login bem-sucedido, ${user.name}! Redirecionando...`, false);
        // Simula o armazenamento de uma sessão
        localStorage.setItem('loggedInUser', JSON.stringify({
            identifier: identifier,
            name: user.name,
            type: isFuncionarioMode ? 'funcionario' : 'cliente'
        }));

        setTimeout(() => {
            // Crie estas páginas HTML estáticas para simular os portais
            // Ajuste os nomes dos arquivos para corresponderem aos seus JSPs de portal
            const targetUrl = isFuncionarioMode ? 'portalfuncionario.jsp' : 'portalcliente.jsp';
            console.log("Redirecionando para:", targetUrl);
            if (isFuncionarioMode) {
                window.location.href = 'portalfuncionario.jsp'; // Assumindo que este arquivo existe
            } else {
                window.location.href = 'portalcliente.jsp';
            }
        }, 1500);
    }


    document.getElementById('loginForm').addEventListener('submit', function (e) {
        e.preventDefault(); // Previne o envio tradicional do formulário
        processLogin();
    });

    // Check for Server Redirect Parameters on Load
    window.addEventListener('load', function () {
        hideMessages();
        document.body.style.opacity = '0'; // Start fade-in prep

        // Lógica de URL params removida, pois o estado é gerenciado internamente
        /*
        let initialModeIsFuncionario = false;
        if ((effectiveIdentifierForModeCheck && effectiveIdentifierForModeCheck.toUpperCase().startsWith('FUN')) || tipoUsuario === 'FUNCIONARIO') {
            initialModeIsFuncionario = true;
        }

        // Set the correct initial mode and pre-fill credentials
        // Global 'isFuncionarioMode' starts as false.
        if (initialModeIsFuncionario) {
            if (!isFuncionarioMode) toggleMode(); else {
                document.body.classList.add('funcionario-mode');
                resetFormState(); updateContentForFuncionario();
            }
        } else {
            if (isFuncionarioMode) toggleMode(); else {
                document.body.classList.remove('funcionario-mode');
                resetFormState(); updateContentForCliente();
            }
        }
        */
        // Com a lógica de backend removida, sempre iniciamos no modo cliente padrão
        resetFormState();
        updateContentForCliente(); // Garante que os campos de cliente sejam preenchidos inicialmente

        // Fade-in animation
        setTimeout(() => {
            document.body.style.transition = 'opacity 0.5s ease';
            document.body.style.opacity = '1';
        }, 100);
    });

    // Keyboard shortcut for toggling mode (Ctrl + M)
    document.addEventListener('keydown', function (e) {
        if (e.ctrlKey && e.key.toLowerCase() === 'm') {
            e.preventDefault();
            toggleMode();
        }
    });
</script>
</body>
</html>
