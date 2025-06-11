<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Gerador de Cartão - Callisto Bank</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="../css/cartao.css">
</head>

<body>
<div id="messageBox" role="alert"
     style="position: fixed; top: 20px; left: 50%; transform: translateX(-50%); background-color: var(--error-red-light); color: var(--error-red-dark); border: 1px solid #f5c6cb; padding: 15px 20px; border-radius: 8px; z-index: 1000; display: none; box-shadow: 0 4px 12px rgba(0,0,0,0.15);">
    <span id="messageText"></span>
    <button aria-label="Fechar mensagem" onclick="document.getElementById('messageBox').style.display='none'"
            style="background: none; border: none; font-size: 1.2rem; cursor: pointer; float: right; margin-left: 10px; color: var(--error-red-dark);">
        &times;
    </button>
</div>

<div id="confirmDialog"
     style="position: fixed; top: 0; left: 0; width: 100%; height: 100%; background-color: rgba(0,0,0,0.5); display: none; justify-content: center; align-items: center; z-index: 1001;">
    <div style="background-color: white; padding: 30px; border-radius: 10px; box-shadow: 0 5px 15px rgba(0,0,0,0.3); text-align: center;">
        <p id="confirmMessage" style="margin-bottom: 20px; font-size: 1.1rem; color: var(--text-color-dark);"></p>
        <button id="confirmYesBtn"
                style="padding: 10px 20px; margin-right: 10px; background-color: #dc3545; color: white; border: none; border-radius: 5px; cursor: pointer;">
            Sim
        </button>
        <button id="confirmNoBtn"
                style="padding: 10px 20px; background-color: #6c757d; color: white; border: none; border-radius: 5px; cursor: pointer;">
            Não
        </button>
    </div>
</div>

<div class="container" id="mainContainer">
    <div class="form-section" id="formSection">
        <h2 class="form-title">Gerar Cartão Callisto</h2>
        <div class="form-note">
            Preencha os dados abaixo para gerar seu cartão personalizado
        </div>
        <form id="cardForm">
            <div class="form-group">
                <label class="form-label" for="cardType">Tipo de Cartão</label>
                <select id="cardType" class="form-select" required aria-label="Selecione o tipo de cartão">
                    <option value="PLATINUM">Callisto Platinum</option>
                    <option value="GOLD">Callisto Gold</option>
                    <option value="BLACK">Callisto Black</option>
                </select>
            </div>
            <div class="form-group">
                <label class="form-label" for="cardName">Nome Completo</label>
                <input type="text" id="cardName" class="form-input" placeholder="Digite seu nome completo" required
                       aria-required="true">
            </div>
            <div class="form-group">
                <label class="form-label" for="cardCpf">CPF</label>
                <input type="text" id="cardCpf" class="form-input" placeholder="000.000.000-00" maxlength="14" required
                       aria-required="true">
            </div>
            <button type="submit" class="generate-btn" aria-label="Gerar cartão">Gerar Cartão</button>
        </form>
        <div class="card-advantages-display" id="cardAdvantagesDisplay">
        </div>
    </div>

    <div class="card-section">
        <div class="credit-card" id="creditCard">
            <div class="card-inner card-animation">
                <div class="card-face card-front" id="cardFrontFace">
                    <div class="card-header">
                        <div class="bank-name">CALLISTO</div>
                        <div class="card-type" id="displayCardType">PLATINUM</div>
                    </div>
                    <div class="chip">
                        <svg viewBox="0 0 50 40">
                            <rect x="0" y="0" width="50" height="40" rx="8" ry="8" fill="#FFD700"/>
                            <rect x="5" y="5" width="40" height="30" rx="4" ry="4" fill="#333"/>
                            <rect x="10" y="10" width="10" height="8" fill="#FFD700"/>
                            <rect x="10" y="22" width="10" height="8" fill="#FFD700"/>
                            <rect x="20" y="10" width="10" height="8" fill="#FFD700"/>
                            <rect x="20" y="22" width="10" height="8" fill="#FFD700"/>
                            <rect x="30" y="10" width="10" height="8" fill="#FFD700"/>
                            <rect x="30" y="22" width="10" height="8" fill="#FFD700"/>
                        </svg>
                    </div>
                    <div class="card-number" id="displayCardNumber">0000 0000 0000 0000</div>
                    <div class="card-info">
                        <div class="card-field">
                            <div class="card-label">Nome do Portador</div>
                            <div class="card-value" id="displayCardName">SEU NOME AQUI</div>
                        </div>
                        <div class="card-field">
                            <div class="card-label">Válido até</div>
                            <div class="card-value" id="displayCardExpiry">MM/AA</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <div class="card-actions" id="cardActions">
            <button class="action-btn btn-secondary" id="newCardBtn" aria-label="Gerar novo cartão">Novo Cartão</button>
            <button class="action-btn btn-danger" id="clearCardBtn" aria-label="Limpar dados do cartão">Limpar Dados
            </button>
        </div>
        <div class="card-info-display" id="cardInfoDisplay">
            <h3 class="info-title">Informações do Cartão</h3>
            <div class="info-row">
                <span class="info-label">Portador:</span>
                <span class="info-value" id="infoName">-</span>
            </div>
            <div class="info-row">
                <span class="info-label">CPF:</span>
                <span class="info-value" id="infoCpf">-</span>
            </div>
            <div class="info-row">
                <span class="info-label">Número:</span>
                <span class="info-value" id="infoNumber">-</span>
            </div>
            <div class="info-row">
                <span class="info-label">Validade:</span>
                <span class="info-value" id="infoExpiry">-</span>
            </div>
            <div class="info-row">
                <span class="info-label">Tipo:</span>
                <span class="info-value" id="infoType">-</span>
            </div>
        </div>
    </div>
</div>

<script src="../js/cartao.js"></script>
</body>
</html>
