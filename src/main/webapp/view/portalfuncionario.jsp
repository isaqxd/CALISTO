<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href=../css/portalfuncionario.css">
    <title>Callisto Bank - Portal do Funcionário</title>
</head>
<body>
    <header class="header">
        <div class="header-content">
            <div class="logo">
                <img src="../img/image.svg" class="logo-image">
            </div>
            <div class="user-info">
                <div class="user-avatar">LE</div>
                <div class="user-details">
                    <div class="user-name">Luís Eduardo</div>
                    <div class="user-role">Gerente Geral</div>
                </div>
                <button class="logout-btn" onclick="logout()">Sair</button>
            </div>
        </div>
    </header>

    <div class="main-container">
        <div id="dashboard-home-elements" class="dashboard-home-elements">
            <div class="welcome-section loading">
                <h1 class="welcome-title">Portal do Funcionário</h1>
                <p class="welcome-subtitle">Gerencie operações bancárias com eficiência e segurança</p>
            </div>
            <div class="dashboard-grid loading">
                 <div class="dashboard-card">
                    <div class="card-icon">👥</div> <h3 class="card-title">Clientes Ativos</h3>
                     <p class="card-description">Visão geral da base de clientes.</p>
                    <div class="card-stats">
                        <div class="stat-item"><div class="stat-number" id="stat-clientes-ativos">1,247</div><div class="stat-label">Total</div></div>
                        <div class="stat-item"><div class="stat-number" id="stat-novos-clientes-hoje">23</div><div class="stat-label">Novos Hoje</div></div>
                    </div>
                    <button class="card-button" onclick="navigateTo('consulta-dados', 'cliente')">Consultar Cliente</button>
                </div>
                <div class="dashboard-card">
                    <div class="card-icon">💰</div> <h3 class="card-title">Volume de Transações</h3>
                     <p class="card-description">Acompanhamento do fluxo financeiro.</p>
                    <div class="card-stats">
                        <div class="stat-item"><div class="stat-number" id="stat-volume-hoje">R$ 2.4M</div><div class="stat-label">Hoje</div></div>
                        <div class="stat-item"><div class="stat-number" id="stat-transacoes-pendentes">156</div><div class="stat-label">Pendentes</div></div>
                    </div>
                     <button class="card-button" onclick="navigateTo('relatorios')">Ver Relatórios</button>
                </div>
                 <div class="dashboard-card">
                    <div class="card-icon">➕</div> <h3 class="card-title">Abertura Rápida</h3>
                    <p class="card-description">Inicie o processo de abertura de conta.</p>
                     <div class="card-stats">
                        <div class="stat-item"><div class="stat-number" id="stat-contas-abertas-mes">78</div><div class="stat-label">Este Mês</div></div>
                        <div class="stat-item"><div class="stat-number" id="stat-media-diaria">~4</div><div class="stat-label">Média Dia</div></div>
                    </div>
                    <button class="card-button" onclick="navigateTo('abertura-conta')">Abrir Nova Conta</button>
                </div>
            </div>
            <div class="menu-section-visual loading">
                <h2 class="section-title">Operações Principais</h2>
                <div class="menu-grid">
                    <div class="menu-item-card" onclick="navigateTo('abertura-conta')">
                        <div class="menu-item-icon">➕</div> <div class="menu-item-title">Abertura de Conta</div>
                        <div class="menu-item-desc">Cadastre novos clientes e contas.</div>
                    </div>
                    <div class="menu-item-card" onclick="navigateTo('encerramento-conta')">
                        <div class="menu-item-icon">❌</div> <div class="menu-item-title">Encerramento de Conta</div>
                        <div class="menu-item-desc">Processe solicitações de encerramento.</div>
                    </div>
                    <div class="menu-item-card" onclick="navigateTo('consulta-dados')">
                        <div class="menu-item-icon">🔍</div> <div class="menu-item-title">Consulta de Dados</div>
                        <div class="menu-item-desc">Acesse informações de contas, clientes e funcionários.</div>
                    </div>
                    <div class="menu-item-card" onclick="navigateTo('alteracao-dados')">
                        <div class="menu-item-icon">✏️</div> <div class="menu-item-title">Alteração de Dados</div>
                        <div class="menu-item-desc">Modifique informações cadastrais.</div>
                    </div>
                    <div class="menu-item-card" onclick="navigateTo('cadastro-funcionario')">
                        <div class="menu-item-icon">👤</div> <div class="menu-item-title">Cadastro de Funcionário</div>
                        <div class="menu-item-desc">Gerencie a equipe interna.</div>
                    </div>
                    <div class="menu-item-card" onclick="navigateTo('relatorios')">
                        <div class="menu-item-icon">📈</div> <div class="menu-item-title">Geração de Relatórios</div>
                        <div class="menu-item-desc">Exporte dados e análises.</div>
                    </div>
                </div>
            </div>
            <div class="quick-actions loading">
                <button class="quick-action-btn" onclick="navigateTo('abertura-conta')">➕ Nova Conta</button>
                <button class="quick-action-btn" onclick="navigateTo('consulta-dados', 'conta')">🔍 Consultar Conta</button>
                <button class="quick-action-btn" onclick="navigateTo('relatorios')">📊 Gerar Relatório Rápido</button>
            </div>
        </div>

        <div id="functional-sections-wrapper">
            <div id="abertura-conta" class="functional-section">
                <button class="btn btn-secondary" onclick="showDashboardHome()" style="margin-bottom: 2rem;">⬅️ Voltar ao Menu</button>
                <h1 class="content-title">Abertura de Conta</h1>
                <form id="form-abertura-conta">
                    <div class="form-grid">
                        <div class="form-group"><label for="nome">Nome Completo*</label><input type="text" id="nome" required></div>
                        <div class="form-group"><label for="cpf">CPF*</label><input type="text" id="cpf" required placeholder="000.000.000-00"></div>
                        <div class="form-group"><label for="nascimento">Data de Nascimento*</label><input type="date" id="nascimento" required></div>
                        <div class="form-group"><label for="telefone">Telefone*</label><input type="tel" id="telefone" required placeholder="(11) 99999-9999"></div>
                        <div class="form-group full-width"><label for="endereco">Endereço Completo*</label><textarea id="endereco" rows="3" required placeholder="Rua, número, complemento, bairro, cidade, CEP"></textarea></div>
                        <div class="form-group">
                            <label for="tipo-conta">Tipo de Conta*</label>
                            <select id="tipo-conta" required onchange="toggleAccountFields()">
                                <option value="">Selecione...</option><option value="poupanca">Conta Poupança (CP)</option><option value="corrente">Conta Corrente (CC)</option><option value="investimento">Conta Investimento (CI)</option>
                            </select>
                        </div>
                    </div>
                    <div id="campos-poupanca" class="form-grid" style="display: none;"><div class="form-group"><label for="taxa-rendimento">Taxa de Rendimento (%)</label><input type="number" id="taxa-rendimento" step="0.01" placeholder="0.50"></div></div>
                    <div id="campos-corrente" class="form-grid" style="display: none;">
                        <div class="form-group"><label for="limite">Limite de Crédito (R$)</label><input type="number" id="limite" step="0.01" placeholder="1000.00"></div>
                        <div class="form-group"><label for="taxa-manutencao">Taxa de Manutenção (R$)</label><input type="number" id="taxa-manutencao" step="0.01" placeholder="15.00"></div>
                        <div class="form-group"><label for="vencimento">Vencimento</label><input type="date" id="vencimento"></div>
                    </div>
                    <div id="campos-investimento" class="form-grid" style="display: none;">
                        <div class="form-group">
                            <label for="perfil-risco">Perfil de Risco</label>
                            <select id="perfil-risco"><option value="">Selecione...</option><option value="conservador">Conservador</option><option value="moderado">Moderado</option><option value="arrojado">Arrojado</option></select>
                        </div>
                        <div class="form-group"><label for="valor-minimo">Valor Mínimo (R$)</label><input type="number" id="valor-minimo" step="0.01" placeholder="5000.00"></div>
                        <div class="form-group"><label for="taxa-base">Taxa Base (%)</label><input type="number" id="taxa-base" step="0.01" placeholder="1.25"></div>
                    </div>
                    <div class="info-card"><h3>Número da Conta</h3><p id="numero-conta-preview">Será gerado automaticamente</p></div>
                    <button type="submit" class="btn">Criar Conta</button>
                    <button type="reset" class="btn btn-secondary" onclick="handleFormReset('form-abertura-conta')">Limpar</button>
                </form>
            </div>

            <div id="encerramento-conta" class="functional-section">
                 <button class="btn btn-secondary" onclick="showDashboardHome()" style="margin-bottom: 2rem;">⬅️ Voltar</button>
                <h1 class="content-title">Encerramento de Conta</h1>
                <form id="form-encerramento-conta">
                    <div class="form-grid">
                        <div class="form-group"><label for="numero-conta-encerrar">Número da Conta*</label><input type="text" id="numero-conta-encerrar" required placeholder="12345-6"></div>
                        <div class="form-group"><label for="senha-admin-enc">Senha de Administrador*</label><input type="password" id="senha-admin-enc" required></div>
                        <div class="form-group"><label for="otp">Código OTP*</label><input type="text" id="otp" required placeholder="123456"></div>
                        <div class="form-group">
                            <label for="motivo-encerramento">Motivo*</label>
                            <select id="motivo-encerramento" required>
                                <option value="">Selecione...</option><option value="solicitacao">Solicitação do Cliente</option><option value="inadimplencia">Inadimplência</option><option value="inatividade">Inatividade</option><option value="fraude">Suspeita de Fraude</option><option value="obito">Óbito</option><option value="outros">Outros</option>
                            </select>
                        </div>
                    </div>
                    <div id="saldo-negativo-warning" class="info-card" style="display: none; background: #fff3cd; border-left-color: #ffc107;"><h3>⚠️ Saldo Negativo</h3><p>Conta com pendências.</p></div>
                    <button type="submit" class="btn btn-danger">Encerrar Conta</button>
                    <button type="button" class="btn btn-secondary" onclick="consultarContaParaEncerramento()">Consultar Saldo</button>
                </form>
            </div>

            <div id="consulta-dados" class="functional-section">
                <button class="btn btn-secondary" onclick="showDashboardHome()" style="margin-bottom: 2rem;">⬅️ Voltar</button>
                <h1 class="content-title">Consulta de Dados</h1>
                <div class="form-grid" style="grid-template-columns: repeat(3, 1fr); margin-bottom: 1rem;">
                    <button class="btn" id="btn-show-consulta-conta" onclick="showConsultaSubSection('conta')">Conta</button>
                    <button class="btn" id="btn-show-consulta-funcionario" onclick="showConsultaSubSection('funcionario')">Funcionário</button>
                    <button class="btn" id="btn-show-consulta-cliente" onclick="showConsultaSubSection('cliente')">Cliente</button>
                </div>
                <div id="consulta-conta-sub" class="consulta-subsection" style="display: none;">
                    <h3 style="margin-top:1.5rem; margin-bottom:1rem; color:#260A04;">Consulta de Conta</h3>
                    <form id="form-consulta-conta" class="form-grid" style="align-items: end;">
                        <div class="form-group"><label for="numero-conta-consulta">Núm. Conta</label><input type="text" id="numero-conta-consulta" placeholder="12345-6"></div>
                        <div class="form-group"><button type="button" class="btn" onclick="consultarDadosConta()">Consultar</button></div>
                    </form>
                    <div id="resultado-consulta-conta" class="info-card" style="display: none;">
                        <h4>Conta <span id="consulta-conta-numero-display"></span></h4>
                        <ul>
                            <li><strong>Tipo:</strong> <span id="consulta-conta-tipo">N/A</span></li><li><strong>Saldo:</strong> <span id="consulta-conta-saldo">N/A</span></li>
                            <li><strong>Limite:</strong> <span id="consulta-conta-limite">N/A</span></li><li><strong>Proj. Rend. (30d):</strong> <span id="consulta-conta-projecao">N/A</span></li>
                        </ul>
                        <h4>Histórico (90 dias)</h4>
                        <div style="max-height: 300px; overflow-y: auto;"><table class="data-table" id="historico-conta-table"><thead><tr><th>Data</th><th>Descrição</th><th>Valor</th><th>Saldo Ant.</th><th>Saldo Post.</th></tr></thead><tbody><tr><td colspan="5" style="text-align:center;">Nenhum histórico.</td></tr></tbody></table></div>
                    </div>
                </div>
                <div id="consulta-funcionario-sub" class="consulta-subsection" style="display: none;">
                     <h3 style="margin-top:1.5rem; margin-bottom:1rem; color:#260A04;">Consulta de Funcionário</h3>
                     <form id="form-consulta-funcionario" class="form-grid" style="align-items: end;">
                        <div class="form-group"><label for="codigo-funcionario-consulta">Cód. Funcionário</label><input type="text" id="codigo-funcionario-consulta" placeholder="FUNC001"></div>
                        <div class="form-group"><button type="button" class="btn" onclick="consultarDadosFuncionario()">Consultar</button></div>
                    </form>
                    <div id="resultado-consulta-funcionario" class="info-card" style="display: none;">
                        <h4>Funcionário: <span id="consulta-funcionario-nome">N/A</span> (<span id="consulta-funcionario-codigo-display"></span>)</h4>
                        <p><strong>CPF:</strong> <span id="consulta-funcionario-cpf">N/A</span></p><p><strong>Nasc.:</strong> <span id="consulta-funcionario-nascimento">N/A</span></p>
                        <p><strong>Tel:</strong> <span id="consulta-funcionario-telefone">N/A</span></p><p><strong>End.:</strong> <span id="consulta-funcionario-endereco">N/A</span></p>
                        <p><strong>Cargo:</strong> <span id="consulta-funcionario-cargo">N/A</span></p><p><strong>Desempenho (Mov. 30d):</strong> <span id="consulta-funcionario-desempenho">N/A</span></p>
                    </div>
                </div>
                <div id="consulta-cliente-sub" class="consulta-subsection" style="display: none;">
                     <h3 style="margin-top:1.5rem; margin-bottom:1rem; color:#260A04;">Consulta de Cliente</h3>
                    <form id="form-consulta-cliente" class="form-grid" style="align-items: end;">
                        <div class="form-group"><label for="cpf-cliente-consulta">CPF Cliente</label><input type="text" id="cpf-cliente-consulta" placeholder="000.000.000-00"></div>
                        <div class="form-group"><button type="button" class="btn" onclick="consultarDadosCliente()">Consultar</button></div>
                    </form>
                    <div id="resultado-consulta-cliente" class="info-card" style="display: none;">
                        <h4>Cliente: <span id="consulta-cliente-nome-display">N/A</span> (<span id="consulta-cliente-cpf-display"></span>)</h4>
                        <p><strong>Nasc.:</strong> <span id="consulta-cliente-nascimento">N/A</span></p><p><strong>Tel:</strong> <span id="consulta-cliente-telefone-display">N/A</span></p>
                        <p><strong>End.:</strong> <span id="consulta-cliente-endereco-display">N/A</span></p><p><strong>Score Créd.:</strong> <span id="score-credito-cliente">N/A</span></p>
                        <p><strong>Contas Ativas:</strong> <span id="contas-ativas-cliente">N/A</span></p><p><strong>Contas Inativas:</strong> <span id="contas-inativas-cliente">N/A</span></p>
                    </div>
                </div>
            </div>

            <div id="alteracao-dados" class="functional-section">
                <button class="btn btn-secondary" onclick="showDashboardHome()" style="margin-bottom: 2rem;">⬅️ Voltar</button>
                <h1 class="content-title">Alteração de Dados</h1>
                 <div class="form-grid" style="grid-template-columns: repeat(3, 1fr); margin-bottom: 1rem;">
                    <button class="btn" id="btn-show-alteracao-conta" onclick="showAlteracaoSubSection('conta')">Conta</button>
                    <button class="btn" id="btn-show-alteracao-funcionario" onclick="showAlteracaoSubSection('funcionario')">Funcionário</button>
                    <button class="btn" id="btn-show-alteracao-cliente" onclick="showAlteracaoSubSection('cliente')">Cliente</button>
                </div>
                <div id="alteracao-conta-sub" class="alteracao-subsection" style="display: none;">
                    <h3 style="margin-top:1.5rem; margin-bottom:1rem; color:#260A04;">Alterar Dados da Conta</h3>
                    <form id="form-alteracao-conta">
                        <div class="form-grid">
                            <div class="form-group"><label for="numero-conta-alterar">Núm. Conta*</label><input type="text" id="numero-conta-alterar" placeholder="12345-6" required></div>
                            <div class="form-group"><label for="novo-limite-conta">Novo Limite (R$)</label><input type="number" id="novo-limite-conta" step="0.01"></div>
                            <div class="form-group"><label for="novo-vencimento-conta">Novo Vencimento</label><input type="date" id="novo-vencimento-conta"></div>
                            <div class="form-group"><label for="nova-taxa-manutencao-conta">Nova Taxa Manut. (R$)</label><input type="number" id="nova-taxa-manutencao-conta" step="0.01"></div>
                            <div class="form-group full-width"><label for="senha-admin-alt-conta">Senha Admin*</label><input type="password" id="senha-admin-alt-conta" required></div>
                        </div><button type="submit" class="btn">Salvar (Conta)</button>
                    </form>
                </div>
                 <div id="alteracao-funcionario-sub" class="alteracao-subsection" style="display: none;">
                    <h3 style="margin-top:1.5rem; margin-bottom:1rem; color:#260A04;">Alterar Dados do Funcionário</h3>
                    <form id="form-alteracao-funcionario">
                        <div class="form-grid">
                            <div class="form-group"><label for="codigo-funcionario-alterar">Cód. Funcionário*</label><input type="text" id="codigo-funcionario-alterar" placeholder="FUNC001" required></div>
                            <div class="form-group"><label for="novo-cargo-funcionario">Novo Cargo</label><select id="novo-cargo-funcionario"><option value="">Manter</option><option value="estagiario">Estagiário</option><option value="atendente">Atendente</option><option value="gerente">Gerente</option></select></div>
                            <div class="form-group"><label for="novo-telefone-funcionario">Novo Telefone</label><input type="tel" id="novo-telefone-funcionario" placeholder="Manter atual"></div>
                            <div class="form-group full-width"><label for="novo-endereco-funcionario">Novo Endereço</label><textarea id="novo-endereco-funcionario" rows="3" placeholder="Manter atual"></textarea></div>
                            <div class="form-group full-width"><label for="senha-admin-alt-funcionario">Senha Admin*</label><input type="password" id="senha-admin-alt-funcionario" required></div>
                        </div><button type="submit" class="btn">Salvar (Funcionário)</button>
                    </form>
                </div>
                <div id="alteracao-cliente-sub" class="alteracao-subsection" style="display: none;">
                    <h3 style="margin-top:1.5rem; margin-bottom:1rem; color:#260A04;">Alterar Dados do Cliente</h3>
                    <form id="form-alteracao-cliente">
                        <div class="form-grid">
                            <div class="form-group"><label for="cpf-cliente-alterar">CPF Cliente*</label><input type="text" id="cpf-cliente-alterar" placeholder="000.000.000-00" required></div>
                            <div class="form-group"><label for="novo-telefone-cliente">Novo Telefone</label><input type="tel" id="novo-telefone-cliente" placeholder="Manter atual"></div>
                            <div class="form-group full-width"><label for="novo-endereco-cliente">Novo Endereço</label><textarea id="novo-endereco-cliente" rows="3" placeholder="Manter atual"></textarea></div>
                            <div class="form-group"><label for="nova-senha-cliente">Nova Senha Cliente</label><input type="password" id="nova-senha-cliente" placeholder="Manter atual"></div>
                            <div class="form-group"><label for="confirmar-nova-senha-cliente">Confirmar Nova Senha</label><input type="password" id="confirmar-nova-senha-cliente"></div>
                            <div class="form-group full-width"><label for="senha-admin-alt-cliente">Senha Admin*</label><input type="password" id="senha-admin-alt-cliente" required></div>
                        </div><button type="submit" class="btn">Salvar (Cliente)</button>
                    </form>
                </div>
            </div>

            <div id="cadastro-funcionario" class="functional-section">
                <button class="btn btn-secondary" onclick="showDashboardHome()" style="margin-bottom: 2rem;">⬅️ Voltar</button>
                <h1 class="content-title">Cadastro de Funcionário</h1>
                <form id="form-cadastro-funcionario">
                    <div class="form-grid">
                        <div class="form-group"><label for="nome-func">Nome Completo*</label><input type="text" id="nome-func" required></div>
                        <div class="form-group"><label for="cpf-func">CPF*</label><input type="text" id="cpf-func" required placeholder="000.000.000-00"></div>
                        <div class="form-group"><label for="nascimento-func">Nascimento*</label><input type="date" id="nascimento-func" required></div>
                        <div class="form-group"><label for="telefone-func">Telefone*</label><input type="tel" id="telefone-func" required placeholder="(11) 99999-9999"></div>
                        <div class="form-group full-width"><label for="endereco-func">Endereço Completo*</label><textarea id="endereco-func" rows="3" required placeholder="Rua, nº, bairro, cidade, CEP"></textarea></div>
                        <div class="form-group"><label for="cargo-func">Cargo*</label><select id="cargo-func" required><option value="">Selecione...</option><option value="estagiario">Estagiário</option><option value="atendente">Atendente</option><option value="gerente">Gerente</option></select></div>
                        <div class="form-group"><label for="senha-func">Senha de Acesso*</label><input type="password" id="senha-func" required></div>
                        <div class="form-group"><label for="confirmar-senha-func">Confirmar Senha*</label><input type="password" id="confirmar-senha-func" required></div>
                    </div>
                    <div class="info-card"><h3>Código do Funcionário</h3><p id="codigo-funcionario-preview">Será gerado: FUNC###</p></div>
                    <button type="submit" class="btn">Cadastrar Funcionário</button>
                    <button type="reset" class="btn btn-secondary" onclick="handleFormReset('form-cadastro-funcionario')">Limpar</button>
                </form>
            </div>

            <div id="relatorios" class="functional-section">
                <button class="btn btn-secondary" onclick="showDashboardHome()" style="margin-bottom: 2rem;">⬅️ Voltar</button>
                <h1 class="content-title">Geração de Relatórios</h1>
                 <form id="form-filtros-relatorios">
                    <div class="form-grid">
                        <div class="form-group"><label for="data-inicio-rel">Data Início</label><input type="date" id="data-inicio-rel"></div>
                        <div class="form-group"><label for="data-fim-rel">Data Fim</label><input type="date" id="data-fim-rel"></div>
                        <div class="form-group"><label for="tipo-transacao-rel">Tipo Transação</label><select id="tipo-transacao-rel"><option value="">Todas</option><option value="deposito">Depósito</option><option value="saque">Saque</option><option value="transferencia">Transferência</option><option value="pagamento">Pagamento</option></select></div>
                        <div class="form-group"><label for="agencia-rel">Agência</label><select id="agencia-rel"><option value="">Todas</option><option value="001">Ag. 001</option><option value="002">Ag. 002</option><option value="003">Ag. 003</option></select></div>
                    </div>
                </form>
                <div class="form-grid" style="grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));">
                    <button class="btn" onclick="gerarRelatorio('movimentacoes')">Movimentações</button>
                    <button class="btn" onclick="gerarRelatorio('inadimplencia')">Inadimplência</button>
                    <button class="btn" onclick="gerarRelatorio('desempenho')">Desempenho</button>
                </div>
                <div id="resultado-relatorio" class="info-card" style="display: none; margin-top: 1.5rem;">
                    <h3>Resultado: <span id="tipo-relatorio-display"></span></h3>
                     <div style="max-height: 400px; overflow-y: auto;"><table class="data-table"><thead id="tabela-relatorio-head"></thead><tbody id="tabela-relatorio-body"></tbody></table></div>
                    <div class="form-grid" style="margin-top: 1.5rem; justify-content: flex-start;">
                        <button class="btn btn-secondary" onclick="exportarPDF()">📄 Exportar PDF</button>
                        <button class="btn btn-secondary" onclick="exportarExcel()">📊 Exportar Excel</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <script src="../js/portalfuncionario.js"></script>
</body>
</html>