<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Callisto Bank - Painel do Funcionário</title>
    <link rel="stylesheet" href="/src/main/webapp/css/portalfuncionario.css">
    <link rel="stylesheet" href="/src/main/webapp/css/cartao.css">
</head>
<body class="funcionario-mode">
    <div class="background-layer bg-funcionario"></div>

    <header class="header">
        <div class="logo">
            <img src="/src/main/webapp/imagens/image.svg" alt="Callisto Bank" class="logo-image">
        </div>
        <div class="user-info">
            <span>Olá, Luís Eduardo</span>
            <a href="login.html" class="logout-btn">Logout</a>
        </div>
    </header>

    <div class="app-container">
        <aside class="sidebar">
            <div class="sidebar-header">
                <h3>Menu Principal</h3>
            </div>
            <nav class="sidebar-nav">
                <ul>
                    <li><a href="#dashboard" id="menu-dashboard" class="active"><span class="menu-icon">📊</span><span>Dashboard</span></a></li>
                    <li><a href="#clientes" id="menu-clientes"><span class="menu-icon">👥</span><span>Contas de clientes</span></a></li>
                    <li><a href="#transacoes" id="menu-transacoes"><span class="menu-icon">💳</span><span>Transações</span></a></li>
                    <li><a href="#analise" id="menu-analise"><span class="menu-icon">📈</span><span>Análise da Conta</span></a></li>
                    <li><a href="#relatorios" id="menu-relatorios"><span class="menu-icon">📄</span><span>Relatórios</span></a></li>
                    <li><a href="#ferramentas" id="menu-ferramentas"><span class="menu-icon">🔧</span><span>Ferramentas Internas</span></a></li>
                    <li><a href="#suporte" id="menu-suporte"><span class="menu-icon">🎧</span><span>Suporte</span></a></li>
                    <li><a href="#configuracoes" id="menu-configuracoes"><span class="menu-icon">⚙️</span><span>Configurações</span></a></li>
                </ul>
            </nav>
        </aside>

        <main class="main-content" id="main-content">
            <!-- O JS irá preencher aqui -->
            <button onclick="exportarRelatorioPDF('tabela-extrato')">Exportar PDF</button>
            <button onclick="exportarRelatorioExcel('tabela-extrato')">Exportar Excel</button>
        </main>
    </div>
    <footer class="app-footer" style="position:fixed; right:0; bottom:0; width:auto; min-width:220px; border-top-left-radius:12px;">
        &copy; <span id="currentYear"></span> Callisto Bank - Todos os direitos reservados.
    </footer>

    <!-- Coloque as bibliotecas ANTES do seu JS -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.5.1/jspdf.umd.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.18.5/xlsx.full.min.js"></script>
    <script src="/src/main/webapp/script/portalfuncionario.js"></script>
</body>
</html>