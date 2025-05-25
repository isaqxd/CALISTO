<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Callisto Bank - Sua Jornada Financeira</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }

        body {
            font-family: 'Arial', sans-serif;
            line-height: 1.6;
            color: #333;
            overflow-x: hidden;
        }

        /* Header */
        .header {
            background-color: rgba(255, 255, 255, 0.95);
            padding: 1rem 2rem;
            position: fixed;
            top: 0;
            width: 100%;
            z-index: 1000;
            backdrop-filter: blur(10px);
            transition: all 0.3s ease;
        }

        .header.scrolled {
            background-color: rgba(255, 255, 255, 0.98);
            box-shadow: 0 2px 20px rgba(0,0,0,0.1);
        }

        .nav-container {
            display: flex;
            justify-content: space-between;
            align-items: center;
            max-width: 1200px;
            margin: 0 auto;
        }

        .logo {
            display: flex;
            align-items: center;
        }

        .logo-image {
            height: 50px;
            width: auto;
            object-fit: contain;
        }

        .nav-menu {
            display: flex;
            list-style: none;
            gap: 2rem;
        }

        .nav-menu a {
            text-decoration: none;
            color: #595348;
            font-weight: 500;
            transition: color 0.3s ease;
            position: relative;
        }

        .nav-menu a:hover {
            color: #BF7A24;
        }

        .nav-menu a::after {
            content: '';
            position: absolute;
            bottom: -5px;
            left: 0;
            width: 0;
            height: 2px;
            background: linear-gradient(90deg, #F2B950, #BF7A24);
            transition: width 0.3s ease;
        }

        .nav-menu a:hover::after {
            width: 100%;
        }

        .nav-actions {
            display: flex;
            gap: 1rem;
            align-items: center;
        }

        .btn-login, .btn-primary {
            padding: 0.7rem 1.8rem;
            border: none;
            border-radius: 25px;
            cursor: pointer;
            font-weight: 600;
            transition: all 0.3s ease;
            text-decoration: none;
            display: inline-block;
            text-align: center;
        }

        .btn-login {
            background: transparent;
            border: 2px solid #BF7A24;
            color: #BF7A24;
        }

        .btn-login:hover {
            background: #BF7A24;
            color: white;
            transform: translateY(-2px);
            box-shadow: 0 5px 15px rgba(191, 122, 36, 0.3);
        }

        .btn-primary {
            background: linear-gradient(135deg, #F2B950 0%, #BF7A24 100%);
            color: white;
            border: 2px solid transparent;
        }

        .btn-primary:hover {
            transform: translateY(-3px);
            box-shadow: 0 8px 25px rgba(191, 122, 36, 0.4);
            background: linear-gradient(135deg, #BF7A24 0%, #8C480D 100%);
        }

        /* Hero Section */
        .hero {
            height: 100vh;
            background: linear-gradient(135deg, rgba(242, 185, 80, 0.1) 0%, rgba(191, 122, 36, 0.1) 100%),
                        url('data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 1000 1000"><defs><radialGradient id="grad1" cx="50%" cy="50%" r="50%"><stop offset="0%" stop-color="%23F2B950" stop-opacity="0.1"/><stop offset="100%" stop-color="%23BF7A24" stop-opacity="0.05"/></radialGradient></defs><circle cx="200" cy="200" r="100" fill="url(%23grad1)"/><circle cx="800" cy="300" r="150" fill="url(%23grad1)"/><circle cx="400" cy="700" r="120" fill="url(%23grad1)"/></svg>');
            display: flex;
            align-items: center;
            justify-content: center;
            text-align: center;
            position: relative;
            overflow: hidden;
        }

        .hero::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background: url('data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 100 100"><circle cx="10" cy="10" r="1" fill="%23F2B950" opacity="0.3"><animate attributeName="r" values="1;3;1" dur="4s" repeatCount="indefinite"/></circle><circle cx="90" cy="20" r="1.5" fill="%23BF7A24" opacity="0.2"><animate attributeName="r" values="1.5;2.5;1.5" dur="6s" repeatCount="indefinite"/></circle><circle cx="20" cy="80" r="1" fill="%238C480D" opacity="0.4"><animate attributeName="r" values="1;2;1" dur="5s" repeatCount="indefinite"/></circle></svg>');
            animation: float 30s infinite linear;
        }

        .hero-content {
            max-width: 800px;
            z-index: 2;
            position: relative;
            padding: 0 2rem;
        }

        .hero-title {
            font-size: 3.5rem;
            font-weight: bold;
            margin-bottom: 1.5rem;
            color: #260A04;
            text-shadow: 2px 2px 4px rgba(0,0,0,0.1);
            animation: slideInFromBottom 1s ease-out;
        }

        .hero-subtitle {
            font-size: 1.3rem;
            margin-bottom: 2rem;
            color: #595348;
            font-weight: 300;
            animation: slideInFromBottom 1s ease-out 0.3s both;
        }

        .hero-cta {
            display: flex;
            gap: 1rem;
            justify-content: center;
            flex-wrap: wrap;
            animation: slideInFromBottom 1s ease-out 0.6s both;
        }

        /* Services Section */
        .services {
            padding: 6rem 2rem;
            background: linear-gradient(45deg, #F2F2F2 0%, rgba(242, 214, 141, 0.1) 100%);
        }

        .services-container {
            max-width: 1200px;
            margin: 0 auto;
            text-align: center;
        }

        .section-title {
            font-size: 2.5rem;
            margin-bottom: 1rem;
            color: #260A04;
            font-weight: bold;
        }

        .section-subtitle {
            font-size: 1.1rem;
            margin-bottom: 4rem;
            color: #595348;
            max-width: 600px;
            margin-left: auto;
            margin-right: auto;
        }

        .services-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
            gap: 2rem;
            margin-top: 3rem;
        }

        .service-card {
            background: white;
            padding: 2.5rem;
            border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.1);
            transition: all 0.3s ease;
            position: relative;
            overflow: hidden;
        }

        .service-card::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            height: 4px;
            background: linear-gradient(90deg, #F2B950, #BF7A24, #8C480D);
        }

        .service-card:hover {
            transform: translateY(-10px);
            box-shadow: 0 20px 40px rgba(191, 122, 36, 0.2);
        }

        .service-icon {
            font-size: 3rem;
            margin-bottom: 1.5rem;
            color: #BF7A24;
        }

        .service-title {
            font-size: 1.5rem;
            margin-bottom: 1rem;
            color: #260A04;
            font-weight: bold;
        }

        .service-description {
            color: #595348;
            line-height: 1.6;
        }

        /* About Section */
        .about {
            padding: 6rem 2rem;
            background: linear-gradient(135deg, rgba(89, 83, 72, 0.05) 0%, rgba(38, 10, 4, 0.05) 100%);
        }

        .about-container {
            max-width: 1200px;
            margin: 0 auto;
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 4rem;
            align-items: center;
        }

        .about-content h2 {
            font-size: 2.5rem;
            margin-bottom: 1.5rem;
            color: #260A04;
        }

        .about-content p {
            margin-bottom: 1.5rem;
            color: #595348;
            font-size: 1.1rem;
        }

        .about-stats {
            display: grid;
            grid-template-columns: repeat(2, 1fr);
            gap: 2rem;
            margin-top: 2rem;
        }

        .stat-item {
            text-align: center;
            padding: 1.5rem;
            background: white;
            border-radius: 10px;
            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
        }

        .stat-number {
            font-size: 2rem;
            font-weight: bold;
            color: #BF7A24;
            margin-bottom: 0.5rem;
        }

        .stat-label {
            color: #595348;
            font-size: 0.9rem;
        }

        .about-image {
            position: relative;
            border-radius: 15px;
            overflow: hidden;
            height: 400px;
            background: linear-gradient(135deg, #F2B950 0%, #BF7A24 100%);
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 8rem;
            color: rgba(255,255,255,0.3);
        }

        /* CTA Section */
        .cta-section {
            padding: 4rem 2rem;
            background: linear-gradient(135deg, #F2B950 0%, #BF7A24 50%, #8C480D 100%);
            text-align: center;
            color: white;
            position: relative;
            overflow: hidden;
        }

        .cta-section::before {
            content: '';
            position: absolute;
            top: 0;
            left: 0;
            right: 0;
            bottom: 0;
            background: url('data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 100 100"><circle cx="20" cy="20" r="2" fill="rgba(255,255,255,0.1)"/><circle cx="80" cy="30" r="1.5" fill="rgba(255,255,255,0.1)"/><circle cx="40" cy="70" r="2.5" fill="rgba(255,255,255,0.1)"/></svg>');
            animation: float 25s infinite linear;
        }

        .cta-content {
            max-width: 600px;
            margin: 0 auto;
            position: relative;
            z-index: 2;
        }

        .cta-title {
            font-size: 2.5rem;
            margin-bottom: 1rem;
            font-weight: bold;
        }

        .cta-subtitle {
            font-size: 1.2rem;
            margin-bottom: 2rem;
            opacity: 0.9;
        }

        .btn-cta {
            background: white;
            color: #BF7A24;
            font-size: 1.1rem;
            padding: 1rem 2.5rem;
            border-radius: 30px;
            text-decoration: none;
            font-weight: bold;
            transition: all 0.3s ease;
            display: inline-block;
        }

        .btn-cta:hover {
            transform: translateY(-3px);
            box-shadow: 0 10px 30px rgba(0,0,0,0.3);
            background: #F2F2F2;
        }

        /* Footer */
        .footer {
            background: #260A04;
            color: #F2F2F2;
            padding: 3rem 2rem 1rem;
            text-align: center;
        }

        .footer-content {
            max-width: 1200px;
            margin: 0 auto;
        }

        .footer-links {
            display: flex;
            justify-content: center;
            gap: 2rem;
            margin-bottom: 2rem;
            flex-wrap: wrap;
        }

        .footer-links a {
            color: #F2D68D;
            text-decoration: none;
            transition: color 0.3s ease;
        }

        .footer-links a:hover {
            color: #F2B950;
        }

        /* Animations */
        @keyframes slideInFromBottom {
            from {
                opacity: 0;
                transform: translateY(50px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        @keyframes float {
            0% { transform: translateX(-100px); }
            100% { transform: translateX(100px); }
        }

        @keyframes fadeInUp {
            from {
                opacity: 0;
                transform: translateY(30px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }

        /* Responsive Design */
        @media (max-width: 768px) {
            .nav-menu {
                display: none;
            }

            .hero-title {
                font-size: 2.5rem;
            }

            .hero-subtitle {
                font-size: 1.1rem;
            }

            .hero-cta {
                flex-direction: column;
                align-items: center;
            }

            .about-container {
                grid-template-columns: 1fr;
                gap: 2rem;
            }

            .about-stats {
                grid-template-columns: 1fr;
            }

            .services-grid {
                grid-template-columns: 1fr;
            }

            .section-title {
                font-size: 2rem;
            }

            .cta-title {
                font-size: 2rem;
            }
        }

        /* Scroll animations */
        .fade-in {
            opacity: 0;
            transform: translateY(30px);
            transition: all 0.6s ease;
        }

        .fade-in.visible {
            opacity: 1;
            transform: translateY(0);
        }
    </style>
</head>
<body>
    <!-- Header -->
    <header class="header" id="header">
        <div class="nav-container">
            <div class="logo">
                <img src="/src/main/WebContent/imagens/image.svg" alt="Callisto Bank" class="logo-image">
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
                <a href="/src/main/WebContent/outraspaginas/loginselect.jsp" class="btn-login">Acessar Conta</a>
            </div>
        </div>
    </header>

    <!-- Hero Section -->
    <section class="hero" id="home">
        <div class="hero-content">
            <h1 class="hero-title">Seu Futuro Financeiro Começa Aqui</h1>
            <p class="hero-subtitle">No Callisto Bank, transformamos suas metas financeiras em realidade com soluções inovadoras, segurança incomparável e atendimento personalizado.</p>
            <div class="hero-cta">
                <a href="/src/main/WebContent/outraspaginas/loginselect.jsp" class="btn-primary" onclick="goToLoginPage()">Começar Agora</a>
                <a href="#services" class="btn-login">Conhecer Serviços</a>
            </div>
        </div>
    </section>

    <!-- Services Section -->
    <section class="services fade-in" id="services">
        <div class="services-container">
            <h2 class="section-title">Nossos Serviços</h2>
            <p class="section-subtitle">Oferecemos uma gama completa de soluções financeiras para atender todas as suas necessidades</p>
            
            <div class="services-grid">
                <div class="service-card">
                    <div class="service-icon">💳</div>
                    <h3 class="service-title">Conta Digital</h3>
                    <p class="service-description">Conta corrente completa sem taxas, com cartão de débito e crédito, transferências ilimitadas e PIX instantâneo.</p>
                </div>
                
                <div class="service-card">
                    <div class="service-icon">📈</div>
                    <h3 class="service-title">Investimentos</h3>
                    <p class="service-description">Diversifique seu patrimônio com nossa plataforma de investimentos: CDB, fundos, ações e muito mais.</p>
                </div>
                
                <div class="service-card">
                    <div class="service-icon">🏠</div>
                    <h3 class="service-title">Crédito Imobiliário</h3>
                    <p class="service-description">Realize o sonho da casa própria com as melhores condições do mercado e aprovação rápida.</p>
                </div>
                
                <div class="service-card">
                    <div class="service-icon">💼</div>
                    <h3 class="service-title">Empresarial</h3>
                    <p class="service-description">Soluções completas para sua empresa: conta jurídica, máquinhas de cartão e capital de giro.</p>
                </div>
                
                <div class="service-card">
                    <div class="service-icon">🛡️</div>
                    <h3 class="service-title">Seguros</h3>
                    <p class="service-description">Proteja o que mais importa: seguro de vida, residencial, automotivo e empresarial.</p>
                </div>
                
                <div class="service-card">
                    <div class="service-icon">📱</div>
                    <h3 class="service-title">App Mobile</h3>
                    <p class="service-description">Gerencie suas finanças na palma da mão com nosso app intuitivo e seguro, disponível 24/7.</p>
                </div>
            </div>
        </div>
    </section>

    <!-- About Section -->
    <section class="about fade-in" id="about">
        <div class="about-container">
            <div class="about-content">
                <h2>Por que escolher o Callisto Bank?</h2>
                <p>Somos mais que um banco, somos seu parceiro financeiro. Com mais de uma década de experiência, oferecemos soluções inovadoras que se adaptam ao seu estilo de vida.</p>
                <p>Nossa missão é democratizar o acesso aos serviços financeiros, proporcionando uma experiência digital excepcional sem abrir mão do atendimento humano quando você precisar.</p>
                
                <div class="about-stats">
                    <div class="stat-item">
                        <div class="stat-number">2M+</div>
                        <div class="stat-label">Clientes Ativos</div>
                    </div>
                    <div class="stat-item">
                        <div class="stat-number">10+</div>
                        <div class="stat-label">Anos de Mercado</div>
                    </div>
                    <div class="stat-item">
                        <div class="stat-number">99.9%</div>
                        <div class="stat-label">Disponibilidade</div>
                    </div>
                    <div class="stat-item">
                        <div class="stat-number">5★</div>
                        <div class="stat-label">Avaliação App</div>
                    </div>
                </div>
            </div>
            
            <div class="about-image">
                🏦
            </div>
        </div>
    </section>

    <!-- CTA Section -->
    <section class="cta-section fade-in">
        <div class="cta-content">
            <h2 class="cta-title">Pronto para começar?</h2>
            <p class="cta-subtitle">Abra sua conta em poucos minutos e descubra um novo jeito de cuidar do seu dinheiro</p>
            <a href="/src/main/WebContent/outraspaginas/loginselect.jsp" class="btn-cta" onclick="goToLoginPage()">Acessar Minha Conta</a>
        </div>
    </section>

    <!-- Footer -->
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
        // Header scroll effect
        window.addEventListener('scroll', () => {
            const header = document.getElementById('header');
            if (window.scrollY > 50) {
                header.classList.add('scrolled');
            } else {
                header.classList.remove('scrolled');
            }
        });

        // Scroll animations
        const observerOptions = {
            threshold: 0.1,
            rootMargin: '0px 0px -50px 0px'
        };

        const observer = new IntersectionObserver((entries) => {
            entries.forEach(entry => {
                if (entry.isIntersecting) {
                    entry.target.classList.add('visible');
                }
            });
        }, observerOptions);

        document.querySelectorAll('.fade-in').forEach(el => {
            observer.observe(el);
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

        // Function to navigate to login page
        function goToLoginPage() {
            // Aqui você pode colocar o caminho real da sua página de login
            window.location.href = 'login-selection.html'; // ou o nome que você der para a página de seleção
            
            // Alternativamente, se for no mesmo diretório:
            // window.location.href = './login-selection.html';
        }

        // Add hover effect to service cards
        document.querySelectorAll('.service-card').forEach(card => {
            card.addEventListener('mouseenter', function() {
                this.style.transform = 'translateY(-15px) scale(1.02)';
            });
            
            card.addEventListener('mouseleave', function() {
                this.style.transform = 'translateY(0) scale(1)';
            });
        });

        // Parallax effect for hero section
        window.addEventListener('scroll', () => {
            const scrolled = window.pageYOffset;
            const hero = document.querySelector('.hero');
            if (hero) {
                hero.style.transform = `translateY(${scrolled * 0.5}px)`;
            }
        });
    </script>
</body>
</html>