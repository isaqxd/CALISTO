<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Callisto Bank - Sua Jornada Financeira</title>
  <link rel="stylesheet" href="css/index.css">
</head>
<body>
<!-- Header -->
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
      <a href="views/loginselect.jsp" class="btn-login">Acessar Conta</a>
    </div>
  </div>
</header>

<!-- Hero Section -->
<section class="hero" id="home">
  <div class="hero-content">
    <h1 class="hero-title">Seu Futuro Financeiro Começa Aqui</h1>
    <p class="hero-subtitle">No Callisto Bank, transformamos suas metas financeiras em realidade com soluções inovadoras, segurança incomparável e atendimento personalizado.</p>
    <div class="hero-cta">
      <a href="views/loginselect.jsp" class="btn-primary" onclick="goToLoginPage()">Começar Agora</a>
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
    <a href="views/loginselect.jsp" class="btn-cta" onclick="goToLoginPage()">Acessar Minha Conta</a>
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

<script src="js/index.js">
</script>
</body>
</html>