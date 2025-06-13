<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Portal do Cliente - Calisto Bank</title>
    
    <link href="https://fonts.googleapis.com/icon?family=Material+Icons" rel="stylesheet">
    
    <link rel="stylesheet" href="/src/main/webapp/css/portalcliente.css">
</head>
<body>
    <div class="background-layer"></div>

    <header class="main-header">
        <div class="header-container">
            <div class="logo">
                <img src="/src/main/webapp/img/image.svg" alt="Logo Calisto Bank" style="height:48px;">
            </div>
            <div class="user-menu">
                <span class="welcome-message">Olá, Cliente!</span>
                <a href="login.jsp" class="logout-btn">Sair</a>
            </div>
        </div>
    </header>

    <main class="main-container">
        
        <aside class="account-overview">
            
            <div class="balance-card">
                <div class="balance-header">
                    <h2>Saldo em Conta</h2>
                    <button class="toggle-balance" id="toggleBalanceBtn" title="Ocultar/Mostrar Saldo">
                        <i class="material-icons">visibility</i>
                    </button>
                </div>
                <div class="balance-value">
                    <span id="saldoValor"></span>
                </div>
            </div>

            <div class="quick-access">
                <h3>Acesso Rápido</h3>
                <div class="quick-access-grid">
                    <a href="#" class="quick-access-item" id="btn-extrato">
                        <i class="material-icons">receipt_long</i>
                        <span>Extrato</span>
                    </a>
                    <a href="#" class="quick-access-item" id="btn-transferir">
                        <i class="material-icons">swap_horiz</i>
                        <span>Transferir</span>
                    </a>
                    <a href="#" class="quick-access-item" id="btn-depositar">
                        <i class="material-icons">attach_money</i>
                        <span>Depositar</span>
                    </a>
                     <a href="#" class="quick-access-item" id="btn-sacar">
                        <i class="material-icons">local_atm</i>
                        <span>Saque</span>
                    </a>
                    <a href="#" class="quick-access-item" id="btn-cartoes">
                        <i class="material-icons">credit_card</i>
                        <span>Cartões</span>
                    </a>
                    <a href="#" class="quick-access-item" id="btn-meus-dados">
                        <i class="material-icons">person</i>
                        <span>Meus Dados</span>
                    </a>
                </div>
            </div>
        </aside>

        <section id="main-content"></section>

    </main>
    
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/2.5.1/jspdf.umd.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf-autotable/3.8.2/jspdf.plugin.autotable.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/xlsx/0.18.5/xlsx.full.min.js"></script>
    
    <script src="/src/main/webapp/js/portalcliente.js"></script>
</body>
</html>