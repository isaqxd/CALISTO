<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Callisto Bank - Login</title>
    <link rel="stylesheet" href="css/loginStyle.css">
</head>
<body class="cliente-mode">
    <!-- Background Layers -->
    <div class="background-layer bg-cliente"></div>
    <div class="background-layer bg-funcionario"></div>

    <!-- Header -->
    <header class="header">
        <div class="logo">
            <img src="img/image.svg" alt="Callisto Bank" class="logo-image">
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
                    <p class="welcome-description">Gerencie suas finanças com segurança</p><br><br>
                    <div class="toggle-section">
                            <p class="toggle-text">Acesso para funcionários?</p>
                            <button type="button" class="toggle-btn" onclick="toggleMode()">Portal do Funcionário</button>
                        </div>
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
                                <input type="password" class="form-input" placeholder="Senha" id="clientePassword" required>
                                <button type="button" class="password-toggle" onclick="togglePassword('clientePassword')">
                                    <img src="img/iconeyeclosed.png" alt="Mostrar senha" id="icon-clientePassword" width="20">
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
                            <button type="button" class="toggle-btn" onclick="location.href='index.jsp'">Sair</button>
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
                    <p class="welcome-description">Acesso restrito para colaboradores</p><br><br>
                    <div class="toggle-section">
                            <p class="toggle-text">Acesso para funcionários?</p>
                            <button type="button" class="toggle-btn" onclick="toggleMode()">Acesso do Cliente</button>
                        </div>
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
                                <button type="button" class="password-toggle" onclick="togglePassword('funcionarioPassword')">
                                    <img src="img/iconeyeclosed.png" alt="Mostrar senha" id="icon-funcionarioPassword" width="20">
                                </button>
                            </div>
                        </div>

                        <p class="form-note">Use pelo menos 8 caracteres com 1 número e um caractere especial.</p>

                        <button type="submit" class="login-btn">Entrar</button>

                        <div class="forgot-password">
                            <a href="#forgot">Esqueceu a senha? Sair</a>
                        </div>

                        <div class="toggle-section">
                            <p class="toggle-text">Acesso para clientes?</p>
                            <button type="button" class="toggle-btn" onclick="location.href='index.jsp'">Sair</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script>
        let isFuncionarioMode = false;

        // Função para detectar qual modo usar baseado na URL
        function detectModeFromURL() {
            const urlParams = new URLSearchParams(window.location.search);
            const mode = urlParams.get('mode');
            const hash = window.location.hash;
            
            // Verifica parâmetro ?mode=funcionario ou ?mode=employee
            if (mode === 'funcionario' || mode === 'employee') {
                return true;
            }
            
            // Verifica hash #funcionario ou #employee
            if (hash === '#funcionario' || hash === '#employee') {
                return true;
            }
            
            // Verifica se a URL contém palavras-chave
            const url = window.location.href.toLowerCase();
            if (url.includes('funcionario') || url.includes('employee') || url.includes('staff')) {
                return true;
            }
            
            return false; // Por padrão, cliente
        }

        // Função para definir o modo inicial
        function setInitialMode() {
            const shouldBeFuncionarioMode = detectModeFromURL();
            
            if (shouldBeFuncionarioMode) {
                isFuncionarioMode = true;
                document.body.classList.remove('cliente-mode');
                document.body.classList.add('funcionario-mode');
            } else {
                isFuncionarioMode = false;
                document.body.classList.remove('funcionario-mode');
                document.body.classList.add('cliente-mode');
            }
        }

        function toggleMode() {
            const body = document.body;
            isFuncionarioMode = !isFuncionarioMode;
            
            if (isFuncionarioMode) {
                body.classList.remove('cliente-mode');
                body.classList.add('funcionario-mode');
                // Opcionalmente, atualiza a URL
                history.replaceState(null, null, '?mode=funcionario');
            } else {
                body.classList.remove('funcionario-mode');
                body.classList.add('cliente-mode');
                // Opcionalmente, atualiza a URL
                history.replaceState(null, null, '?mode=cliente');
            }
        }

        function togglePassword(fieldId) {
    const field = document.getElementById(fieldId);
    const icon = document.getElementById('icon-' + fieldId);

    if (field.type === 'password') {
        field.type = 'text';
        icon.src = '/img/iconeyeclosed.png'; // Ícone para "ocultar senha"
        icon.alt = 'Ocultar senha';
    } else {
        field.type = 'password';
        icon.src = '/img/iconeyeopen.png'; // Ícone para "mostrar senha"
        icon.alt = 'Mostrar senha';
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
            // Define o modo inicial baseado na URL
            setInitialMode();
            
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