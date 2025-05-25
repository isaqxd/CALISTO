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
            height: 100vh;
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
            0% { transform: translateX(-50px) translateY(-20px); }
            50% { transform: translateX(50px) translateY(20px); }
            100% { transform: translateX(-50px) translateY(-20px); }
        }

        /* Header */
        .header {
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            padding: 1.5rem 2rem;
            z-index: 100;
        }

        .logo {
            display: flex;
            align-items: center;
            justify-content: center;
        }

        .logo-image {
            height: 90px;
            width: auto;
            object-fit: contain;
            filter: drop-shadow(0 2px 8px rgba(0,0,0,0.1));
        }

        /* Main Container - CORRIGIDO */
        .login-container {
            display: flex;
            height: 100vh;
            position: relative;
            overflow: hidden;
            width: 100vw;
        }

        /* Sliding Content - CORRIGIDO */
        .content-slider {
            display: flex;
            width: 200vw; /* Alterado para viewport width */
            height: 100%;
            transition: transform 0.8s cubic-bezier(0.4, 0, 0.2, 1);
            transform: translateX(0); /* Estado inicial */
        }

        /* Quando em modo funcionário, desliza para a esquerda */
        body.funcionario-mode .content-slider {
            transform: translateX(-100vw); /* Move uma tela completa para a esquerda */
        }

        /* Individual Sides - CORRIGIDO */
        .login-side {
            width: 100vw; /* Cada lado ocupa a largura total da viewport */
            height: 100vh;
            display: flex;
            flex-shrink: 0; /* Impede que os lados encolham */
            position: relative;
        }

        /* Welcome Section */
        .welcome-section {
            flex: 1;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            padding: 2rem;
            text-align: center;
        }

        .welcome-title {
            font-size: 3rem;
            font-weight: bold;
            margin-bottom: 1rem;
            text-shadow: 2px 2px 4px rgba(0,0,0,0.1);
        }

        .welcome-subtitle {
            font-size: 1.8rem;
            font-weight: 300;
            margin-bottom: 0.5rem;
        }

        .welcome-description {
            font-size: 1rem;
            opacity: 0.8;
        }

        /* Cliente Side Styling */
        .cliente-side .welcome-title {
            color: #BF7A24;
        }

        .cliente-side .welcome-subtitle {
            color: #8C480D;
        }

        .cliente-side .welcome-description {
            color: #595348;
        }

        /* Funcionário Side Styling */
        .funcionario-side .welcome-title {
            color: #260A04;
        }

        .funcionario-side .welcome-subtitle {
            color: #595348;
        }

        .funcionario-side .welcome-description {
            color: #595348;
        }

        /* Login Form Section */
        .form-section {
            flex: 1;
            display: flex;
            flex-direction: column;
            justify-content: center;
            align-items: center;
            padding: 2rem;
            background: rgba(255, 255, 255, 0.95);
            backdrop-filter: blur(10px);
            position: relative;
        }

        .form-section::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background: rgba(255, 255, 255, 0.1);
            backdrop-filter: blur(5px);
        }

        .login-form {
            
            background: rgba(255, 255, 255, 0.9);
            border-radius: 20px;
            box-shadow: 0 20px 40px rgba(0,0,0,0.1);
            width: 100%;
            max-width: 400px;
            position: relative;
            padding: 3rem;
            z-index: 2;
            backdrop-filter: blur(10px);
        }

        .form-title {

        }

        .cliente-side .form-title {
            color: #BF7A24;
            text-align: center;
        }

        .funcionario-side .form-title {
            color: #260A04;
            text-align: center;

        }

        .form-group {
            margin-bottom: 1.5rem;
        }

        .form-label {
            display: block;
            margin-bottom: 0.5rem;
            font-weight: 500;
            color: #333;
        }

        .form-input {
            width: 100%;
            padding: 1rem;
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

        .funcionario-side .form-input:focus {
            border-color: #260A04;
            box-shadow: 0 0 0 3px rgba(38, 10, 4, 0.1);
        }

        .password-container {
            position: relative;
        }

        .password-toggle {
            position: absolute;
            right: 1rem;
            top: 50%;
            transform: translateY(-50%);
            background: none;
            border: none;
            cursor: pointer;
            color: #666;
            font-size: 1.2rem;
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
            font-size: 1.1rem;
            font-weight: 600;
            cursor: pointer;
            transition: all 0.3s ease;
            margin-bottom: 1rem;
        }

        .cliente-side .login-btn {
            background: linear-gradient(135deg, #F2B950 0%, #BF7A24 100%);
            color: white;
        }

        .cliente-side .login-btn:hover {
            background: linear-gradient(135deg, #BF7A24 0%, #8C480D 100%);
            transform: translateY(-2px);
            box-shadow: 0 8px 25px rgba(191, 122, 36, 0.3);
        }

        .funcionario-side .login-btn {
            background: linear-gradient(135deg, #595348 0%, #260A04 100%);
            color: white;
        }

        .funcionario-side .login-btn:hover {
            background: linear-gradient(135deg, #260A04 0%, #000000 100%);
            transform: translateY(-2px);
            box-shadow: 0 8px 25px rgba(38, 10, 4, 0.3);
        }

        .forgot-password {
            text-align: center;
            margin-bottom: 2rem;
        }

        .forgot-password a {
            color: #BF7A24;
            text-decoration: none;
            transition: color 0.3s ease;
        }

        .funcionario-side .forgot-password a {
            color: #260A04;
        }

        .forgot-password a:hover {
            color: #8C480D;
        }

        .funcionario-side .forgot-password a:hover {
            color: #595348;
        }

        /* Toggle Section */
        .toggle-section {
            text-align: center;
            padding: 1rem;
            border-top: 1px solid rgba(0,0,0,0.1);
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
            transition: all 0.3s ease;
        }

        .toggle-btn:hover {
            background: #BF7A24;
            color: white;
            transform: translateY(-2px);
        }

        .funcionario-side .toggle-btn {
            border-color: #260A04;
            color: #260A04;
        }

        .funcionario-side .toggle-btn:hover {
            background: #260A04;
            color: white;
        }

        /* Responsive Design */
        @media (max-width: 768px) {
            .login-side {
                flex-direction: column;
            }

            .welcome-section {
                flex: 0 0 auto;
                padding: 1rem;
            }

            .welcome-title {
                font-size: 2rem;
            }

            .welcome-subtitle {
                font-size: 1.2rem;
            }

            .form-section {
                flex: 1;
                padding: 1rem;
            }

            .login-form {
                padding: 2rem;
            }

            .logo-image {
                height: 50px;
            }
        }

        @media (max-width: 480px) {
            .welcome-title {
                font-size: 1.5rem;
            }

            .welcome-subtitle {
                font-size: 1rem;
            }

            .login-form {
                padding: 1.5rem;
            }
        }
    </style>
</head>
<body class="cliente-mode">
    <!-- Background Layers -->
    <div class="background-layer bg-cliente"></div>
    <div class="background-layer bg-funcionario"></div>

    <!-- Header -->
    <header class="header">
        <div class="logo">
            <img src="/src/main/WebContent/imagens/image.svg" alt="Callisto Bank" class="logo-image">
        </div>
    </header>

    <!-- Main Container -->
    <div class="login-container">
        <div class="content-slider">
            <!-- Cliente Side -->
            <div class="login-side cliente-side">
                <!-- Welcome Section -->
                <div class="welcome-section">
                    <h1 class="welcome-title">Bem vindo !</h1>
                    <h2 class="welcome-subtitle">Acesso do Cliente</h2>
                    <p class="welcome-description">Gerencie suas finanças com segurança</p>
                </div>

                <!-- Form Section -->
                <div class="form-section">
                    <form class="login-form" id="clienteForm">
                        <h2 class="form-title">Login</h2>
                        
                        <div class="form-group">
                            <label class="form-label">Email</label>
                            <input type="email" class="form-input" placeholder="seuemail@email.com" required>
                        </div>

                        <div class="form-group">
                            <label class="form-label">Senha</label>
                            <div class="password-container">
                                <input type="password" id="myPassword">
                                <button onclick="togglePassword('myPassword')">
                                    <img src="/src/main/WebContent/imagens/iconeyeopen.png" alt="Mostrar senha" width="20">
                                </button>
                            </div>
                        </div>

                        <p class="form-note">Use pelo menos 8 caracteres com 1 número e um caractere especial.</p>

                        <button type="submit" class="login-btn">Entrar</button>

                        <div class="forgot-password">
                            <a href="#forgot">Esqueceu a senha? Sair</a>
                        </div>

                        <div class="toggle-section">
                            <p class="toggle-text">Acesso para funcionários?</p>
                            <button type="button" class="toggle-btn" onclick="toggleMode()">Portal do Funcionário</button>
                        </div>
                    </form>
                </div>
            </div>


            <!-- Funcionário Side -->
            <div class="login-side funcionario-side">
                <!-- Welcome Section -->
                <div class="welcome-section">
                    <h1 class="welcome-title">Bem vindo !</h1>
                    <h2 class="welcome-subtitle">Portal do Funcionário</h2>
                    <p class="welcome-description">Acesso restrito para colaboradores</p>
                </div>

                <!-- Form Section -->
                <div class="form-section">
                    <form class="login-form" id="funcionarioForm">
                        <h2 class="form-title">Login</h2>
                        
                        <div class="form-group">
                            <label class="form-label">Email</label>
                            <input type="email" class="form-input" placeholder="seuemail@email.com" required>
                        </div>

                        <div class="form-group">
                            <label class="form-label">Senha</label>
                            <div class="password-container">
                                <input type="password" class="form-input" placeholder="Senha" id="funcionarioPassword" required>
                                <button type="button" class="password-toggle" onclick="togglePassword('funcionarioPassword')">👁</button>
                            </div>
                        </div>

                        <p class="form-note">Use pelo menos 8 caracteres com 1 número e um caractere especial.</p>

                        <button type="submit" class="login-btn">Entrar</button>

                        <div class="forgot-password">
                            <a href="#forgot">Esqueceu a senha? Sair</a>
                        </div>

                        <div class="toggle-section">
                            <p class="toggle-text">Acesso para clientes?</p>
                            <button type="button" class="toggle-btn" onclick="toggleMode()">Acesso do Cliente</button>
                        </div>
                    </form>
                </div>

            </div>
        </div>
    </div>

    <script>
        let isFuncionarioMode = false;

        function toggleMode() {
            const body = document.body;
            isFuncionarioMode = !isFuncionarioMode;
            
            if (isFuncionarioMode) {
                body.classList.remove('cliente-mode');
                body.classList.add('funcionario-mode');
            } else {
                body.classList.remove('funcionario-mode');
                body.classList.add('cliente-mode');
            }
        }

        function togglePassword(fieldId) {
            const field = document.getElementById(fieldId);
            const button = field.nextElementSibling;
            const img = button.querySelector('img');
            
    if (field.type === 'password') {
        field.type = 'text';
        img.src = ''; // imagem de olho fechado
        img.alt = 'Ocultar senha';
    } else {
        field.type = 'password';
        img.src = 'eye.png'; // imagem de olho aberto
        img.alt = 'Mostrar senha';
    }
}


        // Form submissions
        document.getElementById('clienteForm').addEventListener('submit', function(e) {
            e.preventDefault();
            alert('Login de cliente processado!');
        });

        document.getElementById('funcionarioForm').addEventListener('submit', function(e) {
            e.preventDefault();
            alert('Login de funcionário processado!');
        });

        // Smooth transitions on page load
        window.addEventListener('load', function() {
            document.body.style.opacity = '0';
            setTimeout(() => {
                document.body.style.transition = 'opacity 0.5s ease';
                document.body.style.opacity = '1';
            }, 100);
        });

        // Add keyboard navigation
        document.addEventListener('keydown', function(e) {
            if (e.key === 'Tab' && e.shiftKey && e.ctrlKey) {
                e.preventDefault();
                toggleMode();
            }
        });

        // Add subtle hover effects to form inputs
        document.querySelectorAll('.form-input').forEach(input => {
            input.addEventListener('focus', function() {
                this.parentElement.style.transform = 'translateY(-2px)';
            });
            
            input.addEventListener('blur', function() {
                this.parentElement.style.transform = 'translateY(0)';
            });
        });
    </script>
</body>
</html>