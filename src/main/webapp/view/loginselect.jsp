<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="../css/loginselect.css">
    <title>Callisto Bank</title>
</head>
<body>
    <header class="header" id="header">
        <div class="nav-container">
            <div class="logo">
                <img src="../img/image.svg" alt="Callisto Bank" class="logo-image">
            </div>
            
            <nav>
                <ul class="nav-menu">
                    <li><a href="#home">Início</a></li>
                    <li><a href="#services">Serviços</a></li>
                    <li><a href="#about">Sobre</a></li>
                    <li><a href="#contact">Contato</a></li>
                </ul>
            </nav>
            
            <div class="nav-actions">
                <a href="/src/main/java/WebContent/landpage.jsp" class="btn-login">Voltar</a>
            </div>
        </div>
    </header>

    <section class="welcome-section">
        <div class="welcome-content">
            <h1 class="welcome-title">Bem-vindo ao Callisto Bank</h1>
            <p class="welcome-subtitle">Sua jornada financeira começa aqui. Escolha como deseja acessar nossa plataforma.</p>
        </div>
    </section>

    <main class="main-container">
        <div class="selection-container">
            <div class="selection-card cliente-card" onclick="location.href='login.jsp?mode=cliente'">
                <div class="card-background"></div>
                <div class="card-content">
                    <div class="card-icon">👤</div>
                    <h2 class="card-title">CLIENTE</h2>
                    <p class="card-subtitle">Acesse sua conta e gerencie suas finanças</p>
                </div>
            </div>

            <div class="selection-card funcionario-card" onclick="location.href='login.jsp?mode=funcionario'">
                <div class="card-background"></div>
                <div class="card-content">
                    <div class="card-icon">💼</div>
                    <h2 class="card-title">FUNCIONÁRIO</h2>
                    <p class="card-subtitle">Portal interno para colaboradores</p>
                </div>
            </div>
        </div>
    </main>

    <footer class="footer">
        <div class="footer-content">
            <div class="footer-links">
                <a href="#privacy">Política de Privacidade</a>
                <a href="#terms">Termos de Uso</a>
                <a href="#help">Central de Ajuda</a>
                <a href="#contact">Contato</a>
                <a href="#careers">Trabalhe Conosco</a>
            </div>
            <p>&copy; 2024 Callisto Bank. Todos os direitos reservados.</p>
        </div>
    </footer>

    <script src="../js/loginselect.js"></script>
</body>
</html>