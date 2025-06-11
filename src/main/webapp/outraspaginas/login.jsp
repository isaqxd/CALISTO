<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Callisto Bank - Login</title>
    <link rel="stylesheet" href="../css/login.css">
</head>
<body class="cliente-mode">
<div class="background-layer bg-cliente"></div>
<div class="background-layer bg-funcionario"></div>

<header class="header">
    <div class="logo">
        <img src="../img/image.svg" alt="Callisto Bank" class="logo-image">
    </div>
</header>

<div class="login-container">
    <div class="content-slider">
        <div class="login-side cliente-side">
            <div class="welcome-section">
                <h1 class="welcome-title">Bem vindo !</h1>
                <h2 class="welcome-subtitle">Acesso do Cliente</h2>
                <p class="welcome-description">Gerencie suas finanças com segurança</p><br><br>
                <div class="toggle-section">
                    <p class="toggle-text">Acesso para funcionários?</p>
                    <button type="button" class="toggle-btn" onclick="toggleMode()">Portal do Funcionário</button>
                </div>
            </div>

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
                                <img src="../img/iconeyeclosed.png" alt="Mostrar senha"
                                     id="icon-clientePassword" width="20">
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
                        <button type="button" class="toggle-btn"
                                onclick="location.href='../landpage.jsp'">Sair
                        </button>
                    </div>
                </form>
            </div>
        </div>

        <div class="login-side funcionario-side">
            <div class="welcome-section">
                <h1 class="welcome-title">Bem vindo !</h1>
                <h2 class="welcome-subtitle">Portal do Funcionário</h2>
                <p class="welcome-description">Acesso restrito para colaboradores</p><br><br>
                <div class="toggle-section">
                    <p class="toggle-text">Acesso para funcionários?</p>
                    <button type="button" class="toggle-btn" onclick="toggleMode()">Acesso do Cliente</button>
                </div>
            </div>

            <div class="form-section">
                <form class="login-form" id="funcionarioForm">
                    <h2 class="form-title">Login</h2>

                    <div class="form-group">
                        <label class="form-label">Código do Funcionário</label>
                        <input type="text" class="form-input" placeholder="Ex: FUN0001" id="funcionarioCodigo" required>
                    </div>

                    <div class="form-group">
                        <label class="form-label">Senha</label>
                        <div class="password-container">
                            <input type="password" class="form-input" placeholder="Senha" id="funcionarioPassword"
                                   required>
                            <button type="button" class="password-toggle"
                                    onclick="togglePassword('funcionarioPassword')">
                                <img src="/src/main/webapp/imagens/iconeyeclosed.png" alt="Mostrar senha"
                                     id="icon-funcionarioPassword" width="20">
                            </button>
                        </div>
                    </div>

                    <button type="submit" class="login-btn">Entrar</button>

                    <div class="forgot-password">
                        <a href="#forgot">Esqueceu a senha? Sair</a>
                    </div>

                    <div class="toggle-section">
                        <p class="toggle-text">Acesso para clientes?</p>
                        <button type="button" class="toggle-btn"
                                onclick="location.href='../landpage.jsp'">Sair
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script src="../js/login.js"></script>
</body>
</html>