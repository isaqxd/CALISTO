<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Portal do Cliente - Calisto Bank</title>
    
    <!-- Ícones do Google Fonts -->
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    
    <!-- Link para a folha de estilo externa -->
    <link rel="stylesheet" href="/src/main/webapp/css/portalcliente.css">
</head>
<body>
    <div class="background-layer"></div>

    <header class="main-header">
        <div class="header-container">
            <div class="logo">
                <img src="/src/main/webapp/img/image.svg" alt="Logo" style="height:48px;">
            </div>
            <div class="user-menu">
                <span class="welcome-message"></span>
                <a href="#" class="logout-btn">Sair</a>
            </div>
        </div>
    </header>

    <main class="main-container">
        <section class="account-overview">
            <div class="balance-card">
                <div class="balance-header">
                    <h2>Saldo em Conta Corrente</h2>
                    <button class="toggle-balance" id="toggleBalanceBtn" title="Ocultar Saldo">
                        <i class="material-icons">visibility</i>
                    </button>
                </div>
                <div class="balance-value">
                    <span id="saldoValor">Carregando...</span>
                </div>
                <div class="account-details">
                    <span>Agência: <span id="agenciaValor"></span></span>
                    <span>Conta: <span id="contaValor"></span></span>
                </div>
                <div class="special-limit">
                    <span>Limite Cheque Especial:</span>
                    <span id="limiteValor"></span>
                </div>
            </div>
            <div class="quick-access">
                <h3>Acesso Rápido</h3>
                <div class="quick-access-grid">
                    <a href="#" class="quick-access-item"><i class="material-icons">swap_horiz</i><span>Transferir</span></a>                <a href="#" class="quick-access-item"><i class="material-icons">credit_card</i><span>Cartões</span></a>
                    <a href="#" class="quick-access-item"><i class="material-icons">qr_code_scanner</i><span>Pagar QR</span></a>
                    <a href="#" class="quick-access-item"><i class="material-icons">savings</i><span>Investir</span></a>
                </div>
            </div>
        </section>

        <section class="main-content">
            <div class="content-card">
                <h3>Últimos Lançamentos</h3>
                <div class="transaction-list" id="transactionList">
                    <div class="transaction-placeholder">
                        <p>Nenhum lançamento recente.</p>
                    </div>
                </div>
                <a href="#" class="view-more-link">Ver extrato completo</a>
            </div>
        </section>
    </main>

    <!-- Link para o arquivo JavaScript externo -->
    <script src="/src/main/webapp/js/portalcliente.js"></script>
</body>
</html>
