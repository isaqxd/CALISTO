<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="css/loginSelectStyle.css">
    <title>Callisto Bank</title>
</head>
<body>
    <!-- Header - Atualizado da landpage -->
    <header class="header" id="header">
        <div class="nav-container">
            <div class="logo">
                <img src="img/image.svg" alt="Callisto Bank" class="logo-image">
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
                <a href="index.jsp" class="btn-login">Voltar</a>

            </div>
        </div>
    </header>

    <!-- Welcome Message Section -->
    <section class="welcome-section">
        <div class="welcome-content">
            <h1 class="welcome-title">Bem-vindo ao Callisto Bank</h1>
            <p class="welcome-subtitle">Sua jornada financeira começa aqui. Escolha como deseja acessar nossa plataforma.</p>
        </div>
    </section>

    <!-- Main Content -->
    <main class="main-container">
        <div class="selection-container">
            <!-- Cliente Card -->
            <div class="selection-card cliente-card" onclick="location.href='login.jsp?mode=cliente'">
                <div class="card-background"></div>
                <div class="card-content">
                    <div class="card-icon">👤</div>
                    <h2 class="card-title">CLIENTE</h2>
                    <p class="card-subtitle">Acesse sua conta e gerencie suas finanças</p>
                </div>
            </div>

            <!-- Funcionário Card -->
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

    <!-- Footer - Atualizado da landpage -->
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

    <script>
        // Header scroll effect e Footer visibility
        window.addEventListener('scroll', () => {
            const header = document.getElementById('header');
            const footer = document.querySelector('.footer');
            
            if (window.scrollY > 50) {
                header.classList.add('scrolled');
                footer.classList.add('visible');
            } else {
                header.classList.remove('scrolled');
                footer.classList.remove('visible');
            }
        });

        // Enhanced hover effects
        document.querySelectorAll('.selection-card').forEach(card => {
            card.addEventListener('mouseenter', function() {
                // Add pulse effect to icon
                const icon = this.querySelector('.card-icon');
                icon.style.animation = 'pulse 1s infinite';
            });
            
            card.addEventListener('mouseleave', function() {
                const icon = this.querySelector('.card-icon');
                icon.style.animation = 'none';
            });
        });

        // Add pulse animation
        const style = document.createElement('style');
        style.textContent = `
            @keyframes pulse {
                0% { transform: scale(1); }
                50% { transform: scale(1.1); }
                100% { transform: scale(1); }
            }
        `;
        document.head.appendChild(style);

        // Add click feedback with sound simulation
        document.querySelectorAll('.selection-card').forEach(card => {
            card.addEventListener('click', function() {
                this.style.transform = 'scale(0.98)';
                setTimeout(() => {
                    this.style.transform = 'scale(1.02)';
                }, 100);
                setTimeout(() => {
                    this.style.transform = '';
                }, 200);
            });
        });

        // Smooth scroll for anchor links
        document.querySelectorAll('a[href^="#"]').forEach(anchor => {
            anchor.addEventListener('click', function (e) {
                if (this.getAttribute('href') === '#login' || this.getAttribute('href') === '#') return;
                
                e.preventDefault();
                const target = document.querySelector(this.getAttribute('href'));
                if (target) {
                    target.scrollIntoView({
                        behavior: 'smooth',
                        block: 'start'
                    });
                }
            });
        });
    </script>
</body>
</html>