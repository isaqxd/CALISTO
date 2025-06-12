// IIFE (Immediately Invoked Function Expression) para encapsular o código JavaScript
(function() {
    let cardData = {}; // Objeto para armazenar os dados do cartão gerado
    const generatedCardTypes = new Set(); // Conjunto para controlar os tipos de cartão já gerados
    const LOCAL_STORAGE_CARD_KEY = 'callistoCardData'; // Chave para localStorage do cartão
    const LOCAL_STORAGE_TYPES_KEY = 'callistoGeneratedTypes'; // Chave para localStorage dos tipos gerados

    /**
     * Exibe uma mensagem personalizada na tela.
     * @param {string} message - A mensagem a ser exibida.
     * @param {string} type - O tipo da mensagem ('success' ou 'error').
     */
    function showMessage(message, type = 'error') {
        const msgBox = document.getElementById('messageBox');
        const msgText = document.getElementById('messageText');
        if (msgBox && msgText) { // Verifica se os elementos existem
            msgText.textContent = message;

            // Define as cores da caixa de mensagem com base no tipo
            msgBox.style.backgroundColor = type === 'error' ? 'var(--error-red-light)' : '#d4edda';
            msgBox.style.color = type === 'error' ? 'var(--error-red-dark)' : '#155724';
            msgBox.style.borderColor = type === 'error' ? '#f5c6cb' : '#c3e6cb';
            msgBox.style.display = 'block';

            // Oculta a mensagem automaticamente após 3 segundos
            setTimeout(() => {
                msgBox.style.display = 'none';
            }, 3000);
        } else {
            console.error('Elementos da MessageBox não encontrados.');
        }
    }

    /**
     * Exibe um diálogo de confirmação personalizado.
     * @param {string} message - A mensagem de confirmação.
     * @param {function(boolean): void} callback - Função de retorno chamada com 'true' para sim, 'false' para não.
     */
    function showConfirm(message, callback) {
        const dialog = document.getElementById('confirmDialog');
        const msg = document.getElementById('confirmMessage');
        const yesBtn = document.getElementById('confirmYesBtn');
        const noBtn = document.getElementById('confirmNoBtn');

        if (dialog && msg && yesBtn && noBtn) { // Verifica se os elementos existem
            msg.textContent = message;
            dialog.style.display = 'flex'; // Torna o diálogo visível

            // Função para limpar os event listeners e ocultar o diálogo
            const cleanUp = () => {
                yesBtn.onclick = null;
                noBtn.onclick = null;
                dialog.style.display = 'none';
            };

            // Define os event listeners para os botões de sim/não
            yesBtn.onclick = () => {
                cleanUp();
                callback(true);
            };
            noBtn.onclick = () => {
                cleanUp();
                callback(false);
            };
        } else {
            console.error('Elementos do ConfirmDialog não encontrados.');
        }
    }

    /**
     * Valida um número de CPF.
     * @param {string} cpf - O número do CPF (pode estar formatado ou não).
     * @returns {boolean} True se o CPF for válido, false caso contrário.
     */
    function validateCPF(cpf) {
        cpf = cpf.replace(/[^\d]+/g, ''); // Remove caracteres não numéricos

        if (cpf.length !== 11 || /^(\d)\1{10}$/.test(cpf)) {
            return false; // Verifica se tem 11 dígitos e se não são todos iguais
        }

        let sum = 0;
        let remainder;

        // Validação do primeiro dígito verificador
        for (let i = 1; i <= 9; i++) {
            sum = sum + parseInt(cpf.substring(i - 1, i)) * (11 - i);
        }
        remainder = (sum * 10) % 11;
        if ((remainder === 10) || (remainder === 11)) {
            remainder = 0;
        }
        if (remainder !== parseInt(cpf.substring(9, 10))) {
            return false;
        }

        sum = 0;
        // Validação do segundo dígito verificador
        for (let i = 1; i <= 10; i++) {
            sum = sum + parseInt(cpf.substring(i - 1, i)) * (12 - i);
        }
        remainder = (sum * 10) % 11;
        if ((remainder === 10) || (remainder === 11)) {
            remainder = 0;
        }
        if (remainder !== parseInt(cpf.substring(10, 11))) {
            return false;
        }

        return true;
    }

    /**
     * Formata um nome completo, abreviando o segundo nome (segunda palavra).
     * Ex: "Luiz Gustavo Damascena" -> "Luiz G. Damascena"
     * Ex: "João Silva" -> "João Silva"
     * Ex: "Maria da Conceição Souza" -> "Maria d. Conceição Souza"
     * @param {string} fullName - O nome completo.
     * @returns {string} O nome formatado.
     */
    function formatName(fullName) {
        const parts = fullName.split(' ').filter(part => part.length > 0); // Divide e remove partes vazias

        if (parts.length <= 2) {
            return fullName; // Não abrevia se tiver 2 palavras ou menos
        }

        const firstName = parts[0];
        const secondNameAbbr = parts[1].charAt(0) ; // Primeira letra da segunda palavra + ponto
        const remainingParts = parts.slice(2).join(' '); // O resto do nome

        return `${firstName} ${secondNameAbbr} ${remainingParts}`;
    }

    // Carrega dados salvos ao inicializar a página
    window.addEventListener('load', function() {
        const savedCard = loadCardData();
        const savedTypes = loadGeneratedTypes();

        if (savedTypes) {
            savedTypes.forEach(type => generatedCardTypes.add(type));
        }

        if (savedCard) {
            displaySavedCard(savedCard);
        } else {
            // Inicializa o visual do cartão e as vantagens para o tipo padrão (Platinum)
            const initialCardTypeSelect = document.getElementById('cardType');
            if (initialCardTypeSelect) {
                updateCardDisplayAndAdvantages(initialCardTypeSelect.value);
            }
        }
        updateCardTypeOptions(); // Atualiza as opções do dropdown ao carregar a página
    });

    // Formatação do input CPF
    const cardCpfElement = document.getElementById('cardCpf');
    if (cardCpfElement) {
        cardCpfElement.addEventListener('input', function(e) {
            let value = e.target.value.replace(/\D/g, ''); // Remove tudo que não é dígito
            value = value.replace(/(\d{3})(\d)/, '$1.$2'); // Adiciona o primeiro ponto
            value = value.replace(/(\d{3})(\d)/, '$1.$2'); // Adiciona o segundo ponto
            value = value.replace(/(\d{3})(\d{1,2})$/, '$1-$2'); // Adiciona o traço
            e.target.value = value;
        });
    }

    // Atualiza o nome no cartão visual enquanto o usuário digita
    const cardNameElement = document.getElementById('cardName');
    if (cardNameElement) {
        cardNameElement.addEventListener('input', function(e) {
            const name = e.target.value.toUpperCase();
            const displayCardNameElement = document.getElementById('displayCardName');
            if (displayCardNameElement) {
                // Aplica a formatação do nome ao exibir
                displayCardNameElement.textContent = formatName(name) || 'SEU NOME AQUI';
            }
        });
    }

    /**
     * Gera um número de cartão de crédito fictício (Visa).
     * @returns {string} O número do cartão formatado com espaços.
     */
    function generateCardNumber() {
        // Começa com 4555 (prefixo comum para Visa) + 12 dígitos aleatórios
        let cardNumber = '4555';
        for (let i = 0; i < 12; i++) {
            cardNumber += Math.floor(Math.random() * 10);
        }
        // Formata com espaços a cada 4 dígitos
        return cardNumber.replace(/(\d{4})(?=\d)/g, '$1 ');
    }

    /**
     * Gera uma data de validade fictícia (MM/AA) para 6 anos no futuro.
     * @returns {string} A data de validade formatada.
     */
    function generateExpiryDate() {
        const currentDate = new Date();
        // Adiciona 6 anos à data atual
        const expiryDate = new Date(currentDate.setFullYear(currentDate.getFullYear() + 6));

        const month = String(expiryDate.getMonth() + 1).padStart(2, '0'); // Mês com 2 dígitos
        const year = String(expiryDate.getFullYear()).slice(-2); // Últimos 2 dígitos do ano

        return `${month}/${year}`;
    }

    /**
     * Salva os dados do cartão no localStorage.
     * @param {object} data - O objeto com os dados do cartão.
     */
    function saveCardData(data) {
        cardData = data;
        localStorage.setItem(LOCAL_STORAGE_CARD_KEY, JSON.stringify(data));
        console.log('Cartão salvo no localStorage:', data);
    }

    /**
     * Carrega os dados do cartão do localStorage.
     * @returns {object|null} O objeto com os dados do cartão ou null se não houver.
     */
    function loadCardData() {
        const saved = localStorage.getItem(LOCAL_STORAGE_CARD_KEY);
        return saved ? JSON.parse(saved) : null;
    }

    /**
     * Salva os tipos de cartão já gerados no localStorage.
     */
    function saveGeneratedTypes() {
        localStorage.setItem(LOCAL_STORAGE_TYPES_KEY, JSON.stringify(Array.from(generatedCardTypes)));
        console.log('Tipos de cartão gerados salvos no localStorage:', Array.from(generatedCardTypes));
    }

    /**
     * Carrega os tipos de cartão já gerados do localStorage.
     * @returns {Array<string>|null} Um array com os tipos gerados ou null.
     */
    function loadGeneratedTypes() {
        const saved = localStorage.getItem(LOCAL_STORAGE_TYPES_KEY);
        return saved ? JSON.parse(saved) : null;
    }

    /**
     * Limpa todos os dados do cartão e tipos gerados do localStorage e da memória.
     */
    function clearCardData() {
        cardData = {};
        generatedCardTypes.clear();
        localStorage.removeItem(LOCAL_STORAGE_CARD_KEY);
        localStorage.removeItem(LOCAL_STORAGE_TYPES_KEY);
        console.log('Dados do cartão e tipos gerados limpos.');
    }

    /**
     * Exibe o cartão salvo e suas informações.
     * @param {object} data - Os dados do cartão a serem exibidos.
     */
    function displaySavedCard(data) {
        const displayCardNumberElement = document.getElementById('displayCardNumber');
        const displayCardNameElement = document.getElementById('displayCardName');
        const displayCardExpiryElement = document.getElementById('displayCardExpiry');
        const displayCardTypeElement = document.getElementById('displayCardType');
        const cardFrontFaceElement = document.getElementById('cardFrontFace');
        const infoNameElement = document.getElementById('infoName');
        const infoCpfElement = document.getElementById('infoCpf');
        const infoNumberElement = document.getElementById('infoNumber');
        const infoExpiryElement = document.getElementById('infoExpiry');
        const infoTypeElement = document.getElementById('infoType');

        // Atualiza os elementos visuais do cartão
        if (displayCardNumberElement) displayCardNumberElement.textContent = data.number;
        // Aplica a formatação do nome ao exibir dados salvos
        if (displayCardNameElement) displayCardNameElement.textContent = formatName(data.name);
        if (displayCardExpiryElement) displayCardExpiryElement.textContent = data.expiry;
        if (displayCardTypeElement) displayCardTypeElement.textContent = data.type; // Atualiza o tipo no cartão visual

        // Adiciona a classe do tipo de cartão para estilização CSS (cores e padrões)
        if (cardFrontFaceElement) {
            cardFrontFaceElement.className = 'card-face card-front'; // Reseta classes
            cardFrontFaceElement.classList.add(data.type.toLowerCase()); // Adiciona a nova classe
        }

        // Preenche as informações detalhadas no painel abaixo do cartão
        if (infoNameElement) infoNameElement.textContent = formatName(data.name); // Aplica a formatação também aqui
        if (infoCpfElement) infoCpfElement.textContent = data.cpf;
        if (infoNumberElement) infoNumberElement.textContent = data.number;
        if (infoExpiryElement) infoExpiryElement.textContent = data.expiry;
        if (infoTypeElement) infoTypeElement.textContent = `Callisto ${data.type}`; // Atualiza o tipo na info detalhada

        // Oculta o formulário e mostra o cartão e as ações
        setTimeout(() => {
            hideFormShowCard();
        }, 0); // Pequeno atraso para garantir que o DOM esteja pronto

        // Adiciona o botão "Voltar" após o cartão visual, se ainda não existir:
        const cardActions = document.getElementById('cardActions');
        if (cardActions && !document.getElementById('btn-voltar-cartao')) {
            const voltarBtn = document.createElement('button');
            voltarBtn.id = 'btn-voltar-cartao';
            voltarBtn.className = 'btn-voltar';
            voltarBtn.textContent = 'Voltar';
            voltarBtn.style.marginTop = '2rem';
            voltarBtn.style.background = '#ccc';
            voltarBtn.style.color = '#260A04';
            voltarBtn.style.border = 'none';
            voltarBtn.style.borderRadius = '6px';
            voltarBtn.style.padding = '0.6rem 2rem';
            voltarBtn.style.cursor = 'pointer';
            // ação do botão voltar
            voltarBtn.onclick = function() {
                showFormHideCard(); // ou a função que retorna ao formulário
            };
            cardActions.appendChild(voltarBtn);
        }
    }

    /**
     * Oculta o formulário e exibe o cartão, ações e informações detalhadas.
     */
    function hideFormShowCard() {
        const container = document.getElementById('mainContainer');
        const formSection = document.getElementById('formSection');
        const cardActions = document.getElementById('cardActions');
        const cardInfoDisplay = document.getElementById('cardInfoDisplay');
        const creditCard = document.getElementById('creditCard');
        const cardAdvantagesDisplay = document.getElementById('cardAdvantagesDisplay');

        if (container) container.classList.add('card-only');
        if (formSection) formSection.style.display = 'none'; // Oculta o formulário
        if (cardActions) cardActions.classList.add('visible');
        if (cardInfoDisplay) cardInfoDisplay.classList.add('visible');
        if (creditCard) creditCard.classList.add('expanded');
        if (cardAdvantagesDisplay) cardAdvantagesDisplay.style.display = 'none'; // Oculta as vantagens ao mostrar o cartão gerado
    }

    /**
     * Exibe o formulário e oculta o cartão, ações e informações detalhadas, resetando o formulário.
     */
    function showFormHideCard() {
        const container = document.getElementById('mainContainer');
        const formSection = document.getElementById('formSection');
        const cardActions = document.getElementById('cardActions');
        const cardInfoDisplay = document.getElementById('cardInfoDisplay');
        const creditCard = document.getElementById('creditCard');

        setTimeout(() => {
            if (container) container.classList.remove('card-only');
            if (formSection) formSection.style.display = 'block'; // Mostra o formulário
            if (cardActions) cardActions.classList.remove('visible');
            if (cardInfoDisplay) cardInfoDisplay.classList.remove('visible');
            if (creditCard) creditCard.classList.remove('expanded');

            // Reseta o formulário e os displays do cartão para o estado inicial
            const cardFormElement = document.getElementById('cardForm');
            if (cardFormElement) cardFormElement.reset();

            const displayCardNameElement = document.getElementById('displayCardName');
            if (displayCardNameElement) displayCardNameElement.textContent = 'SEU NOME AQUI';

            const displayCardNumberElement = document.getElementById('displayCardNumber');
            if (displayCardNumberElement) displayCardNumberElement.textContent = '0000 0000 0000 0000';

            const displayCardExpiryElement = document.getElementById('displayCardExpiry');
            if (displayCardExpiryElement) displayCardExpiryElement.textContent = 'MM/AA';

            const displayCardTypeElement = document.getElementById('displayCardType');
            if (displayCardTypeElement) displayCardTypeElement.textContent = 'PLATINUM';

            const cardFrontFaceElement = document.getElementById('cardFrontFace');
            if (cardFrontFaceElement) cardFrontFaceElement.className = 'card-face card-front platinum'; // Reseta para Platinum

            updateCardTypeOptions(); // Atualiza as opções do dropdown para permitir novas gerações

            // Garante que o display de vantagens esteja visível e atualizado para o tipo padrão
            const cardTypeSelect = document.getElementById('cardType');
            if (cardTypeSelect) {
                updateCardDisplayAndAdvantages(cardTypeSelect.value);
            }
        }, 0);
    }

    /**
     * Atualiza as opções do dropdown de tipo de cartão, desabilitando os tipos já gerados.
     * Também controla o estado do botão "Gerar Cartão".
     */
    function updateCardTypeOptions() {
        const cardTypeSelect = document.getElementById('cardType');
        if (!cardTypeSelect) return;

        const options = cardTypeSelect.options;
        let firstAvailableOptionValue = null;

        // Ativa todas as opções primeiro e encontra a primeira disponível
        for (let i = 0; i < options.length; i++) {
            options[i].disabled = false;
            options[i].style.display = 'block'; // Garante que estejam visíveis
            if (firstAvailableOptionValue === null && !generatedCardTypes.has(options[i].value)) {
                firstAvailableOptionValue = options[i].value;
            }
        }

        // Desativa e oculta as opções já geradas
        generatedCardTypes.forEach(type => {
            for (let i = 0; i < options.length; i++) {
                if (options[i].value === type) {
                    options[i].disabled = true;
                    options[i].style.display = 'none'; // Oculta opções desativadas
                }
            }
        });

        // Se a opção atualmente selecionada estiver desabilitada, seleciona a primeira disponível
        if (cardTypeSelect.selectedOptions[0].disabled) {
            if (firstAvailableOptionValue) {
                cardTypeSelect.value = firstAvailableOptionValue;
            } else {
                // Se não houver opções disponíveis, seleciona a primeira (que estará desabilitada)
                cardTypeSelect.selectedIndex = 0;
            }
        }

        // Controla o estado do botão de geração
        const generateBtn = document.querySelector('.generate-btn');
        if (generatedCardTypes.size === options.length) {
            if (generateBtn) {
                generateBtn.disabled = true;
                generateBtn.textContent = 'Todos os cartões gerados!';
            }
        } else {
            if (generateBtn) {
                generateBtn.disabled = false;
                generateBtn.textContent = 'Gerar Cartão';
            }
        }
    }

    /**
     * Atualiza o visual do cartão e o display de vantagens com base no tipo selecionado.
     * @param {string} selectedType - O tipo de cartão selecionado (PLATINUM, GOLD, BLACK).
     */
    function updateCardDisplayAndAdvantages(selectedType) {
        const cardFrontFace = document.getElementById('cardFrontFace');
        const displayCardType = document.getElementById('displayCardType');
        const cardAdvantagesDisplay = document.getElementById('cardAdvantagesDisplay');

        if (cardFrontFace) {
            cardFrontFace.className = 'card-face card-front'; // Reseta classes
            cardFrontFace.classList.add(selectedType.toLowerCase()); // Adiciona a nova classe de estilo
        }
        if (displayCardType) {
            displayCardType.textContent = selectedType; // Atualiza o texto do tipo no cartão
        }

        // Define as vantagens com base no tipo de cartão
        let advantagesText = '';
        switch (selectedType) {
            case 'PLATINUM':
                advantagesText = `
                    <h3>Vantagens Callisto Platinum (Básico):</h3>
                    <ul>
                        <li>Anuidade: R$ 19,90/mês (gratuita no 1º ano).</li>
                        <li>Pontos: 1 ponto a cada R$ 5 gastos.</li>
                        <li>Descontos em parceiros selecionados.</li>
                        <li>Atendimento digital 24h.</li>
                    </ul>
                    <p class="credit-limit"><strong>Limite de Crédito:</strong> R$ 300 - R$ 1.500</p>
                `;
                break;
            case 'GOLD':
                advantagesText = `
                    <h3>Vantagens Callisto Gold (Médio):</h3>
                    <ul>
                        <li>Anuidade: R$ 9,90/mês (gratuita se gastar +R$1.000/mês).</li>
                        <li>Pontos: 1 ponto a cada R$ 3 gastos.</li>
                        <li>Acesso a programas de recompensa exclusivos.</li>
                        <li>Seguro viagem básico e proteção de compra.</li>
                    </ul>
                    <p class="credit-limit"><strong>Limite de Crédito:</strong> R$ 1.501 - R$ 10.000</p>
                `;
                break;
            case 'BLACK':
                advantagesText = `
                    <h3>Vantagens Callisto Black (Premium):</h3>
                    <ul>
                        <li>Anuidade gratuita para sempre.</li>
                        <li>Cashback de 2% em todas as compras.</li>
                        <li>Acesso ilimitado a salas VIP globais.</li>
                        <li>Concierge exclusivo e experiências de luxo.</li>
                        <li>Seguro de vida e saúde abrangente.</li>
                        <li>Pontos em dobro em todas as compras.</li>
                    </ul>
                    <p class="credit-limit"><strong>Limite de Crédito:</strong> A partir de R$ 10.001 (personalizado)</p>
                `;
                break;
            default:
                advantagesText = 'Selecione um tipo de cartão para ver as vantagens.';
        }

        if (cardAdvantagesDisplay) {
            cardAdvantagesDisplay.innerHTML = advantagesText;
            cardAdvantagesDisplay.style.display = 'block'; // Garante que o display de vantagens esteja visível
        }
    }

    // Event listener para o formulário de geração de cartão
    const cardFormElement = document.getElementById('cardForm');
    if (cardFormElement) {
        cardFormElement.addEventListener('submit', function(e) {
            e.preventDefault(); // Impede o envio padrão do formulário

            const cardTypeElement = document.getElementById('cardType');
            const nameElement = document.getElementById('cardName');
            const cpfElement = document.getElementById('cardCpf');

            const cardType = cardTypeElement ? cardTypeElement.value : '';
            const name = nameElement ? nameElement.value.trim().toUpperCase() : '';
            const cpf = cpfElement ? cpfElement.value : '';

            // Validação básica dos campos
            if (!name || !cpf || !cardType) {
                showMessage('Por favor, preencha todos os campos e selecione o tipo de cartão!', 'error');
                return;
            }

            // Validação de CPF mais robusta
            if (!validateCPF(cpf)) {
                showMessage('CPF inválido. Por favor, verifique o número.', 'error');
                return;
            }

            // Verifica se o tipo de cartão já foi gerado
            if (generatedCardTypes.has(cardType)) {
                showMessage(`O cartão ${cardType} já foi gerado. Por favor, escolha outro tipo.`, 'error');
                return;
            }

            // Gera o número do cartão e a data de validade
            const generatedCardNumber = generateCardNumber();
            const generatedExpiry = generateExpiryDate();

            // Salva os dados do novo cartão
            const newCardData = {
                name: name,
                cpf: cpf,
                number: generatedCardNumber,
                expiry: generatedExpiry,
                type: cardType,
                createdAt: new Date().toISOString()
            };

            saveCardData(newCardData); // Salva no localStorage
            generatedCardTypes.add(cardType); // Adiciona o tipo gerado ao conjunto
            saveGeneratedTypes(); // Salva os tipos gerados no localStorage

            // Atualiza os elementos visuais do cartão com os novos dados
            const displayCardNumberElement = document.getElementById('displayCardNumber');
            if (displayCardNumberElement) displayCardNumberElement.textContent = generatedCardNumber;

            const displayCardExpiryElement = document.getElementById('displayCardExpiry');
            if (displayCardExpiryElement) displayCardExpiryElement.textContent = generatedExpiry;

            updateCardDisplayAndAdvantages(cardType); // Atualiza o display e as vantagens

            // Animação de geração do cartão
            const cardInner = document.querySelector('.card-inner');
            const generateButton = document.querySelector('.generate-btn');

            if (cardInner && generateButton) {
                cardInner.style.animation = 'none'; // Remove a animação flutuante temporariamente
                cardInner.style.transform = 'rotateY(360deg) scale(1.1)'; // Efeito de rotação e escala

                setTimeout(() => {
                    cardInner.style.transform = 'rotateY(0deg) scale(1)'; // Volta ao normal
                    cardInner.style.animation = 'cardFloat 6s ease-in-out infinite'; // Restaura a animação flutuante

                    const originalText = generateButton.textContent;
                    generateButton.textContent = 'Cartão Gerado com Sucesso!';
                    generateButton.style.background = 'linear-gradient(135deg, var(--secondary-green-light) 0%, var(--secondary-green-dark) 100%)';

                    setTimeout(() => {
                        generateButton.textContent = originalText;
                        generateButton.style.background = 'linear-gradient(135deg, var(--primary-orange-light) 0%, var(--primary-orange-medium) 100%)';

                        // Mostra o cartão criado e oculta o formulário
                        displaySavedCard(newCardData);
                        updateCardTypeOptions(); // Atualiza as opções do dropdown após gerar
                    }, 2000); // Tempo para o botão voltar ao normal
                }, 500); // Tempo para a animação de rotação
            }
        });
    }

    // Event listener para a mudança do tipo de cartão no dropdown
    const cardTypeSelectElement = document.getElementById('cardType');
    if (cardTypeSelectElement) {
        cardTypeSelectElement.addEventListener('change', function() {
            const selectedType = this.value;
            updateCardDisplayAndAdvantages(selectedType); // Atualiza o display e as vantagens
        });
    }

    // Event listener para o botão "Novo Cartão"
    const newCardBtnElement = document.getElementById('newCardBtn');
    if (newCardBtnElement) {
        newCardBtnElement.addEventListener('click', function() {
            showFormHideCard(); // Volta para o formulário
        });
    }

    // Event listener para o botão "Limpar Dados"
    const clearCardBtnElement = document.getElementById('clearCardBtn');
    if (clearCardBtnElement) {
        clearCardBtnElement.addEventListener('click', function() {
            showConfirm('Tem certeza que deseja limpar todos os dados do cartão?', (result) => {
                if (result) {
                    clearCardData(); // Limpa os dados
                    showFormHideCard(); // Volta para o formulário
                    showMessage('Dados do cartão limpos com sucesso!', 'success');
                }
            });
        });
    }

    // Efeito de hover no cartão (apenas quando não está expandido)
    const creditCardElement = document.querySelector('.credit-card');
    if (creditCardElement) {
        creditCardElement.addEventListener('mouseenter', function() {
            const cardInnerElement = this.querySelector('.card-inner');
            // Aplica o efeito apenas se o cartão não estiver no estado "expandido" (após geração)
            if (cardInnerElement && !this.classList.contains('expanded')) {
                cardInnerElement.style.transform = 'rotateY(10deg) scale(1.05)';
            }
        });
        creditCardElement.addEventListener('mouseleave', function() {
            const cardInnerElement = this.querySelector('.card-inner');
            if (cardInnerElement && !this.classList.contains('expanded')) {
                cardInnerElement.style.transform = 'rotateY(0deg) scale(1)';
            }
        });
    }

    /**
     * Renderiza o cartão visual com base nos dados do cartão.
     * @param {object} cartao - O objeto com os dados do cartão.
     * @returns {string} O HTML do cartão renderizado.
     */
    function renderCartaoVisual(cartao) {
        return `
        <div class="credit-card card-animation" style="max-width:340px;">
            <div class="card-inner">
                <div class="card-face card-front ${cartao.tipo.toLowerCase()}">
                    <div class="card-header">
                        <span class="bank-name">CALLISTO BANK</span>
                        <span class="card-type">${cartao.tipo.charAt(0).toUpperCase() + cartao.tipo.slice(1)}</span>
                    </div>
                    <div class="chip">
                        <svg viewBox="0 0 50 40">
                            <rect x="5" y="5" width="40" height="30" rx="6" fill="#e0e0e0" stroke="#bdbdbd" stroke-width="2"/>
                            <rect x="15" y="10" width="20" height="20" rx="3" fill="#bdbdbd" />
                        </svg>
                    </div>
                    <div class="card-number">${cartao.numero}</div>
                    <div class="card-info">
                        <div class="card-field">
                            <div class="card-label">Nome</div>
                            <div class="card-value">${cartao.nome}</div>
                        </div>
                        <div class="card-field">
                            <div class="card-label">Validade</div>
                            <div class="card-value">${cartao.validade}</div>
                        </div>
                        <div class="card-field">
                            <div class="card-label">CVV</div>
                            <div class="card-value">${cartao.cvv}</div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        `;
    }
})(); // Fim da IIFE
