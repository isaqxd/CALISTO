<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Callisto Bank - Painel do Funcionário</title>
  <style>
    /* Reset e base */
    * {
      margin: 0;
      padding: 0;
      box-sizing: border-box;
    }

    html, body {
      height: 100%;
      margin: 0;
      padding: 0;
    }

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

    /* Background Layer */
    .background-layer {
      position: fixed;
      top: 0;
      left: 0;
      width: 100%;
      height: 100%;
      transition: all 0.8s cubic-bezier(0.4, 0, 0.2, 1);
      z-index: -1;
    }
    .bg-funcionario {
      background: linear-gradient(135deg,
      rgba(89, 83, 72, 0.08) 0%,
      rgba(38, 10, 4, 0.12) 50%,
      rgba(0, 0, 0, 0.04) 100%),
      #f5f5f2;
      opacity: 1;
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
      z-index: -2;
    }
    @keyframes float {
      0% { transform: translateX(-50px) translateY(-20px); }
      50% { transform: translateX(50px) translateY(20px); }
      100% { transform: translateX(-50px) translateY(-20px); }
    }

    /* Header */
    .header {
      position: fixed;
      top: 0;
      left: 0;
      right: 0;
      padding: 1rem 2rem;
      z-index: 1000;
      background-color: rgba(245, 245, 242, 0.85);
      box-shadow: 0 2px 10px rgba(0,0,0,0.05);
      display: flex;
      justify-content: space-between;
      align-items: center;
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
    .user-info {
      display: flex;
      align-items: center;
      color: #260A04;
    }
    .user-info span {
      margin-right: 1rem;
      font-weight: 500;
    }
    .logout-btn {
      background: linear-gradient(135deg, #595348 0%, #260A04 100%);
      color: white;
      padding: 0.6rem 1.2rem;
      border: none;
      border-radius: 20px;
      text-decoration: none;
      font-size: 0.9rem;
      font-weight: 600;
      transition: all 0.3s ease;
    }
    .logout-btn:hover {
      background: linear-gradient(135deg, #260A04 0%, #000000 100%);
      transform: translateY(-2px);
      box-shadow: 0 4px 15px rgba(38, 10, 4, 0.2);
    }

    /* Layout Principal */
    .app-container {
      flex: 1 0 auto;
      display: flex;
      flex-direction: row;
      min-height: 0;
      padding-top: 80px;
      height: 100vh;
    }

    /* Sidebar (Menu) */
    .sidebar {
      width: 260px;
      background: rgba(235, 233, 230, 0.7);
      padding: 1.5rem 0;
      height: calc(100vh - 80px);
      position: fixed;
      top: 80px;
      left: 0;
      overflow-y: auto;
      box-shadow: 3px 0 15px rgba(0,0,0,0.05);
      z-index: 900;
    }
    .sidebar-header {
      padding: 0 1.5rem 1.5rem 1.5rem;
      text-align: center;
      border-bottom: 1px solid rgba(38, 10, 4, 0.1);
      margin-bottom: 1rem;
    }
    .sidebar-header h3 {
      color: #260A04;
      font-size: 1.3rem;
    }
    .sidebar-nav ul {
      list-style: none;
    }
    .sidebar-nav li a {
      display: flex;
      align-items: center;
      padding: 0.9rem 1.5rem;
      color: #260A04;
      text-decoration: none;
      font-weight: 500;
      font-size: 0.95rem;
      transition: all 0.3s ease;
      border-left: 4px solid transparent;
      cursor: pointer;
    }
    .sidebar-nav li a .menu-icon {
      margin-right: 0.8rem;
      font-size: 1.1rem;
      width: 20px;
      text-align: center;
    }
    .sidebar-nav li a.active,
    .sidebar-nav li a:hover {
      background-color: rgba(89, 83, 72, 0.1);
      color: #000;
      border-left-color: #260A04;
    }
    .sidebar-nav li a:hover {
      transform: translateX(5px);
    }

    /* Submenu */
    .submenu {
      display: none;
      background: rgba(255, 255, 255, 0.3);
      margin-left: 1rem;
      border-left: 2px solid #260A04;
    }
    .submenu.active {
      display: block;
    }
    .submenu li a {
      padding: 0.6rem 1rem;
      font-size: 0.85rem;
      font-weight: 400;
      border-left: none;
    }
    .submenu li a:hover {
      background-color: rgba(89, 83, 72, 0.15);
      transform: translateX(3px);
    }

    /* Conteúdo Principal */
    .main-content {
      flex: 1 1 auto;
      padding: 2rem;
      margin-left: 260px;
      overflow-y: auto;
      min-height: 0;
    }

    .content-header {
      margin-bottom: 2rem;
    }

    .content-header h1 {
      font-size: 2.2rem;
      color: #260A04;
      font-weight: bold;
      margin-bottom: 0.5rem;
    }
    .content-header p {
      font-size: 1.1rem;
      color: #595348;
      line-height: 1.6;
    }

    .dashboard-widgets {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
      gap: 1.5rem;
    }

    .widget {
      background: rgba(255, 255, 255, 0.9);
      padding: 1.5rem;
      border-radius: 15px;
      box-shadow: 0 10px 30px rgba(0,0,0,0.07);
      transition: transform 0.3s ease, box-shadow 0.3s ease;
    }
    .widget:hover {
      transform: translateY(-5px);
      box-shadow: 0 15px 35px rgba(0,0,0,0.1);
    }

    .widget h3 {
      font-size: 1.2rem;
      color: #260A04;
      margin-bottom: 1rem;
      border-bottom: 1px solid rgba(38, 10, 4, 0.1);
      padding-bottom: 0.5rem;
    }

    .widget p {
      font-size: 0.95rem;
      color: #595348;
      line-height: 1.6;
    }
    .widget .data-point {
      font-size: 1.8rem;
      color: #260A04;
      font-weight: bold;
      margin-bottom: 0.5rem;
    }

    /* Formulários */
    .form-container {
      background: rgba(255, 255, 255, 0.9);
      padding: 2rem;
      border-radius: 15px;
      box-shadow: 0 10px 30px rgba(0,0,0,0.07);
      margin-bottom: 2rem;
    }

    .form-grid {
      display: grid;
      grid-template-columns: repeat(auto-fit, minmax(300px, 1fr));
      gap: 1.5rem;
    }

    .form-group {
      display: flex;
      flex-direction: column;
    }

    .form-group label {
      color: #260A04;
      font-weight: 600;
      margin-bottom: 0.5rem;
    }

    .form-group input,
    .form-group select,
    .form-group textarea {
      padding: 0.8rem;
      border: 1px solid #cbbfae;
      border-radius: 8px;
      font-size: 1rem;
      background: #f5f5f2;
      color: #260A04;
      transition: border 0.2s;
    }

    .form-group input:focus,
    .form-group select:focus,
    .form-group textarea:focus {
      border: 2px solid #260A04;
      outline: none;
      background: #fff;
    }

    .btn-primary {
      background: linear-gradient(135deg, #595348 0%, #260A04 100%);
      color: white;
      padding: 0.8rem 2rem;
      border: none;
      border-radius: 25px;
      font-size: 1rem;
      font-weight: 600;
      cursor: pointer;
      transition: all 0.3s ease;
      box-shadow: 0 4px 15px rgba(38, 10, 4, 0.2);
    }

    .btn-primary:hover {
      background: linear-gradient(135deg, #260A04 0%, #000000 100%);
      transform: translateY(-2px);
      box-shadow: 0 6px 20px rgba(38, 10, 4, 0.3);
    }

    .btn-secondary {
      background: #BF7A24;
      color: white;
      padding: 0.6rem 1.5rem;
      border: none;
      border-radius: 20px;
      font-size: 0.9rem;
      font-weight: 600;
      cursor: pointer;
      transition: all 0.3s ease;
      margin-right: 1rem;
    }

    .btn-secondary:hover {
      background: #8C480D;
      transform: translateY(-2px);
    }

    .btn-danger {
      background: #d32f2f;
      color: white;
      padding: 0.6rem 1.5rem;
      border: none;
      border-radius: 20px;
      font-size: 0.9rem;
      font-weight: 600;
      cursor: pointer;
      transition: all 0.3s ease;
    }

    .btn-danger:hover {
      background: #b71c1c;
      transform: translateY(-2px);
    }

    /* Tabelas */
    .table-container {
      background: rgba(255, 255, 255, 0.9);
      border-radius: 15px;
      padding: 1.5rem;
      box-shadow: 0 10px 30px rgba(0,0,0,0.07);
      overflow-x: auto;
    }

    table {
      width: 100%;
      border-collapse: collapse;
      margin-top: 1rem;
    }

    th, td {
      padding: 1rem;
      text-align: left;
      border-bottom: 1px solid #e0e0e0;
    }

    th {
      background-color: #f5f5f2;
      color: #260A04;
      font-weight: 600;
    }

    tr:hover {
      background-color: rgba(89, 83, 72, 0.05);
    }

    /* Footer */
    .app-footer {
      position: fixed;
      right: 0;
      bottom: 0;
      width: auto;
      min-width: 220px;
      border-top-left-radius: 12px;
      background: #222;
      color: #fff;
      text-align: center;
      padding: 1rem 2rem;
      z-index: 2;
      margin-top: 0;
    }

    /* Responsividade */
    @media (max-width: 768px) {
      .sidebar {
        width: 60px;
      }
      .sidebar-header h3,
      .sidebar-nav li a span:not(.menu-icon) {
        display: none;
      }
      .sidebar-nav li a {
        justify-content: center;
        padding: 1rem 0.5rem;
      }
      .sidebar-nav li a .menu-icon {
        margin-right: 0;
      }
      .main-content {
        margin-left: 60px;
        padding: 1.5rem;
      }
      .submenu {
        display: none !important;
      }
    }

    /* Seções ocultas */
    .section {
      display: none;
    }
    .section.active {
      display: block;
    }
  </style>
</head>
<body class="funcionario-mode">
<div class="background-layer bg-funcionario"></div>

<header class="header">
  <div class="logo">
    <img src="../img/image.svg" alt="Callisto Bank" class="logo-image">
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
        <li><a href="#" data-section="dashboard" class="menu-item active"><span class="menu-icon">📊</span><span>Dashboard</span></a></li>

        <li>
          <a href="#" class="menu-toggle" data-submenu="funcionario-submenu">
            <span class="menu-icon">🧑‍💼</span>
            <span>Gerenciar Funcionário</span>
          </a>
          <ul class="submenu" id="funcionario-submenu">
            <li><a href="#" data-section="cadastro-funcionario" class="menu-item">Cadastro de Funcionário</a></li>
            <li><a href="#" data-section="alterar-funcionario" class="menu-item">Alterar Cadastro</a></li>
          </ul>
        </li>

        <li>
          <a href="#" class="menu-toggle" data-submenu="cliente-submenu">
            <span class="menu-icon">👥</span>
            <span>Gerenciar Cliente</span>
          </a>
          <ul class="submenu" id="cliente-submenu">
            <li><a href="#" data-section="cadastro-cliente" class="menu-item">Cadastro de Cliente</a></li>
            <li><a href="#" data-section="abertura-conta" class="menu-item">Abertura de Conta</a></li>
            <li><a href="#" data-section="geracao-cartao" class="menu-item">Geração de Cartão</a></li>
            <li><a href="#" data-section="alterar-cliente" class="menu-item">Alterar Cadastro</a></li>
            <li><a href="#" data-section="encerramento-conta" class="menu-item">Encerramento de Conta</a></li>
            <li><a href="#" data-section="transacoes-cliente" class="menu-item">Transações</a></li>
            <li><a href="#" data-section="analise-conta" class="menu-item">Análise de Conta</a></li>
          </ul>
        </li>

        <li><a href="#" data-section="relatorios" class="menu-item"><span class="menu-icon">📄</span><span>Relatórios</span></a></li>
        <li><a href="#" data-section="ferramentas" class="menu-item"><span class="menu-icon">🔧</span><span>Ferramentas Internas</span></a></li>
        <li><a href="#" data-section="suporte" class="menu-item"><span class="menu-icon">🎧</span><span>Suporte</span></a></li>
        <li><a href="#" data-section="configuracoes" class="menu-item"><span class="menu-icon">⚙️</span><span>Configurações</span></a></li>
      </ul>
    </nav>
  </aside>

  <main class="main-content" id="main-content">
    <!-- Dashboard -->
    <section id="dashboard" class="section active">
      <div class="content-header">
        <h1>Dashboard</h1>
        <p>Visão geral das operações do Callisto Bank</p>
      </div>
      <div class="dashboard-widgets">
        <div class="widget">
          <h3>Contas Ativas</h3>
          <div class="data-point">1,245</div>
          <p>Total de contas ativas no sistema</p>
        </div>
        <div class="widget">
          <h3>Transações Hoje</h3>
          <div class="data-point">89</div>
          <p>Transações realizadas hoje</p>
        </div>
        <div class="widget">
          <h3>Novos Clientes</h3>
          <div class="data-point">12</div>
          <p>Cadastros realizados esta semana</p>
        </div>
        <div class="widget">
          <h3>Volume Financeiro</h3>
          <div class="data-point">R$ 2.4M</div>
          <p>Movimentação do mês atual</p>
        </div>
      </div>
    </section>

    <!-- Cadastro de Funcionário -->
    <section id="cadastro-funcionario" class="section">
      <div class="content-header">
        <h1>Cadastro de Funcionário</h1>
        <p>Cadastre um novo funcionário no sistema</p>
      </div>
      <div class="form-container">
        <form>
          <div class="form-grid">
            <div class="form-group">
              <label>Nome Completo</label>
              <input type="text" name="nome" required>
            </div>
            <div class="form-group">
              <label>CPF</label>
              <input type="text" name="cpf" required>
            </div>
            <div class="form-group">
              <label>Email</label>
              <input type="email" name="email" required>
            </div>
            <div class="form-group">
              <label>Telefone</label>
              <input type="tel" name="telefone" required>
            </div>
            <div class="form-group">
              <label>Cargo</label>
              <select name="cargo" required>
                <option value="">Selecione o cargo</option>
                <option value="caixa">Caixa</option>
                <option value="gerente">Gerente</option>
                <option value="supervisor">Supervisor</option>
              </select>
            </div>
            <div class="form-group">
              <label>Agência</label>
              <select name="agencia" required>
                <option value="">Selecione a agência</option>
                <option value="001">001 - Centro</option>
                <option value="002">002 - Norte</option>
                <option value="003">003 - Sul</option>
              </select>
            </div>
            <div class="form-group">
              <label>Data de Nascimento</label>
              <input type="date" name="data_nascimento" required>
            </div>
            <div class="form-group">
              <label>Senha Inicial</label>
              <input type="password" name="senha" required>
            </div>
          </div>
          <button type="submit" class="btn-primary">Cadastrar Funcionário</button>
        </form>
      </div>
    </section>

    <!-- Alterar Funcionário -->
    <section id="alterar-funcionario" class="section">
      <div class="content-header">
        <h1>Alterar Cadastro de Funcionário</h1>
        <p>Busque e altere dados de funcionários</p>
      </div>
      <div class="form-container">
        <div class="form-group">
          <label>Buscar Funcionário</label>
          <input type="text" placeholder="Digite CPF ou nome do funcionário">
          <button type="button" class="btn-secondary" style="margin-top: 0.5rem;">Buscar</button>
        </div>
      </div>
      <div class="table-container">
        <h3>Funcionários Encontrados</h3>
        <table>
          <thead>
          <tr>
            <th>Código</th>
            <th>Nome</th>
            <th>CPF</th>
            <th>Cargo</th>
            <th>Agência</th>
            <th>Ações</th>
          </tr>
          </thead>
          <tbody>
          <tr>
            <td>001</td>
            <td>Maria Silva</td>
            <td>123.456.789-00</td>
            <td>Caixa</td>
            <td>001</td>
            <td>
              <button class="btn-secondary">Editar</button>
              <button class="btn-danger">Inativar</button>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
    </section>

    <!-- Cadastro de Cliente -->
    <section id="cadastro-cliente" class="section">
      <div class="content-header">
        <h1>Cadastro de Cliente</h1>
        <p>Cadastre um novo cliente no sistema</p>
      </div>
      <div class="form-container">
        <form>
          <div class="form-grid">
            <div class="form-group">
              <label>Nome Completo</label>
              <input type="text" name="nome" required>
            </div>
            <div class="form-group">
              <label>CPF</label>
              <input type="text" name="cpf" required>
            </div>
            <div class="form-group">
              <label>Email</label>
              <input type="email" name="email" required>
            </div>
            <div class="form-group">
              <label>Telefone</label>
              <input type="tel" name="telefone" required>
            </div>
            <div class="form-group">
              <label>Data de Nascimento</label>
              <input type="date" name="data_nascimento" required>
            </div>
            <div class="form-group">
              <label>Agência</label>
              <select name="agencia" required>
                <option value="">Selecione a agência</option>
                <option value="001">001 - Centro</option>
                <option value="002">002 - Norte</option>
                <option value="003">003 - Sul</option>
              </select>
            </div>
            <div class="form-group">
              <label>Endereço</label>
              <input type="text" name="endereco" required>
            </div>
            <div class="form-group">
              <label>CEP</label>
              <input type="text" name="cep" required>
            </div>
            <div class="form-group">
              <label>Cidade</label>
              <input type="text" name="cidade" required>
            </div>
            <div class="form-group">
              <label>Estado</label>
              <select name="estado" required>
                <option value="">Selecione o estado</option>
                <option value="DF">Distrito Federal</option>
                <option value="GO">Goiás</option>
                <option value="MG">Minas Gerais</option>
              </select>
            </div>
          </div>
          <button type="submit" class="btn-primary">Cadastrar Cliente</button>
        </form>
      </div>
    </section>

    <!-- Abertura de Conta -->
    <section id="abertura-conta" class="section">
      <div class="content-header">
        <h1>Abertura de Conta</h1>
        <p>Abra uma nova conta para cliente existente</p>
      </div>
      <div class="form-container">
        <form>
          <div class="form-grid">
            <div class="form-group">
              <label>CPF do Cliente</label>
              <input type="text" name="cpf" required>
              <button type="button" class="btn-secondary" style="margin-top: 0.5rem;">Buscar Cliente</button>
            </div>
            <div class="form-group">
              <label>Tipo de Conta</label>
              <select name="tipo_conta" required>
                <option value="">Selecione o tipo</option>
                <option value="CP">Conta Poupança (CP)</option>
                <option value="CC">Conta Corrente (CC)</option>
                <option value="CI">Conta de Investimento (CI)</option>
              </select>
            </div>
            <div class="form-group">
              <label>Depósito Inicial</label>
              <input type="number" name="deposito_inicial" step="0.01" min="0" required>
            </div>
            <div class="form-group">
              <label>Senha da Conta</label>
              <input type="password" name="senha_conta" required>
            </div>
          </div>
          <button type="submit" class="btn-primary">Abrir Conta</button>
        </form>
      </div>
    </section>

    <!-- Geração de Cartão -->
    <section id="geracao-cartao" class="section">
      <div class="content-header">
        <h1>Geração de Cartão</h1>
        <p>Gere cartões para contas existentes</p>
      </div>
      <div class="form-container">
        <form>
          <div class="form-grid">
            <div class="form-group">
              <label>Número da Conta</label>
              <input type="text" name="numero_conta" required>
              <button type="button" class="btn-secondary" style="margin-top: 0.5rem;">Buscar Conta</button>
            </div>
            <div class="form-group">
              <label>Tipo de Cartão</label>
              <select name="tipo_cartao" required>
                <option value="">Selecione o tipo</option>
                <option value="debito">Cartão de Débito</option>
                <option value="credito">Cartão de Crédito</option>
                <option value="multiplo">Cartão Múltiplo</option>
              </select>
            </div>
            <div class="form-group">
              <label>Limite (para cartão de crédito)</label>
              <input type="number" name="limite" step="0.01" min="0">
            </div>
          </div>
          <button type="submit" class="btn-primary">Gerar Cartão</button>
        </form>
      </div>
    </section>

    <!-- Alterar Cliente -->
    <section id="alterar-cliente" class="section">
      <div class="content-header">
        <h1>Alterar Cadastro de Cliente</h1>
        <p>Busque e altere dados de clientes</p>
      </div>
      <div class="form-container">
        <div class="form-group">
          <label>Buscar Cliente</label>
          <input type="text" placeholder="Digite CPF ou nome do cliente">
          <button type="button" class="btn-secondary" style="margin-top: 0.5rem;">Buscar</button>
        </div>
      </div>
      <div class="table-container">
        <h3>Clientes Encontrados</h3>
        <table>
          <thead>
          <tr>
            <th>CPF</th>
            <th>Nome</th>
            <th>Email</th>
            <th>Telefone</th>
            <th>Agência</th>
            <th>Ações</th>
          </tr>
          </thead>
          <tbody>
          <tr>
            <td>123.456.789-01</td>
            <td>João Santos</td>
            <td>joao@email.com</td>
            <td>(61) 99999-9999</td>
            <td>001</td>
            <td>
              <button class="btn-secondary">Editar</button>
              <button class="btn-danger">Inativar</button>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
    </section>

    <!-- Encerramento de Conta -->
    <section id="encerramento-conta" class="section">
      <div class="content-header">
        <h1>Encerramento de Conta</h1>
        <p>Encerre contas seguindo os procedimentos de segurança</p>
      </div>
      <div class="form-container">
        <form>
          <div class="form-grid">
            <div class="form-group">
              <label>Número da Conta</label>
              <input type="text" name="numero_conta" required>
              <button type="button" class="btn-secondary" style="margin-top: 0.5rem;">Buscar Conta</button>
            </div>
            <div class="form-group">
              <label>Motivo do Encerramento</label>
              <select name="motivo" required>
                <option value="">Selecione o motivo</option>
                <option value="pedido_cliente">Pedido do Cliente</option>
                <option value="inatividade">Inatividade</option>
                <option value="inadimplencia">Inadimplência</option>
                <option value="outros">Outros</option>
              </select>
            </div>
            <div class="form-group">
              <label>Observações</label>
              <textarea name="observacoes" rows="3"></textarea>
            </div>
            <div class="form-group">
              <label>Senha de Administrador</label>
              <input type="password" name="senha_admin" required>
            </div>
            <div class="form-group">
              <label>Código OTP</label>
              <input type="text" name="otp" required>
            </div>
          </div>
          <button type="submit" class="btn-danger">Encerrar Conta</button>
        </form>
      </div>
    </section>

    <!-- Transações Cliente -->
    <section id="transacoes-cliente" class="section">
      <div class="content-header">
        <h1>Transações</h1>
        <p>Consulte e gerencie transações de clientes</p>
      </div>
      <div class="form-container">
        <div class="form-grid">
          <div class="form-group">
            <label>Número da Conta</label>
            <input type="text" name="numero_conta">
          </div>
          <div class="form-group">
            <label>Período</label>
            <select name="periodo">
              <option value="7">Últimos 7 dias</option>
              <option value="30">Últimos 30 dias</option>
              <option value="90">Últimos 90 dias</option>
              <option value="custom">Período personalizado</option>
            </select>
          </div>
          <div class="form-group">
            <label>Tipo de Transação</label>
            <select name="tipo_transacao">
              <option value="">Todas</option>
              <option value="deposito">Depósito</option>
              <option value="saque">Saque</option>
              <option value="transferencia">Transferência</option>
              <option value="pagamento">Pagamento</option>
            </select>
          </div>
        </div>
        <button type="button" class="btn-primary">Consultar Transações</button>
      </div>
      <div class="table-container">
        <h3>Histórico de Transações</h3>
        <div style="margin-bottom: 1rem;">
          <button class="btn-secondary">Exportar PDF</button>
          <button class="btn-secondary">Exportar Excel</button>
        </div>
        <table>
          <thead>
          <tr>
            <th>Data/Hora</th>
            <th>Tipo</th>
            <th>Valor</th>
            <th>Origem/Destino</th>
            <th>Status</th>
            <th>Saldo</th>
          </tr>
          </thead>
          <tbody>
          <tr>
            <td>14/06/2025 10:30</td>
            <td>Depósito</td>
            <td>R$ 500,00</td>
            <td>Caixa Eletrônico</td>
            <td>Concluído</td>
            <td>R$ 1.500,00</td>
          </tr>
          <tr>
            <td>13/06/2025 14:15</td>
            <td>Transferência</td>
            <td>R$ 200,00</td>
            <td>Conta 12345-6</td>
            <td>Concluído</td>
            <td>R$ 1.000,00</td>
          </tr>
          </tbody>
        </table>
      </div>
    </section>

    <!-- Análise de Conta -->
    <section id="analise-conta" class="section">
      <div class="content-header">
        <h1>Análise de Conta</h1>
        <p>Análise detalhada e score de crédito dos clientes</p>
      </div>
      <div class="form-container">
        <div class="form-group">
          <label>Número da Conta</label>
          <input type="text" name="numero_conta" required>
          <button type="button" class="btn-secondary" style="margin-top: 0.5rem;">Analisar Conta</button>
        </div>
      </div>
      <div class="dashboard-widgets">
        <div class="widget">
          <h3>Score de Crédito</h3>
          <div class="data-point">750</div>
          <p>Classificação: Bom</p>
        </div>
        <div class="widget">
          <h3>Saldo Médio</h3>
          <div class="data-point">R$ 2.340</div>
          <p>Últimos 90 dias</p>
        </div>
        <div class="widget">
          <h3>Movimentação Mensal</h3>
          <div class="data-point">R$ 4.500</div>
          <p>Média dos últimos 6 meses</p>
        </div>
        <div class="widget">
          <h3>Projeção de Rendimentos</h3>
          <div class="data-point">R$ 89,50</div>
          <p>Próximos 12 meses (Poupança)</p>
        </div>
      </div>
    </section>

    <!-- Relatórios -->
    <section id="relatorios" class="section">
      <div class="content-header">
        <h1>Relatórios</h1>
        <p>Gere relatórios detalhados do sistema</p>
      </div>
      <div class="dashboard-widgets">
        <div class="widget">
          <h3>Relatório de Movimentações</h3>
          <p>Relatório completo de todas as movimentações financeiras por período</p>
          <div style="margin-top: 1rem;">
            <button class="btn-secondary">Gerar PDF</button>
            <button class="btn-secondary">Gerar Excel</button>
          </div>
        </div>
        <div class="widget">
          <h3>Relatório de Inadimplência</h3>
          <p>Clientes em situação de inadimplência e análise de risco</p>
          <div style="margin-top: 1rem;">
            <button class="btn-secondary">Gerar PDF</button>
            <button class="btn-secondary">Gerar Excel</button>
          </div>
        </div>
        <div class="widget">
          <h3>Desempenho de Funcionários</h3>
          <p>Métricas de produtividade e performance por funcionário</p>
          <div style="margin-top: 1rem;">
            <button class="btn-secondary">Gerar PDF</button>
            <button class="btn-secondary">Gerar Excel</button>
          </div>
        </div>
        <div class="widget">
          <h3>Relatório Customizado</h3>
          <p>Configure parâmetros específicos para relatórios personalizados</p>
          <div style="margin-top: 1rem;">
            <button class="btn-primary">Configurar</button>
          </div>
        </div>
      </div>
    </section>

    <!-- Ferramentas Internas -->
    <section id="ferramentas" class="section">
      <div class="content-header">
        <h1>Ferramentas Internas</h1>
        <p>Ferramentas administrativas e de manutenção</p>
      </div>
      <div class="dashboard-widgets">
        <div class="widget">
          <h3>Backup do Sistema</h3>
          <p>Realizar backup completo ou incremental do banco de dados</p>
          <div style="margin-top: 1rem;">
            <button class="btn-primary">Backup Completo</button>
            <button class="btn-secondary">Backup Incremental</button>
          </div>
        </div>
        <div class="widget">
          <h3>Auditoria de Sistema</h3>
          <p>Consultar logs de auditoria e atividades do sistema</p>
          <div style="margin-top: 1rem;">
            <button class="btn-primary">Ver Logs</button>
          </div>
        </div>
        <div class="widget">
          <h3>Manutenção de Dados</h3>
          <p>Limpeza e otimização da base de dados</p>
          <div style="margin-top: 1rem;">
            <button class="btn-secondary">Otimizar BD</button>
          </div>
        </div>
        <div class="widget">
          <h3>Monitoramento</h3>
          <p>Status do sistema e alertas em tempo real</p>
          <div style="margin-top: 1rem;">
            <button class="btn-primary">Dashboard Técnico</button>
          </div>
        </div>
      </div>
    </section>

    <!-- Suporte -->
    <section id="suporte" class="section">
      <div class="content-header">
        <h1>Suporte</h1>
        <p>Central de ajuda e suporte técnico</p>
      </div>
      <div class="dashboard-widgets">
        <div class="widget">
          <h3>Tickets de Suporte</h3>
          <p>Abrir e acompanhar tickets de suporte técnico</p>
          <div style="margin-top: 1rem;">
            <button class="btn-primary">Novo Ticket</button>
            <button class="btn-secondary">Meus Tickets</button>
          </div>
        </div>
        <div class="widget">
          <h3>Base de Conhecimento</h3>
          <p>Documentação e guias de procedimentos</p>
          <div style="margin-top: 1rem;">
            <button class="btn-primary">Acessar</button>
          </div>
        </div>
        <div class="widget">
          <h3>Chat com Suporte</h3>
          <p>Suporte em tempo real via chat</p>
          <div style="margin-top: 1rem;">
            <button class="btn-primary">Iniciar Chat</button>
          </div>
        </div>
        <div class="widget">
          <h3>Treinamentos</h3>
          <p>Material de treinamento e capacitação</p>
          <div style="margin-top: 1rem;">
            <button class="btn-secondary">Ver Cursos</button>
          </div>
        </div>
      </div>
    </section>

    <!-- Configurações -->
    <section id="configuracoes" class="section">
      <div class="content-header">
        <h1>Configurações</h1>
        <p>Configurações do sistema e preferências</p>
      </div>
      <div class="form-container">
        <h3>Configurações Pessoais</h3>
        <form>
          <div class="form-grid">
            <div class="form-group">
              <label>Alterar Senha</label>
              <input type="password" name="senha_atual" placeholder="Senha atual">
            </div>
            <div class="form-group">
              <label>Nova Senha</label>
              <input type="password" name="nova_senha" placeholder="Nova senha">
            </div>
            <div class="form-group">
              <label>Confirmar Nova Senha</label>
              <input type="password" name="confirma_senha" placeholder="Confirmar nova senha">
            </div>
            <div class="form-group">
              <label>Tema do Sistema</label>
              <select name="tema">
                <option value="claro">Claro</option>
                <option value="escuro">Escuro</option>
                <option value="auto">Automático</option>
              </select>
            </div>
          </div>
          <button type="submit" class="btn-primary">Salvar Configurações</button>
        </form>
      </div>
      <div class="dashboard-widgets">
        <div class="widget">
          <h3>Configurações do Sistema</h3>
          <p>Parâmetros gerais do sistema bancário</p>
          <div style="margin-top: 1rem;">
            <button class="btn-secondary">Configurar</button>
          </div>
        </div>
        <div class="widget">
          <h3>Permissões</h3>
          <p>Gerenciar permissões e níveis de acesso</p>
          <div style="margin-top: 1rem;">
            <button class="btn-secondary">Gerenciar</button>
          </div>
        </div>
      </div>
    </section>
  </main>
</div>

<footer class="app-footer">
  &copy; <span id="currentYear">2025</span> Callisto Bank - Todos os direitos reservados.
</footer>

<script>
  // Função para alternar submenus
  document.addEventListener('DOMContentLoaded', function() {
    const menuToggles = document.querySelectorAll('.menu-toggle');
    const menuItems = document.querySelectorAll('.menu-item');
    const sections = document.querySelectorAll('.section');

    // Toggle submenus
    menuToggles.forEach(toggle => {
      toggle.addEventListener('click', function(e) {
        e.preventDefault();
        const submenuId = this.getAttribute('data-submenu');
        const submenu = document.getElementById(submenuId);

        // Fechar outros submenus
        document.querySelectorAll('.submenu').forEach(menu => {
          if (menu.id !== submenuId) {
            menu.classList.remove('active');
          }
        });

        // Toggle submenu atual
        submenu.classList.toggle('active');
      });
    });

    // Navegação entre seções
    menuItems.forEach(item => {
      item.addEventListener('click', function(e) {
        e.preventDefault();
        const sectionId = this.getAttribute('data-section');

        if (sectionId) {
          // Remover classe active de todos os itens de menu
          menuItems.forEach(menuItem => {
            menuItem.classList.remove('active');
          });

          // Adicionar classe active ao item clicado
          this.classList.add('active');

          // Ocultar todas as seções
          sections.forEach(section => {
            section.classList.remove('active');
          });

          // Mostrar seção correspondente
          const targetSection = document.getElementById(sectionId);
          if (targetSection) {
            targetSection.classList.add('active');
          }
        }
      });
    });

    // Definir ano atual no footer
    document.getElementById('currentYear').textContent = new Date().getFullYear();
  });
</script>
</body>
</html>