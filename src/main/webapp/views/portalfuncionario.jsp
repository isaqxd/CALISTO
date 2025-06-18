<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Callisto Bank - Painel do Funcionário</title>
    <!-- Inclusão do Font Awesome para ícones -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css">
    <!-- Inclusão do jsPDF para exportar relatórios -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.5.1/jspdf.umd.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf-autotable/3.5.23/jspdf.plugin.autotable.min.js"></script>
    <style>
        /* Reset e Estilos Base */
        * { margin: 0; padding: 0; box-sizing: border-box; }
        html, body { height: 100%; margin: 0; padding: 0; }
        body {
            font-family: 'Arial', sans-serif;
            min-height: 100vh;
            display: flex;
            flex-direction: column;
            overflow-x: hidden;
            position: relative;
            transition: all 0.8s cubic-bezier(0.4, 0, 0.2, 1);
            background-color: #f5f5f2;
        }

        /* Camada de Fundo Animada */
        .background-layer {
            position: fixed; top: 0; left: 0; width: 100%; height: 100%;
            transition: all 0.8s cubic-bezier(0.4, 0, 0.2, 1);
            z-index: -1;
        }
        .bg-funcionario {
            background: linear-gradient(135deg, rgba(89, 83, 72, 0.08) 0%, rgba(38, 10, 4, 0.12) 50%, rgba(0, 0, 0, 0.04) 100%), #f5f5f2;
            opacity: 1;
        }
        .bg-funcionario::before {
            content: ''; position: absolute; top: 0; left: 0; right: 0; bottom: 0;
            background: url('data:image/svg+xml,<svg xmlns="http://www.w3.org/2000/svg" viewBox="0 0 100 100"><circle cx="15" cy="25" r="1.5" fill="%23595348" opacity="0.3"><animate attributeName="r" values="1.5;3;1.5" dur="5s" repeatCount="indefinite"/></circle><circle cx="85" cy="40" r="2" fill="%23260A04" opacity="0.2"><animate attributeName="r" values="2;3.5;2" dur="7s" repeatCount="indefinite"/></circle><circle cx="50" cy="80" r="1.8" fill="%23000000" opacity="0.1"><animate attributeName="r" values="1.8;3.2;1.8" dur="6s" repeatCount="indefinite"/></circle></svg>');
            animation: float 30s infinite linear reverse;
            z-index: -2;
        }
        @keyframes float {
            0% { transform: translateX(-50px) translateY(-20px); }
            50% { transform: translateX(50px) translateY(20px); }
            100% { transform: translateX(-50px) translateY(-20px); }
        }

        /* Cabeçalho */
        .header {
            position: fixed; top: 0; left: 0; right: 0; padding: 1rem 2rem;
            z-index: 1000; background-color: rgba(245, 245, 242, 0.85);
            box-shadow: 0 2px 10px rgba(0,0,0,0.05); display: flex;
            justify-content: space-between; align-items: center;
        }
        .logo { display: flex; align-items: center; }
        .logo-image { height: 50px; width: auto; object-fit: contain; }
        .user-info { display: flex; align-items: center; color: #260A04; }
        .user-info span { margin-right: 1rem; font-weight: 500; }
        .logout-btn {
            background: linear-gradient(135deg, #595348 0%, #260A04 100%); color: white;
            padding: 0.6rem 1.2rem; border: none; border-radius: 20px;
            text-decoration: none; font-size: 0.9rem; font-weight: 600;
            transition: all 0.3s ease;
        }
        .logout-btn:hover {
            background: linear-gradient(135deg, #260A04 0%, #000000 100%);
            transform: translateY(-2px); box-shadow: 0 4px 15px rgba(38, 10, 4, 0.2);
        }

        /* Layout Principal */
        .app-container {
            flex: 1 0 auto; display: flex; flex-direction: row; min-height: 0;
            padding-top: 80px; height: 100vh;
        }

        /* Barra Lateral (Menu) */
        .sidebar {
            width: 260px; background: rgba(235, 233, 230, 0.7);
            padding: 1.5rem 0; height: calc(100vh - 80px); position: fixed;
            top: 80px; left: 0; overflow-y: auto;
            box-shadow: 3px 0 15px rgba(0,0,0,0.05); z-index: 900;
        }
        .sidebar-header {
            padding: 0 1.5rem 1.5rem 1.5rem; text-align: center;
            border-bottom: 1px solid rgba(38, 10, 4, 0.1); margin-bottom: 1rem;
        }
        .sidebar-header h3 { color: #260A04; font-size: 1.3rem; }
        .sidebar-nav ul { list-style: none; }
        .sidebar-nav li a {
            display: flex; align-items: center; padding: 0.9rem 1.5rem; color: #260A04;
            text-decoration: none; font-weight: 500; font-size: 0.95rem;
            transition: all 0.3s ease; border-left: 4px solid transparent; cursor: pointer;
        }
        .sidebar-nav li a .menu-icon { margin-right: 0.8rem; font-size: 1.1rem; width: 20px; text-align: center; }
        .sidebar-nav li a.active, .sidebar-nav li a:hover {
            background-color: rgba(89, 83, 72, 0.1); color: #000; border-left-color: #260A04;
        }
        .sidebar-nav li a:hover { transform: translateX(5px); }
        
        /* Submenu */
        .submenu {
            display: none; background: rgba(255, 255, 255, 0.3);
            margin-left: 1rem; border-left: 2px solid #260A04;
        }
        .submenu.active { display: block; }
        .submenu li a { padding: 0.6rem 1rem; font-size: 0.85rem; font-weight: 400; border-left: none; }
        .submenu li a:hover { background-color: rgba(89, 83, 72, 0.15); transform: translateX(3px); }

        /* Conteúdo Principal */
        .main-content {
            flex: 1 1 auto; padding: 2rem; margin-left: 260px;
            overflow-y: auto; min-height: 0;
        }
        .content-header { margin-bottom: 2rem; }
        .content-header h1 { font-size: 2.2rem; color: #260A04; font-weight: bold; margin-bottom: 0.5rem; }
        .content-header p { font-size: 1.1rem; color: #595348; line-height: 1.6; }

        /* Widgets do Dashboard */
        .dashboard-widgets {
            display: grid; grid-template-columns: repeat(auto-fit, minmax(280px, 1fr)); gap: 1.5rem;
        }
        .widget {
            background: rgba(255, 255, 255, 0.9); padding: 1.5rem; border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.07);
            transition: transform 0.3s ease, box-shadow 0.3s ease;
        }
        .widget:hover { transform: translateY(-5px); box-shadow: 0 15px 35px rgba(0,0,0,0.1); }
        .widget h3 {
            font-size: 1.2rem; color: #260A04; margin-bottom: 1rem;
            border-bottom: 1px solid rgba(38, 10, 4, 0.1); padding-bottom: 0.5rem;
        }
        .widget p { font-size: 0.95rem; color: #595348; line-height: 1.6; }
        .widget .data-point { font-size: 1.8rem; color: #260A04; font-weight: bold; margin-bottom: 0.5rem; }
        
        /* Formulários */
        .form-container {
            background: rgba(255, 255, 255, 0.9); padding: 2rem; border-radius: 15px;
            box-shadow: 0 10px 30px rgba(0,0,0,0.07); margin-bottom: 2rem;
        }
        .form-grid { display: grid; grid-template-columns: repeat(auto-fit, minmax(300px, 1fr)); gap: 1.5rem; }
        .form-group { display: flex; flex-direction: column; }
        .form-group label { color: #260A04; font-weight: 600; margin-bottom: 0.5rem; }
        .form-group input, .form-group select, .form-group textarea {
            padding: 0.8rem; border: 1px solid #cbbfae; border-radius: 8px; font-size: 1rem;
            background: #f5f5f2; color: #260A04; transition: border 0.2s;
        }
        .form-group input:focus, .form-group select:focus, .form-group textarea:focus {
            border: 2px solid #260A04; outline: none; background: #fff;
        }

        /* Botões */
        .btn-primary, .btn-secondary, .btn-danger, .btn-edit {
            color: white; padding: 0.8rem 2rem; border: none; border-radius: 25px;
            font-size: 1rem; font-weight: 600; cursor: pointer;
            transition: all 0.3s ease;
        }
        .btn-primary {
             background: linear-gradient(135deg, #595348 0%, #260A04 100%);
             box-shadow: 0 4px 15px rgba(38, 10, 4, 0.2);
        }
        .btn-primary:hover {
            background: linear-gradient(135deg, #260A04 0%, #000000 100%);
            transform: translateY(-2px); box-shadow: 0 6px 20px rgba(38, 10, 4, 0.3);
        }
        .btn-secondary, .btn-danger, .btn-edit {
            padding: 0.6rem 1.5rem; font-size: 0.9rem; border-radius: 20px;
        }
        .btn-secondary { background: #BF7A24; margin-right: 1rem; }
        .btn-secondary:hover { background: #8C480D; transform: translateY(-2px); }
        .btn-danger { background: #d32f2f; }
        .btn-danger:hover { background: #b71c1c; transform: translateY(-2px); }
        .btn-edit { background: #1976d2; color: white; margin-right: 0.5rem; }
        .btn-edit:hover { background: #115293; transform: translateY(-2px); }
        
        /* Tabelas */
        .table-container {
            background: rgba(255, 255, 255, 0.9); border-radius: 15px; padding: 1.5rem;
            box-shadow: 0 10px 30px rgba(0,0,0,0.07); overflow-x: auto;
        }
        table { width: 100%; border-collapse: collapse; margin-top: 1rem; }
        th, td { padding: 1rem; text-align: left; border-bottom: 1px solid #e0e0e0; }
        th { background-color: #f5f5f2; color: #260A04; font-weight: 600; }
        tr:hover { background-color: rgba(89, 83, 72, 0.05); }

        /* Rodapé */
        .app-footer {
            position: fixed; right: 0; bottom: 0; width: auto; min-width: 220px;
            border-top-left-radius: 12px; background: #222; color: #fff;
            text-align: center; padding: 1rem 2rem; z-index: 2; margin-top: 0;
        }
        
        /* Notificações/Alertas */
        #notification-area { position: fixed; top: 90px; right: 2rem; z-index: 3000; width: 350px; }
        .alert {
            padding: 1rem; margin-bottom: 1rem; border-radius: 8px;
            color: #fff; font-weight: 500;
            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
            opacity: 0;
            transform: translateX(100%);
            transition: all 0.5s ease-in-out;
        }
        .alert.show { opacity: 1; transform: translateX(0); }
        .alert.alert-success { background-color: #28a745; }
        .alert.alert-danger { background-color: #dc3545; }

        /* Modal para Edição */
        .modal-overlay {
            position: fixed;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            background: rgba(0, 0, 0, 0.6);
            display: none;
            justify-content: center;
            align-items: center;
            z-index: 2000;
        }
        .modal-overlay.show { display: flex; }
        .modal-content {
            background: #f5f5f2;
            padding: 2rem;
            border-radius: 15px;
            width: 90%;
            max-width: 800px;
            max-height: 90vh;
            overflow-y: auto;
            position: relative;
        }
        .modal-close-btn {
            position: absolute;
            top: 1rem;
            right: 1rem;
            background: none;
            border: none;
            font-size: 1.5rem;
            cursor: pointer;
            color: #888;
        }

        /* --- ESTILOS DA GERAÇÃO DE CARTÃO --- */
        #geracao-cartao .card-generator-container {
            display: flex;
            gap: 2rem;
            flex-wrap: wrap;
            align-items: flex-start;
        }
        #geracao-cartao .card-visual-section {
            flex: 1 1 380px;
            perspective: 1000px;
        }
        #geracao-cartao .form-and-advantages-section {
            flex: 1 1 450px;
        }
        #geracao-cartao .credit-card {
            width: 100%;
            max-width: 380px;
            height: 240px;
            position: relative;
            transform-style: preserve-3d;
            transition: transform 0.6s;
        }
        #geracao-cartao .card-inner {
            position: absolute;
            width: 100%;
            height: 100%;
            transition: transform 0.8s;
            transform-style: preserve-3d;
        }
        #geracao-cartao .card-face {
            position: absolute;
            width: 100%;
            height: 100%;
            backface-visibility: hidden;
            border-radius: 15px;
            box-shadow: 0 15px 35px rgba(0,0,0,0.2);
            display: flex;
            flex-direction: column;
            justify-content: space-between;
            padding: 1.5rem;
            color: white;
            font-family: 'Courier New', Courier, monospace;
        }
        /* Estilos dos tipos de cartão */
        #geracao-cartao .card-front.platinum { background: linear-gradient(135deg, #e5e5e5 0%, #d4d4d4 100%); color: #333; }
        #geracao-cartao .card-front.gold { background: linear-gradient(135deg, #f0c74c 0%, #d8ae40 100%); }
        #geracao-cartao .card-front.black { background: linear-gradient(135deg, #333 0%, #111 100%); }
        #geracao-cartao .card-header { display: flex; justify-content: space-between; align-items: flex-start; }
        #geracao-cartao .bank-name { font-size: 1.2rem; font-weight: bold; }
        #geracao-cartao .card-type { font-size: 1rem; font-style: italic; }
        #geracao-cartao .chip { width: 50px; height: 40px; background: linear-gradient(135deg, #d4af37, #b48d21); border-radius: 5px; }
        #geracao-cartao .card-number { font-size: 1.5rem; letter-spacing: 2px; text-align: center; margin-top: 1rem; }
        #geracao-cartao .card-info-flex { display: flex; justify-content: space-between; align-items: flex-end; }
        #geracao-cartao .card-field { font-size: 0.9rem; }
        #geracao-cartao .card-label { font-size: 0.7rem; color: rgba(255,255,255,0.7); }
        #geracao-cartao .card-front.platinum .card-label { color: rgba(0,0,0,0.5); }
        #geracao-cartao .card-value { font-weight: bold; }
        #geracao-cartao .card-advantages { margin-top: 1.5rem; padding: 1rem; background: rgba(255,255,255,0.05); border-radius: 8px;}
        #geracao-cartao .card-advantages h3 { font-size: 1.1rem; border-bottom: 1px solid; padding-bottom: 0.5rem; margin-bottom: 1rem; }
        #geracao-cartao .card-advantages ul { list-style: inside; padding-left: 0.5rem; }
        #geracao-cartao .card-advantages li { margin-bottom: 0.5rem; }

        /* Estilos da Seção de Transações */
        .transaction-container {
            display: flex;
            gap: 2rem;
            flex-wrap: wrap;
        }
        .transaction-forms { flex: 1 1 400px; }
        .transaction-history { flex: 2 1 500px; }
        .transaction-tabs { display: flex; border-bottom: 2px solid #ddd; margin-bottom: 1.5rem; }
        .transaction-tab {
            padding: 1rem 1.5rem;
            cursor: pointer;
            border: none;
            background: none;
            font-size: 1rem;
            font-weight: 600;
            color: #595348;
            position: relative;
        }
        .transaction-tab.active {
            color: #260A04;
        }
        .transaction-tab.active::after {
            content: '';
            position: absolute;
            bottom: -2px;
            left: 0;
            width: 100%;
            height: 2px;
            background: #260A04;
        }
        .transaction-panel { display: none; }
        .transaction-panel.active { display: block; }
        .account-balance-display {
            background: #e9e7e4;
            padding: 1rem;
            border-radius: 8px;
            margin-top: 1.5rem;
            text-align: center;
        }
        .account-balance-display p { font-size: 1rem; color: #595348; }
        .account-balance-display h3 { font-size: 2rem; color: #260A04; }

        /* Responsividade */
        @media (max-width: 768px) {
            .sidebar { width: 60px; }
            .sidebar-header h3, .sidebar-nav li a span:not(.menu-icon) { display: none; }
            .sidebar-nav li a { justify-content: center; padding: 1rem 0.5rem; }
            .sidebar-nav li a .menu-icon { margin-right: 0; }
            .main-content { margin-left: 60px; padding: 1.5rem; }
            .submenu { display: none !important; }
            #notification-area { width: calc(100% - 4rem); right: 2rem; left: 2rem; }
            #geracao-cartao .card-generator-container { flex-direction: column; }
            .transaction-container { flex-direction: column; }
        }

        /* Seções ocultas */
        .section { display: none; }
        .section.active { display: block; animation: fadeIn 0.5s ease-in-out; }
        @keyframes fadeIn { from { opacity: 0; } to { opacity: 1; } }
    </style>
</head>
<body class="funcionario-mode">
<div class="background-layer bg-funcionario"></div>

<header class="header">
    <div class="logo">
        <img src="/src/main/webapp/img/image.svg" alt="Callisto Bank" class="logo-image">
    </div>
    <div class="user-info">
        <span>Olá, Luís Eduardo</span>
        <a href="#" class="logout-btn">Logout</a>
    </div>
</header>

<div class="app-container">
    <aside class="sidebar">
        <div class="sidebar-header">
            <h3>Menu Principal</h3>
        </div>
        <nav class="sidebar-nav">
            <ul>
                <li><a href="#" data-section="dashboard" class="menu-item active"><span class="menu-icon"><i class="fas fa-chart-line"></i></span><span>Dashboard</span></a></li>
                <li>
                    <a href="#" class="menu-toggle" data-submenu="funcionario-submenu">
                        <span class="menu-icon"><i class="fas fa-user-tie"></i></span>
                        <span>Gerenciar Funcionário</span>
                    </a>
                    <ul class="submenu" id="funcionario-submenu">
                        <li><a href="#" data-section="cadastro-funcionario" class="menu-item">Cadastro de Funcionário</a></li>
                        <li><a href="#" data-section="alterar-funcionario" class="menu-item">Consultar / Alterar</a></li>
                    </ul>
                </li>
                 <li>
                    <a href="#" class="menu-toggle" data-submenu="cliente-submenu">
                        <span class="menu-icon"><i class="fas fa-users"></i></span>
                        <span>Gerenciar Cliente</span>
                    </a>
                    <ul class="submenu" id="cliente-submenu">
                        <li><a href="#" data-section="cadastro-cliente" class="menu-item">Cadastro de Cliente</a></li>
                        <li><a href="#" data-section="abertura-conta" class="menu-item">Abertura de Conta</a></li>
                        <li><a href="#" data-section="geracao-cartao" class="menu-item">Geração de Cartão</a></li>
                        <li><a href="#" data-section="alterar-cliente" class="menu-item">Consultar / Alterar</a></li>
                        <li><a href="#" data-section="encerramento-conta" class="menu-item">Encerramento de Conta</a></li>
                        <li><a href="#" data-section="transacoes-cliente" class="menu-item">Transações</a></li>
                        <li><a href="#" data-section="analise-conta" class="menu-item">Análise de Conta</a></li>
                    </ul>
                </li>
                <li><a href="#" data-section="relatorios" class="menu-item"><span class="menu-icon"><i class="fas fa-file-alt"></i></span><span>Relatórios</span></a></li>
                <li><a href="#" data-section="ferramentas" class="menu-item"><span class="menu-icon"><i class="fas fa-wrench"></i></span><span>Ferramentas Internas</span></a></li>
                <li><a href="#" data-section="configuracoes" class="menu-item"><span class="menu-icon"><i class="fas fa-cog"></i></span><span>Configurações</span></a></li>
            </ul>
        </nav>
    </aside>

    <main class="main-content" id="main-content">
        <!-- Área de Notificação Dinâmica -->
        <div id="notification-area"></div>

        <!-- Dashboard -->
        <section id="dashboard" class="section active">
            <div class="content-header"><h1>Dashboard</h1><p>Visão geral das operações do Callisto Bank</p></div>
            <div class="dashboard-widgets">
                <div class="widget">
                    <h3>Contas Ativas</h3>
                    <div class="data-point" id="db-total-contas">0</div><p>Total de contas ativas no sistema</p>
                </div>
                <div class="widget">
                    <h3>Clientes Cadastrados</h3>
                    <div class="data-point" id="db-total-clientes">0</div><p>Total de clientes no sistema</p>
                </div>
                 <div class="widget">
                    <h3>Funcionários Ativos</h3>
                    <div class="data-point" id="db-total-funcionarios">0</div><p>Total de funcionários no sistema</p>
                </div>
                <div class="widget">
                    <h3>Volume Financeiro Total</h3>
                    <div class="data-point" id="db-volume-total">R$ 0,00</div><p>Soma dos saldos de todas as contas</p>
                </div>
            </div>
        </section>

        <!-- Cadastro de Funcionário -->
        <section id="cadastro-funcionario" class="section">
            <div class="content-header"><h1>Cadastro de Funcionário</h1><p>Cadastre um novo funcionário no sistema</p></div>
            <div class="form-container">
                <form id="form-cadastro-funcionario">
                    <div class="form-grid">
                        <div class="form-group"><label>Nome Completo</label><input type="text" name="nome" required></div>
                        <div class="form-group"><label>CPF</label><input type="text" name="cpf" placeholder="000.000.000-00" required></div>
                        <div class="form-group"><label>Email</label><input type="email" name="email" required></div>
                        <div class="form-group"><label>Telefone</label><input type="tel" name="telefone" required></div>
                        <div class="form-group"><label>Cargo</label><select name="cargo" required><option value="">Selecione o cargo</option><option value="Caixa">Caixa</option><option value="Gerente">Gerente</option><option value="Supervisor">Supervisor</option></select></div>
                        <div class="form-group"><label>Agência</label><select name="agencia" required><option value="">Selecione a agência</option><option value="001">001 - Centro</option><option value="002">002 - Norte</option><option value="003">003 - Sul</option></select></div>
                        <div class="form-group"><label>Data de Nascimento</label><input type="date" name="dataNascimento" required></div>
                        <div class="form-group"><label>Senha Inicial</label><input type="password" name="senha" required></div>
                    </div><br>
                    <button type="submit" class="btn-primary">Cadastrar Funcionário</button>
                </form>
            </div>
        </section>

        <!-- Alterar Funcionário -->
        <section id="alterar-funcionario" class="section">
            <div class="content-header"><h1>Consultar / Alterar Funcionários</h1><p>Busque, edite ou remova dados de funcionários</p></div>
            <div class="form-container">
                <div class="form-group">
                    <label>Buscar Funcionário (por nome ou CPF)</label>
                    <input type="text" id="search-funcionario" placeholder="Digite para buscar...">
                </div>
            </div>
            <div class="table-container">
                <h3>Funcionários Cadastrados</h3>
                <table id="tabela-funcionarios">
                    <thead><tr><th>ID</th><th>Nome</th><th>CPF</th><th>Cargo</th><th>Agência</th><th>Ações</th></tr></thead>
                    <tbody><!-- Dados inseridos via JS --></tbody>
                </table>
            </div>
        </section>

        <!-- Cadastro de Cliente -->
        <section id="cadastro-cliente" class="section">
            <div class="content-header"><h1>Cadastro de Cliente</h1><p>Cadastre um novo cliente no sistema</p></div>
            <div class="form-container">
                <form id="form-cadastro-cliente">
                    <div class="form-grid">
                        <div class="form-group"><label>Nome:</label><input type="text" name="nome" required></div>
                        <div class="form-group"><label>CPF:</label><input type="text" name="cpf" placeholder="000.000.000-00" required></div>
                        <div class="form-group"><label>Data de Nascimento:</label><input type="date" name="dataNascimento" required></div>
                        <div class="form-group"><label>Senha:</label><input type="password" name="senha" required></div>
                        <div class="form-group"><label>Telefone:</label><input type="text" name="telefone" required></div>
                        <div class="form-group"><label>CEP:</label><input type="text" name="cep" required></div>
                        <div class="form-group"><label>Rua:</label><input type="text" name="rua" required></div>
                        <div class="form-group"><label>Número:</label><input type="text" name="numeroCasa" required></div>
                        <div class="form-group"><label>Bairro:</label><input type="text" name="bairro" required></div>
                        <div class="form-group"><label>Cidade:</label><input type="text" name="cidade" required></div>
                        <div class="form-group"><label>Estado:</label><input type="text" name="estado" required></div>
                        <div class="form-group"><label>Complemento:</label><input type="text" name="complemento"></div>
                    </div><br>
                    <button type="submit" class="btn-primary">Cadastrar Cliente</button>
                </form>
            </div>
        </section>
        
        <!-- Abertura de Conta -->
        <section id="abertura-conta" class="section">
            <div class="content-header"><h1>Abertura de Conta</h1><p>Abra uma nova conta para um cliente existente</p></div>
            <div class="form-container">
                <form id="form-abertura-conta">
                    <div class="form-grid">
                         <div class="form-group">
                            <label>Cliente (selecione pelo CPF)</label>
                            <input list="clientes-list-abertura" name="clienteCpf" id="clienteCpf-abertura" required autocomplete="off">
                            <datalist id="clientes-list-abertura"><!-- Options inseridas via JS --></datalist>
                        </div>
                        <div class="form-group"><label>Tipo de Conta</label><select name="tipoConta" required><option value="">Selecione o tipo</option><option value="CP">Conta Poupança</option><option value="CC">Conta Corrente</option></select></div>
                        <div class="form-group"><label>Depósito Inicial (R$)</label><input type="number" name="depositoInicial" step="0.01" min="0" required></div>
                        <div class="form-group"><label>Senha da Conta (4 dígitos)</label><input type="password" name="senhaConta" required pattern="\d{4}" title="A senha deve ter 4 dígitos numéricos."></div>
                    </div><br>
                    <button type="submit" class="btn-primary">Abrir Conta</button>
                </form>
            </div>
        </section>
        
        <!-- Geração de Cartão -->
        <section id="geracao-cartao" class="section">
            <div class="content-header"><h1>Geração de Cartão</h1><p>Gere cartões para contas de clientes existentes</p></div>
            <div class="card-generator-container">
                <div class="card-visual-section">
                    <div class="credit-card" id="card-generator-visual">
                        <div class="card-inner">
                            <div class="card-face card-front platinum" id="card-generator-face">
                                <div class="card-header">
                                    <span class="bank-name">CALLISTO BANK</span>
                                    <span class="card-type" id="card-generator-type-display">PLATINUM</span>
                                </div>
                                <div class="chip"></div>
                                <div class="card-number" id="card-generator-number-display">0000 0000 0000 0000</div>
                                <div class="card-info-flex">
                                    <div class="card-field">
                                        <div class="card-label">NOME</div>
                                        <div class="card-value" id="card-generator-name-display">NOME DO CLIENTE</div>
                                    </div>
                                    <div class="card-field">
                                        <div class="card-label">VALIDADE</div>
                                        <div class="card-value" id="card-generator-expiry-display">MM/AA</div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="form-and-advantages-section">
                    <div class="form-container">
                        <form id="form-geracao-cartao">
                            <div class="form-grid">
                                <div class="form-group">
                                    <label for="card-generator-account">Selecione a Conta do Cliente</label>
                                    <input list="accounts-list-geracao" name="accountNumber" id="card-generator-account" required autocomplete="off" placeholder="Digite o número da conta...">
                                    <datalist id="accounts-list-geracao"></datalist>
                                </div>
                                <div class="form-group">
                                    <label for="card-generator-type">Tipo de Cartão</label>
                                    <select name="cardType" id="card-generator-type" required>
                                        <option value="PLATINUM">Platinum</option>
                                        <option value="GOLD">Gold</option>
                                        <option value="BLACK">Black</option>
                                    </select>
                                </div>
                                <div class="form-group">
                                    <label for="card-generator-limit">Limite de Crédito (R$)</label>
                                    <input type="number" name="limite" id="card-generator-limit" min="0" step="100" placeholder="Ex: 1500" required>
                                </div>
                            </div>
                            <br>
                            <button type="submit" class="btn-primary">Gerar Cartão</button>
                        </form>
                    </div>
                    <div class="card-advantages" id="card-generator-advantages">
                        <!-- Vantagens do cartão aparecem aqui -->
                    </div>
                </div>
            </div>
            <div class="table-container" style="margin-top: 2rem;">
                <h3>Cartões Gerados</h3>
                <table id="tabela-cartoes-gerados">
                    <thead><tr><th>Número do Cartão</th><th>Cliente</th><th>CPF</th><th>Tipo</th><th>Limite (R$)</th><th>Validade</th><th>CVV</th><th>Ações</th></tr></thead>
                    <tbody></tbody>
                </table>
            </div>
        </section>


        <!-- Alterar Cliente -->
        <section id="alterar-cliente" class="section">
            <div class="content-header"><h1>Consultar / Alterar Clientes</h1><p>Busque, edite ou remova dados de clientes</p></div>
            <div class="form-container">
                <div class="form-group">
                    <label>Buscar Cliente (por nome ou CPF)</label>
                    <input type="text" id="search-cliente" placeholder="Digite para buscar...">
                </div>
            </div>
            <div class="table-container">
                <h3>Clientes Cadastrados</h3>
                <table id="tabela-clientes">
                    <thead><tr><th>ID</th><th>Nome</th><th>CPF</th><th>Telefone</th><th>Cidade</th><th>Ações</th></tr></thead>
                    <tbody><!-- Dados inseridos via JS --></tbody>
                </table>
            </div>
        </section>

        <!-- Encerramento de Conta -->
        <section id="encerramento-conta" class="section">
             <div class="content-header"><h1>Encerramento de Conta</h1><p>Encerre permanentemente uma conta de cliente</p></div>
             <div class="form-container">
                <form id="form-encerramento-conta">
                    <div class="form-group">
                        <label for="close-account-number">Selecione a Conta para Encerrar</label>
                        <input list="accounts-list-encerramento" name="accountNumber" id="close-account-number" required autocomplete="off" placeholder="Digite o número da conta...">
                        <datalist id="accounts-list-encerramento"></datalist>
                    </div>
                    <br>
                    <button type="submit" class="btn-danger">Encerrar Conta Permanentemente</button>
                </form>
             </div>
        </section>

        <!-- Transações Cliente -->
        <section id="transacoes-cliente" class="section">
            <div class="content-header"><h1>Transações de Cliente</h1><p>Realize depósitos, saques e transferências</p></div>
            <div class="form-container">
                <div class="form-group">
                    <label for="transaction-account-select">Selecione a Conta</label>
                    <input list="accounts-list-transacoes" name="accountNumber" id="transaction-account-select" required autocomplete="off" placeholder="Digite o número da conta...">
                    <datalist id="accounts-list-transacoes"></datalist>
                </div>
            </div>
            <div id="transaction-section-content" style="display: none;">
                <div class="transaction-container">
                    <div class="transaction-forms">
                        <div class="transaction-tabs">
                            <button class="transaction-tab active" data-tab="deposito">Depósito</button>
                            <button class="transaction-tab" data-tab="saque">Saque</button>
                            <button class="transaction-tab" data-tab="transferencia">Transferência</button>
                        </div>
                        <div id="deposito" class="transaction-panel active">
                            <form id="form-deposito" class="form-container">
                                <h3>Realizar Depósito</h3>
                                <div class="form-group"><label>Valor (R$)</label><input type="number" name="valor" min="0.01" step="0.01" required></div>
                                <button type="submit" class="btn-primary">Confirmar Depósito</button>
                            </form>
                        </div>
                        <div id="saque" class="transaction-panel">
                             <form id="form-saque" class="form-container">
                                <h3>Realizar Saque</h3>
                                <div class="form-group"><label>Valor (R$)</label><input type="number" name="valor" min="0.01" step="0.01" required></div>
                                <button type="submit" class="btn-primary">Confirmar Saque</button>
                            </form>
                        </div>
                        <div id="transferencia" class="transaction-panel">
                             <form id="form-transferencia" class="form-container">
                                <h3>Realizar Transferência</h3>
                                <div class="form-group"><label>Valor (R$)</label><input type="number" name="valor" min="0.01" step="0.01" required></div>
                                <div class="form-group"><label>Conta de Destino</label><input type="text" name="contaDestino" placeholder="00000-0" required></div>
                                <button type="submit" class="btn-primary">Confirmar Transferência</button>
                            </form>
                        </div>
                    </div>
                    <div class="transaction-history">
                        <div class="widget">
                            <h3 id="transaction-history-title">Histórico da Conta</h3>
                            <div class="account-balance-display">
                                <p>Saldo Atual</p>
                                <h3 id="transaction-account-balance">R$ 0,00</h3>
                            </div>
                            <div class="table-container" style="margin-top: 1rem; max-height: 300px; overflow-y: auto;">
                                <table id="tabela-historico-transacoes">
                                    <thead><tr><th>Data</th><th>Tipo</th><th>Valor (R$)</th><th>Descrição</th></tr></thead>
                                    <tbody></tbody>
                                </table>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </section>

        <!-- Análise de Conta -->
        <section id="analise-conta" class="section">
             <div class="content-header"><h1>Análise de Conta</h1><p>Visualize métricas e score de crédito do cliente</p></div>
             <div class="form-container">
                <div class="form-group">
                    <label for="analysis-account-select">Selecione a Conta para Análise</label>
                    <input list="accounts-list-analise" name="accountNumber" id="analysis-account-select" required autocomplete="off" placeholder="Digite o número da conta...">
                    <datalist id="accounts-list-analise"></datalist>
                </div>
            </div>
            <div id="analysis-section-content" class="dashboard-widgets" style="display: none;">
                <div class="widget"><h3>Score de Crédito</h3><div class="data-point" id="analysis-score">---</div><p>Pontuação de 0 a 1000</p></div>
                <div class="widget"><h3>Saldo Atual</h3><div class="data-point" id="analysis-current-balance">R$ 0,00</div><p>Balanço da conta no momento</p></div>
                <div class="widget"><h3>Saldo Médio</h3><div class="data-point" id="analysis-avg-balance">R$ 0,00</div><p>Média do saldo no período</p></div>
                <div class="widget"><h3>Total de Entradas</h3><div class="data-point" id="analysis-total-credit">R$ 0,00</div><p>Soma de depósitos e transferências recebidas</p></div>
                <div class="widget"><h3>Total de Saídas</h3><div class="data-point" id="analysis-total-debit">R$ 0,00</div><p>Soma de saques e transferências enviadas</p></div>
                <div class="widget"><h3>Antiguidade da Conta</h3><div class="data-point" id="analysis-account-age">---</div><p>Dias desde a abertura da conta</p></div>
            </div>
        </section>

        <!-- Relatórios -->
        <section id="relatorios" class="section">
            <div class="content-header"><h1>Relatórios</h1><p>Consulte e exporte informações consolidadas do sistema.</p></div>
             <div class="form-container" style="text-align: right;">
                 <button id="export-pdf-btn" class="btn-primary">Exportar PDF</button>
             </div>
             <div class="table-container">
                <h3>Relatório de Contas de Clientes</h3>
                <table id="tabela-relatorio-contas">
                    <thead><tr><th>Número da Conta</th><th>Agência</th><th>Tipo</th><th>Saldo (R$)</th><th>Nome do Cliente</th><th>CPF do Cliente</th></tr></thead>
                    <tbody><!-- Dados inseridos via JS --></tbody>
                </table>
            </div>
        </section>

        <!-- Ferramentas Internas -->
        <section id="ferramentas" class="section">
            <div class="content-header"><h1>Ferramentas Internas</h1><p>Opções de manutenção do sistema.</p></div>
            <div class="form-container">
                <button id="backup-btn" class="btn-primary">Fazer Backup dos Dados</button>
                <p style="margin-top: 1rem; color: #595348;">Baixa um arquivo JSON com todos os dados de clientes, contas, etc.</p>
            </div>
        </section>
        
        <!-- Configurações -->
        <section id="configuracoes" class="section">
            <div class="content-header"><h1>Configurações</h1><p>Opções gerais do sistema.</p></div>
             <div class="form-container">
                <button id="clear-data-btn" class="btn-danger">Limpar Todos os Dados</button>
                 <p style="margin-top: 1rem; color: #595348;">Atenção: Esta ação é irreversível e irá apagar todos os clientes, contas e funcionários do sistema, restaurando os dados iniciais.</p>
            </div>
        </section>
    </main>
</div>

<!-- Modal de Edição -->
<div class="modal-overlay" id="edit-modal">
    <div class="modal-content">
        <button class="modal-close-btn" id="modal-close-btn">&times;</button>
        <h2 id="modal-title">Editar Informações</h2>
        <form id="edit-form" style="margin-top: 1.5rem;"></form>
    </div>
</div>

<footer class="app-footer">
    &copy; <span id="currentYear"></span> Callisto Bank - Todos os direitos reservados.
</footer>

<script>
document.addEventListener('DOMContentLoaded', function() {
    // --- SIMULAÇÃO DE BANCO DE DADOS (LocalStorage) ---
    const db = {
        funcionarios: [],
        clientes: [],
        contas: [],
        cartoes: [],
        transacoes: []
    };

    function loadDatabase() {
        const storedDb = JSON.parse(localStorage.getItem('callistoBankDb'));
        if (storedDb && storedDb.funcionarios && storedDb.funcionarios.length > 0) {
            Object.assign(db, storedDb);
            // Garante que todos os arrays existam
            db.transacoes = db.transacoes || [];
            db.cartoes = db.cartoes || [];
        } else {
            // Dados iniciais para demonstração
            db.funcionarios = [{ id: 1, nome: 'Admin Geral', cpf: '000.000.000-00', email: 'admin@callisto.com', cargo: 'Gerente', agencia: '001', dataNascimento: '1980-05-10', senha: '123' }];
            db.clientes = [{ id: 1, nome: 'João da Silva', cpf: '111.111.111-11', dataNascimento: '1990-01-15', telefone: '(11) 99999-9999', cep: '01000-000', rua: 'Av. Principal', numeroCasa: '123', bairro: 'Centro', cidade: 'São Paulo', estado: 'SP', complemento: 'Apto 101', senha: 'abc' }];
            db.contas = [{ id: 1, numero: '12345-6', agencia: '001', tipoConta: 'CC', saldo: 2500.75, id_cliente: 1, senhaConta: '1234', dataAbertura: new Date().toISOString() }];
            db.cartoes = [];
            db.transacoes = [];
            saveDatabase();
        }
    }
    
    function saveDatabase() {
        localStorage.setItem('callistoBankDb', JSON.stringify(db));
    }

    const generateId = (array) => array.length > 0 ? Math.max(...array.map(item => item.id)) + 1 : 1;

    // --- LÓGICA DE NOTIFICAÇÃO ---
    const notificationArea = document.getElementById('notification-area');
    function showNotification(message, type = 'success') {
        const alertDiv = document.createElement('div');
        alertDiv.className = `alert alert-${type}`;
        alertDiv.textContent = message;
        notificationArea.appendChild(alertDiv);
        
        setTimeout(() => alertDiv.classList.add('show'), 10);
        setTimeout(() => {
            alertDiv.classList.remove('show');
            alertDiv.addEventListener('transitionend', () => alertDiv.remove());
        }, 5000);
    }

    // --- FUNÇÕES DE RENDERIZAÇÃO E ATUALIZAÇÃO ---
    function renderDashboard() {
        document.getElementById('db-total-contas').textContent = db.contas.length;
        document.getElementById('db-total-clientes').textContent = db.clientes.length;
        document.getElementById('db-total-funcionarios').textContent = db.funcionarios.length;
        const volumeTotal = db.contas.reduce((sum, conta) => sum + conta.saldo, 0);
        document.getElementById('db-volume-total').textContent = `R$ ${volumeTotal.toLocaleString('pt-BR', { minimumFractionDigits: 2, maximumFractionDigits: 2 })}`;
    }

    function renderEmployeeTable(filter = '') {
        const tbody = document.querySelector("#tabela-funcionarios tbody");
        tbody.innerHTML = '';
        const lowerCaseFilter = filter.toLowerCase();
        
        const filteredEmployees = db.funcionarios.filter(func => 
            func.nome.toLowerCase().includes(lowerCaseFilter) || 
            func.cpf.includes(filter)
        );

        filteredEmployees.forEach(func => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td>${func.id}</td>
                <td>${func.nome}</td>
                <td>${func.cpf}</td>
                <td>${func.cargo}</td>
                <td>${func.agencia}</td>
                <td>
                    <button class="btn-edit" data-id="${func.id}" data-type="funcionario">Editar</button>
                    <button class="btn-danger" data-id="${func.id}" data-type="funcionario">Excluir</button>
                </td>
            `;
            tbody.appendChild(tr);
        });
    }

    function renderClientTable(filter = '') {
        const tbody = document.querySelector("#tabela-clientes tbody");
        tbody.innerHTML = '';
        const lowerCaseFilter = filter.toLowerCase();
        
        const filteredClients = db.clientes.filter(cli => 
            cli.nome.toLowerCase().includes(lowerCaseFilter) || 
            cli.cpf.includes(filter)
        );

        filteredClients.forEach(cli => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td>${cli.id}</td>
                <td>${cli.nome}</td>
                <td>${cli.cpf}</td>
                <td>${cli.telefone}</td>
                <td>${cli.cidade}</td>
                <td>
                    <button class="btn-edit" data-id="${cli.id}" data-type="cliente">Editar</button>
                    <button class="btn-danger" data-id="${cli.id}" data-type="cliente">Inativar</button>
                </td>
            `;
            tbody.appendChild(tr);
        });
    }
    
    document.getElementById('search-funcionario').addEventListener('input', (e) => renderEmployeeTable(e.target.value));
    document.getElementById('search-cliente').addEventListener('input', (e) => renderClientTable(e.target.value));

    function renderClientDatalist(datalistId) {
        const datalist = document.getElementById(datalistId);
        if (!datalist) return;
        datalist.innerHTML = '';
        db.clientes.forEach(cli => {
            const option = document.createElement('option');
            option.value = cli.cpf;
            option.textContent = cli.nome;
            datalist.appendChild(option);
        });
    }
    
    function renderAccountReportTable() {
        const tbody = document.querySelector("#tabela-relatorio-contas tbody");
        if (!tbody) return;
        tbody.innerHTML = '';
        db.contas.forEach(conta => {
            const cliente = db.clientes.find(c => c.id === conta.id_cliente) || { nome: 'N/A', cpf: 'N/A' };
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td>${conta.numero}</td>
                <td>${conta.agencia}</td>
                <td>${conta.tipoConta === 'CC' ? 'Corrente' : 'Poupança'}</td>
                <td>${conta.saldo.toLocaleString('pt-BR', { minimumFractionDigits: 2, maximumFractionDigits: 2 })}</td>
                <td>${cliente.nome}</td>
                <td>${cliente.cpf}</td>
            `;
            tbody.appendChild(tr);
        });
    }

    // --- LÓGICA DE NAVEGAÇÃO DO MENU ---
    const menuToggles = document.querySelectorAll('.menu-toggle');
    const menuItems = document.querySelectorAll('.menu-item');
    const sections = document.querySelectorAll('.section');

    menuToggles.forEach(toggle => {
        toggle.addEventListener('click', e => {
            e.preventDefault();
            const submenu = document.getElementById(toggle.dataset.submenu);
            document.querySelectorAll('.submenu.active').forEach(activeSubmenu => {
                if (activeSubmenu !== submenu) activeSubmenu.classList.remove('active');
            });
            submenu.classList.toggle('active');
        });
    });

    menuItems.forEach(item => {
        item.addEventListener('click', e => {
            e.preventDefault();
            const sectionId = item.dataset.section;
            if (!sectionId) return;

            menuItems.forEach(i => i.classList.remove('active'));
            item.classList.add('active');
            if (item.closest('.submenu')) {
                item.closest('.submenu').previousElementSibling.classList.add('active');
            }

            sections.forEach(section => section.classList.remove('active'));
            const targetSection = document.getElementById(sectionId);
            if(targetSection) {
                targetSection.classList.add('active');
            }

            // Atualiza os dados da seção ao abri-la
            switch(sectionId) {
                case 'dashboard': renderDashboard(); break;
                case 'alterar-funcionario': renderEmployeeTable(); break;
                case 'alterar-cliente': renderClientTable(); break;
                case 'abertura-conta': renderClientDatalist('clientes-list-abertura'); break;
                case 'relatorios': renderAccountReportTable(); break;
                case 'geracao-cartao': cardGenerator.init(); break;
                case 'encerramento-conta': accountCloser.init(); break;
                case 'transacoes-cliente': transactionHandler.init(); break;
                case 'analise-conta': analysisHandler.init(); break;
            }
        });
    });

    // --- MODAL ---
    const modal = document.getElementById('edit-modal');
    const modalForm = document.getElementById('edit-form');
    const modalTitle = document.getElementById('modal-title');
    document.getElementById('modal-close-btn').addEventListener('click', () => modal.classList.remove('show'));
    modal.addEventListener('click', (e) => {
        if (e.target === modal) {
            modal.classList.remove('show');
        }
    });

    function openEditModal(type, id) {
        modalForm.innerHTML = ''; // Limpa o formulário
        const isEmployee = type === 'funcionario';
        const data = isEmployee ? db.funcionarios.find(f => f.id === id) : db.clientes.find(c => c.id === id);
        
        if (!data) return;

        modalTitle.textContent = `Editar ${isEmployee ? 'Funcionário' : 'Cliente'}`;
        
        const fields = isEmployee ? 
            ['nome', 'cpf', 'email', 'telefone', 'cargo', 'agencia', 'dataNascimento'] :
            ['nome', 'cpf', 'telefone', 'cep', 'rua', 'numeroCasa', 'bairro', 'cidade', 'estado', 'complemento', 'dataNascimento'];

        let formHtml = '<div class="form-grid">';
        fields.forEach(field => {
            formHtml += `
                <div class="form-group">
                    <label>${field.replace(/([A-Z])/g, ' $1').replace(/^./, str => str.toUpperCase())}</label>
                    <input type="${field === 'dataNascimento' ? 'date' : 'text'}" name="${field}" value="${data[field] || ''}" required>
                </div>
            `;
        });
        formHtml += '</div> <br> <button type="submit" class="btn-primary">Salvar Alterações</button>';
        modalForm.innerHTML = formHtml;
        
        modalForm.onsubmit = (e) => {
            e.preventDefault();
            const formData = new FormData(modalForm);
            const updatedData = Object.fromEntries(formData.entries());
            
            if (isEmployee) {
                const index = db.funcionarios.findIndex(f => f.id === id);
                db.funcionarios[index] = { ...db.funcionarios[index], ...updatedData };
                renderEmployeeTable();
            } else {
                const index = db.clientes.findIndex(c => c.id === id);
                db.clientes[index] = { ...db.clientes[index], ...updatedData };
                renderClientTable();
            }
            
            saveDatabase();
            showNotification(`${isEmployee ? 'Funcionário' : 'Cliente'} atualizado com sucesso!`);
            modal.classList.remove('show');
        };

        modal.classList.add('show');
    }

    // --- HANDLERS DE FORMULÁRIO CADASTRO/ABERTURA ---
    document.getElementById('form-cadastro-funcionario').addEventListener('submit', e => {
        e.preventDefault();
        const formData = new FormData(e.target);
        const funcionario = Object.fromEntries(formData.entries());
        if (db.funcionarios.some(f => f.cpf === funcionario.cpf)) {
            showNotification('CPF de funcionário já cadastrado!', 'danger'); return;
        }
        funcionario.id = generateId(db.funcionarios);
        db.funcionarios.push(funcionario);
        saveDatabase();
        showNotification('Funcionário cadastrado com sucesso!');
        e.target.reset();
        renderDashboard();
    });

    document.getElementById('form-cadastro-cliente').addEventListener('submit', e => {
        e.preventDefault();
        const formData = new FormData(e.target);
        const cliente = Object.fromEntries(formData.entries());
        if (db.clientes.some(c => c.cpf === cliente.cpf)) {
            showNotification('CPF de cliente já cadastrado!', 'danger'); return;
        }
        cliente.id = generateId(db.clientes);
        db.clientes.push(cliente);
        saveDatabase();
        showNotification('Cliente cadastrado com sucesso!');
        e.target.reset();
        renderDashboard();
    });
    
    document.getElementById('form-abertura-conta').addEventListener('submit', e => {
        e.preventDefault();
        const formData = new FormData(e.target);
        const dadosConta = Object.fromEntries(formData.entries());
        const cliente = db.clientes.find(c => c.cpf === dadosConta.clienteCpf);
        if(!cliente) {
            showNotification('Cliente não encontrado. Verifique o CPF.', 'danger'); return;
        }
        const novaConta = {
            id: generateId(db.contas),
            numero: `${Math.floor(10000 + Math.random() * 90000)}-${Math.floor(Math.random() * 9)}`,
            agencia: '001', // Agência padrão
            tipoConta: dadosConta.tipoConta,
            saldo: parseFloat(dadosConta.depositoInicial),
            id_cliente: cliente.id,
            senhaConta: dadosConta.senhaConta,
            dataAbertura: new Date().toISOString()
        };
        db.contas.push(novaConta);
        saveDatabase();
        showNotification(`Conta ${novaConta.numero} aberta com sucesso para ${cliente.nome}!`);
        e.target.reset();
        renderDashboard();
    });

    // --- MÓDULOS DE FUNCIONALIDADES ---
    const cardGenerator = (function() {
        const form = document.getElementById('form-geracao-cartao');
        const accountInput = document.getElementById('card-generator-account');
        const datalist = document.getElementById('accounts-list-geracao');
        const cardTypeSelect = document.getElementById('card-generator-type');
        const advantagesDiv = document.getElementById('card-generator-advantages');
        const nameDisplay = document.getElementById('card-generator-name-display');
        const numberDisplay = document.getElementById('card-generator-number-display');
        const expiryDisplay = document.getElementById('card-generator-expiry-display');
        const typeDisplay = document.getElementById('card-generator-type-display');
        const faceDisplay = document.getElementById('card-generator-face');
        const generatedTableBody = document.querySelector('#tabela-cartoes-gerados tbody');

        function formatName(fullName) {
            const parts = fullName.split(' ').filter(part => part.length > 0);
            if (parts.length <= 2) return fullName;
            return `${parts[0]} ${parts[1].charAt(0)}. ${parts.slice(2).join(' ')}`;
        }

        function updateAdvantages(selectedType) {
            let advantagesText = '';
            switch (selectedType) {
                case 'PLATINUM': advantagesText = `<h3>Vantagens Platinum</h3><ul><li>Anuidade: R$19,90/mês</li><li>1 ponto a cada R$5</li></ul>`; break;
                case 'GOLD': advantagesText = `<h3>Vantagens Gold</h3><ul><li>Anuidade: R$9,90/mês</li><li>1 ponto a cada R$3</li><li>Seguro Viagem</li></ul>`; break;
                case 'BLACK': advantagesText = `<h3>Vantagens Black</h3><ul><li>Anuidade Zero</li><li>Cashback de 2%</li><li>Acesso a Salas VIP</li></ul>`; break;
            }
            advantagesDiv.innerHTML = advantagesText;
        }

        function updateCardVisual(type) {
            faceDisplay.className = 'card-face card-front ' + type.toLowerCase();
            typeDisplay.textContent = type;
            updateAdvantages(type);
        }

        function renderGeneratedCards() {
            generatedTableBody.innerHTML = '';
            db.cartoes.forEach(card => {
                const cliente = db.clientes.find(c => c.id === card.id_cliente) || {};
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>${card.number}</td>
                    <td>${cliente.nome || 'N/A'}</td>
                    <td>${cliente.cpf || 'N/A'}</td>
                    <td>${card.type}</td>
                    <td>R$ ${card.limite.toLocaleString('pt-BR', { minimumFractionDigits: 2 })}</td>
                    <td>${card.expiry}</td>
                    <td>${card.cvv}</td>
                    <td><button class="btn-danger" data-id="${card.id}" data-type="cartao">Excluir</button></td>
                `;
                generatedTableBody.appendChild(tr);
            });
        }
        
        function populateAccounts() {
            datalist.innerHTML = '';
            db.contas.forEach(conta => {
                const cliente = db.clientes.find(c => c.id === conta.id_cliente);
                if(cliente) {
                    const option = document.createElement('option');
                    option.value = conta.numero;
                    option.label = `${cliente.nome} (${cliente.cpf})`;
                    datalist.appendChild(option);
                }
            });
        }
        
        accountInput.addEventListener('input', (e) => {
            const selectedConta = db.contas.find(c => c.numero === e.target.value);
            if (selectedConta) {
                const cliente = db.clientes.find(c => c.id === selectedConta.id_cliente);
                if (cliente) {
                    nameDisplay.textContent = formatName(cliente.nome.toUpperCase());
                }
            } else {
                 nameDisplay.textContent = 'NOME DO CLIENTE';
            }
        });

        cardTypeSelect.addEventListener('change', (e) => updateCardVisual(e.target.value));

        form.addEventListener('submit', e => {
            e.preventDefault();
            const formData = new FormData(form);
            const data = Object.fromEntries(formData.entries());
            
            const conta = db.contas.find(c => c.numero === data.accountNumber);
            if(!conta) {
                showNotification('Conta não encontrada.', 'danger'); return;
            }

            const cardExists = db.cartoes.some(card => card.id_conta === conta.id && card.type === data.cardType);
            if (cardExists) {
                 showNotification(`Um cartão ${data.cardType} já existe para esta conta.`, 'danger'); return;
            }

            const novoCartao = {
                id: generateId(db.cartoes),
                id_conta: conta.id,
                id_cliente: conta.id_cliente,
                number: Array(4).fill(0).map(() => Math.floor(1000 + Math.random() * 9000)).join(' '),
                limite: parseFloat(data.limite),
                expiry: `${String(new Date().getMonth() + 1).padStart(2, '0')}/${String(new Date().getFullYear() + 5).slice(-2)}`,
                cvv: String(Math.floor(100 + Math.random() * 900)),
                type: data.cardType,
            };

            db.cartoes.push(novoCartao);
            saveDatabase();
            showNotification(`Cartão ${novoCartao.type} gerado com sucesso!`);
            renderGeneratedCards();
            form.reset();
            nameDisplay.textContent = 'NOME DO CLIENTE';
            numberDisplay.textContent = '0000 0000 0000 0000';
            expiryDisplay.textContent = 'MM/AA';
        });

        return {
            init() {
                populateAccounts();
                renderGeneratedCards();
                updateCardVisual(cardTypeSelect.value);
            }
        };
    })();

    const accountCloser = (function() {
        const form = document.getElementById('form-encerramento-conta');
        const accountInput = document.getElementById('close-account-number');
        const datalist = document.getElementById('accounts-list-encerramento');
        
        function populateAccounts() {
            datalist.innerHTML = '';
            db.contas.forEach(conta => {
                const cliente = db.clientes.find(c => c.id === conta.id_cliente);
                if(cliente) {
                    const option = document.createElement('option');
                    option.value = conta.numero;
                    option.label = `${cliente.nome} (Saldo: R$ ${conta.saldo.toFixed(2)})`;
                    datalist.appendChild(option);
                }
            });
        }
        
        form.addEventListener('submit', e => {
            e.preventDefault();
            const accountNumber = accountInput.value;
            const accountIndex = db.contas.findIndex(acc => acc.numero === accountNumber);
            if(accountIndex === -1) {
                showNotification("Conta não encontrada.", "danger");
                return;
            }
            const account = db.contas[accountIndex];
            
            if (confirm(`Tem certeza que deseja encerrar a conta ${account.numero}? Esta ação removerá a conta e todos os cartões associados.`)) {
                // Remover conta
                db.contas.splice(accountIndex, 1);
                // Remover cartões associados
                db.cartoes = db.cartoes.filter(card => card.id_conta !== account.id);
                // Remover transações associadas
                db.transacoes = db.transacoes.filter(t => t.accountId !== account.id);

                saveDatabase();
                showNotification("Conta encerrada com sucesso.");
                form.reset();
                populateAccounts();
                renderDashboard();
            }
        });
        
        return { init: populateAccounts };
    })();

    const transactionHandler = (function() {
        let currentAccount = null;
        const accountSelect = document.getElementById('transaction-account-select');
        const datalist = document.getElementById('accounts-list-transacoes');
        const contentDiv = document.getElementById('transaction-section-content');
        const balanceDisplay = document.getElementById('transaction-account-balance');
        const historyTableBody = document.querySelector('#tabela-historico-transacoes tbody');
        const tabs = document.querySelectorAll('.transaction-tab');
        const panels = document.querySelectorAll('.transaction-panel');
        
        function populateAccounts() {
            datalist.innerHTML = '';
            db.contas.forEach(conta => {
                const cliente = db.clientes.find(c => c.id === conta.id_cliente);
                if(cliente) {
                    const option = document.createElement('option');
                    option.value = conta.numero;
                    option.label = `${cliente.nome} (${cliente.cpf})`;
                    datalist.appendChild(option);
                }
            });
        }

        function updateDisplay() {
            if(!currentAccount) {
                contentDiv.style.display = 'none';
                return;
            }
            balanceDisplay.textContent = `R$ ${currentAccount.saldo.toLocaleString('pt-BR', { minimumFractionDigits: 2 })}`;
            historyTableBody.innerHTML = '';
            const accountTransactions = db.transacoes.filter(t => t.accountId === currentAccount.id).reverse();
            accountTransactions.forEach(t => {
                const tr = document.createElement('tr');
                tr.innerHTML = `
                    <td>${new Date(t.date).toLocaleString('pt-BR')}</td>
                    <td>${t.type}</td>
                    <td style="color: ${t.amount < 0 ? '#d32f2f' : '#28a745'}">${t.amount.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })}</td>
                    <td>${t.description}</td>
                `;
                historyTableBody.appendChild(tr);
            });
            contentDiv.style.display = 'block';
        }

        accountSelect.addEventListener('change', () => {
            currentAccount = db.contas.find(acc => acc.numero === accountSelect.value) || null;
            updateDisplay();
        });

        tabs.forEach(tab => {
            tab.addEventListener('click', () => {
                tabs.forEach(t => t.classList.remove('active'));
                panels.forEach(p => p.classList.remove('active'));
                tab.classList.add('active');
                document.getElementById(tab.dataset.tab).classList.add('active');
            });
        });

        document.getElementById('form-deposito').addEventListener('submit', e => {
            e.preventDefault();
            const valor = parseFloat(e.target.valor.value);
            currentAccount.saldo += valor;
            db.transacoes.push({id: generateId(db.transacoes), accountId: currentAccount.id, type: 'Depósito', amount: valor, date: new Date().toISOString(), description: 'Depósito em caixa'});
            saveDatabase();
            showNotification('Depósito realizado com sucesso!');
            e.target.reset();
            updateDisplay();
        });

        document.getElementById('form-saque').addEventListener('submit', e => {
            e.preventDefault();
            const valor = parseFloat(e.target.valor.value);
            if(valor > currentAccount.saldo) {
                showNotification('Saldo insuficiente.', 'danger'); return;
            }
            currentAccount.saldo -= valor;
            db.transacoes.push({id: generateId(db.transacoes), accountId: currentAccount.id, type: 'Saque', amount: -valor, date: new Date().toISOString(), description: 'Retirada em caixa'});
            saveDatabase();
            showNotification('Saque realizado com sucesso!');
            e.target.reset();
            updateDisplay();
        });
        
        document.getElementById('form-transferencia').addEventListener('submit', e => {
            e.preventDefault();
            const valor = parseFloat(e.target.valor.value);
            const contaDestinoNum = e.target.contaDestino.value;
            const contaDestino = db.contas.find(acc => acc.numero === contaDestinoNum);

            if(!contaDestino) { showNotification('Conta de destino não encontrada.', 'danger'); return; }
            if(valor > currentAccount.saldo) { showNotification('Saldo insuficiente.', 'danger'); return; }
            if(currentAccount.id === contaDestino.id) { showNotification('Não é possível transferir para a mesma conta.', 'danger'); return; }

            currentAccount.saldo -= valor;
            contaDestino.saldo += valor;

            db.transacoes.push({id: generateId(db.transacoes), accountId: currentAccount.id, type: 'Transferência Enviada', amount: -valor, date: new Date().toISOString(), description: `Para conta ${contaDestino.numero}`});
            db.transacoes.push({id: generateId(db.transacoes), accountId: contaDestino.id, type: 'Transferência Recebida', amount: valor, date: new Date().toISOString(), description: `De conta ${currentAccount.numero}`});
            
            saveDatabase();
            showNotification('Transferência realizada com sucesso!');
            e.target.reset();
            updateDisplay();
        });

        return { init: populateAccounts };
    })();

    const analysisHandler = (function() {
        const accountSelect = document.getElementById('analysis-account-select');
        const datalist = document.getElementById('accounts-list-analise');
        const contentDiv = document.getElementById('analysis-section-content');

        function populateAccounts() {
             datalist.innerHTML = '';
             db.contas.forEach(conta => {
                const cliente = db.clientes.find(c => c.id === conta.id_cliente);
                if(cliente) {
                    const option = document.createElement('option');
                    option.value = conta.numero;
                    option.label = `${cliente.nome} (${cliente.cpf})`;
                    datalist.appendChild(option);
                }
            });
        }

        accountSelect.addEventListener('change', () => {
            const currentAccount = db.contas.find(acc => acc.numero === accountSelect.value);
            if(currentAccount) {
                const transactions = db.transacoes.filter(t => t.accountId === currentAccount.id);
                
                // Saldo Atual
                document.getElementById('analysis-current-balance').textContent = `R$ ${currentAccount.saldo.toLocaleString('pt-BR', {minimumFractionDigits: 2})}`;
                
                // Entradas e Saídas
                const totalCredit = transactions.filter(t => t.amount > 0).reduce((sum, t) => sum + t.amount, 0);
                const totalDebit = transactions.filter(t => t.amount < 0).reduce((sum, t) => sum + t.amount, 0);
                document.getElementById('analysis-total-credit').textContent = `R$ ${totalCredit.toLocaleString('pt-BR', {minimumFractionDigits: 2})}`;
                document.getElementById('analysis-total-debit').textContent = `R$ ${Math.abs(totalDebit).toLocaleString('pt-BR', {minimumFractionDigits: 2})}`;

                // Saldo Médio (simplificado)
                const avgBalance = transactions.length > 0 ? (transactions.reduce((sum, t, i, arr) => {
                    const balanceAtTime = currentAccount.saldo - arr.slice(i + 1).reduce((s, c) => s + c.amount, 0);
                    return sum + balanceAtTime;
                }, 0) / transactions.length) : currentAccount.saldo;
                document.getElementById('analysis-avg-balance').textContent = `R$ ${avgBalance.toLocaleString('pt-BR', {minimumFractionDigits: 2})}`;

                // Antiguidade da Conta
                const ageInDays = Math.floor((new Date() - new Date(currentAccount.dataAbertura)) / (1000 * 60 * 60 * 24));
                document.getElementById('analysis-account-age').textContent = `${ageInDays} dias`;

                // Score de Crédito (simulado)
                let score = 300;
                if(currentAccount.saldo > 1000) score += 100;
                if(currentAccount.saldo > 5000) score += 150;
                if(totalCredit > 2000) score += 100;
                if(ageInDays > 90) score += 50;
                if(ageInDays > 365) score += 100;
                if (currentAccount.saldo / (Math.abs(totalDebit) || 1) > 2) score += 100;
                document.getElementById('analysis-score').textContent = Math.min(score, 950);

                contentDiv.style.display = 'grid';
            } else {
                contentDiv.style.display = 'none';
            }
        });
        return { init: populateAccounts };
    })();


    // --- DELEGAÇÃO DE EVENTOS PARA AÇÕES (EDITAR/EXCLUIR) ---
    document.getElementById('main-content').addEventListener('click', e => {
        const target = e.target;
        if (target.matches('button.btn-danger')) {
            const id = parseInt(target.dataset.id);
            const type = target.dataset.type;
            const typeName = type === 'funcionario' ? 'funcionário' : (type === 'cliente' ? 'cliente' : 'cartão');

            if (confirm(`Tem certeza que deseja excluir este ${typeName} com ID ${id}? Ação irreversível.`)) {
                if (type === 'funcionario') {
                    db.funcionarios = db.funcionarios.filter(f => f.id !== id);
                    renderEmployeeTable();
                } else if (type === 'cliente') {
                    const clientAccounts = db.contas.filter(acc => acc.id_cliente === id).map(acc => acc.id);
                    db.clientes = db.clientes.filter(c => c.id !== id);
                    db.contas = db.contas.filter(acc => acc.id_cliente !== id);
                    db.cartoes = db.cartoes.filter(card => !clientAccounts.includes(card.id_conta));
                    renderClientTable();
                } else if (type === 'cartao') {
                     db.cartoes = db.cartoes.filter(c => c.id !== id);
                     cardGenerator.init();
                }
                
                saveDatabase();
                showNotification(`${typeName.charAt(0).toUpperCase() + typeName.slice(1)} excluído com sucesso.`);
                renderDashboard();
            }
        } else if (target.matches('button.btn-edit')) {
            const id = parseInt(target.dataset.id);
            const type = target.dataset.type;
            openEditModal(type, id);
        }
    });
    
    // --- EXPORTAÇÃO E FERRAMENTAS ---
    document.getElementById('export-pdf-btn').addEventListener('click', () => {
        const { jsPDF } = window.jspdf;
        const doc = new jsPDF();
        
        doc.text("Relatório de Contas - Callisto Bank", 14, 16);
        doc.setFontSize(10);
        doc.text(`Gerado em: ${new Date().toLocaleString('pt-BR')}`, 14, 22);

        const head = [['#Conta', 'Ag.', 'Tipo', 'Cliente', 'CPF', 'Saldo (R$)']];
        const body = db.contas.map(conta => {
            const cliente = db.clientes.find(c => c.id === conta.id_cliente) || {};
            return [
                conta.numero,
                conta.agencia,
                conta.tipoConta,
                cliente.nome || 'N/A',
                cliente.cpf || 'N/A',
                conta.saldo.toLocaleString('pt-BR')
            ];
        });
        
        doc.autoTable({
            startY: 30,
            head: head,
            body: body,
            theme: 'striped',
            headStyles: { fillColor: [38, 10, 4] }
        });
        
        doc.save('relatorio_contas_callisto.pdf');
    });

    document.getElementById('backup-btn').addEventListener('click', () => {
        const dataStr = "data:text/json;charset=utf-8," + encodeURIComponent(JSON.stringify(db, null, 2));
        const downloadAnchorNode = document.createElement('a');
        downloadAnchorNode.setAttribute("href", dataStr);
        downloadAnchorNode.setAttribute("download", `callisto_backup_${new Date().toISOString()}.json`);
        document.body.appendChild(downloadAnchorNode);
        downloadAnchorNode.click();
        downloadAnchorNode.remove();
        showNotification('Backup realizado com sucesso!');
    });

    document.getElementById('clear-data-btn').addEventListener('click', () => {
        if(confirm("ATENÇÃO! Esta ação apagará TODOS os dados do sistema (clientes, contas, etc.) e restaurará os dados de demonstração. Deseja continuar?")) {
            localStorage.removeItem('callistoBankDb');
            loadDatabase();
            renderDashboard();
            showNotification('Sistema reiniciado com sucesso!', 'success');
        }
    });

    // --- INICIALIZAÇÃO DA APLICAÇÃO ---
    function initializeApp() {
        loadDatabase();
        renderDashboard();
        document.getElementById('currentYear').textContent = new Date().getFullYear();
    }

    initializeApp();
});
</script>
</body>
</html>
