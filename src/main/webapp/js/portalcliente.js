// Adiciona um "ouvinte" que espera todo o HTML da página ser carregado antes de executar o código.
document.addEventListener('DOMContentLoaded', function() {
    
    // --- MAPEAMENTO DOS ELEMENTOS PRINCIPAIS --- //
    const mainContent = document.getElementById('main-content');
    const saldoEl = document.getElementById('saldoValor');
    const toggleBalanceBtn = document.getElementById('toggleBalanceBtn');
    
    // --- ESTADO DA APLICAÇÃO --- //
    let saldoVisivel = true;

    // Simula os dados que viriam de um banco de dados ou API.
    // ADICIONADO: CPF ao objeto do cliente para ser exibido.
    const dados = {
        cliente: { 
            nome: "Cliente Exemplo da Silva", 
            agencia: "0001", 
            conta: "12345-6",
            cpf: "12345678900" // CPF de exemplo (somente números)
        },
        extrato: [
            { data: '12/06/2025', desc: 'Depósito Recebido', valor: 800.00 },
            { data: '11/06/2025', desc: 'Supermercado', valor: -350.75 },
            { data: '10/06/2025', desc: 'Pagamento Fatura Cartão', valor: -1500.50 },
            { data: '10/06/2025', desc: 'Salário', valor: 5200.00 }
        ]
    };

    // --- ANIMAÇÃO DE FUNDO --- //
    const criarAnimacaoDeFundo = () => {
        const backgroundLayer = document.querySelector('.background-layer');
        if (!backgroundLayer) return;
        const bubbleConfigs = [
            { size: 120, left: '10%', top: '20%', color: '#F2B950', anim: 'float1 12s infinite alternate' }, { size: 80,  left: '80%', top: '30%', color: '#BF7A24', anim: 'float2 14s infinite alternate' }, { size: 100, left: '50%', top: '80%', color: '#8C480D', anim: 'float3 16s infinite alternate' }, { size: 60,  left: '70%', top: '60%', color: '#F2B950', anim: 'float4 18s infinite alternate' }, { size: 90,  left: '30%', top: '70%', color: '#BF7A24', anim: 'float5 15s infinite alternate' }, { size: 70,  left: '20%', top: '50%', color: '#F2B950', anim: 'float6 13s infinite alternate' }, { size: 50,  left: '60%', top: '15%', color: '#8C480D', anim: 'float7 17s infinite alternate' }, { size: 110, left: '40%', top: '40%', color: '#BF7A24', anim: 'float8 19s infinite alternate' }
        ];
        bubbleConfigs.forEach(cfg => {
            const bubble = document.createElement('div');
            bubble.className = 'bubble';
            Object.assign(bubble.style, { width: `${cfg.size}px`, height: `${cfg.size}px`, left: cfg.left, top: cfg.top, background: cfg.color, animation: cfg.anim });
            backgroundLayer.appendChild(bubble);
        });
    };

    // --- FUNÇÕES UTILITÁRIAS E DE MÁSCARA --- //
    const unmask = (value) => value ? value.replace(/\D/g, '') : '';
    const maskCurrency = (value) => { if (!value) return ''; let floatValue = parseFloat(unmask(value).padStart(3, '0')) / 100; return floatValue.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' }); };
    const maskCPF = (value) => unmask(value).replace(/(\d{3})(\d)/, '$1.$2').replace(/(\d{3})(\d)/, '$1.$2').replace(/(\d{3})(\d{1,2})/, '$1-$2').substring(0, 14);
    const maskPhone = (value) => unmask(value).replace(/(\d{2})(\d)/, '($1) $2').replace(/(\d{5})(\d)/, '$1-$2').substring(0, 15);
    const masks = { currency: maskCurrency, cpf: maskCPF, phone: maskPhone };
    
    // --- LÓGICA DE VISIBILidade DO SALDO --- //
    const calcularSaldo = () => dados.extrato.reduce((acc, transacao) => acc + transacao.valor, 0);
    const atualizarDisplaySaldo = () => {
        if (saldoVisivel) {
            saldoEl.textContent = calcularSaldo().toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
            toggleBalanceBtn.innerHTML = '<i class="material-icons">visibility</i>';
        } else {
            saldoEl.textContent = 'R$ ●●●●,●●';
            toggleBalanceBtn.innerHTML = '<i class="material-icons">visibility_off</i>';
        }
    };
    const toggleSaldo = () => {
        saldoVisivel = !saldoVisivel;
        atualizarDisplaySaldo();
    };

    // --- FUNÇÕES DE RENDERIZAÇÃO --- //
    const setActiveQuickAccess = (activeId) => { document.querySelectorAll('.quick-access-item').forEach(button => button.classList.remove('active')); document.getElementById(activeId)?.classList.add('active'); };
    
    const renderExtrato = () => { setActiveQuickAccess('btn-extrato'); const saldo = calcularSaldo(); mainContent.innerHTML = `<div class="extrato-header"><h2>Extrato da Conta</h2><div class="saldo-info"><strong>Saldo Atual:</strong> <span style="color: ${saldo >= 0 ? '#4CAF50' : '#f44336'};">${saldo.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' })}</span></div></div><table id="tabela-extrato"><thead><tr><th>Data</th><th>Descrição</th><th style="text-align:right;">Valor (R$)</th></tr></thead><tbody>${dados.extrato.length === 0 ? `<tr><td colspan="3" style="text-align:center; padding: 2rem 0;">Nenhuma movimentação encontrada.</td></tr>` : dados.extrato.map(t => `<tr><td>${t.data}</td><td>${t.desc}</td><td style="text-align:right; color:${t.valor < 0 ? '#f44336' : '#4CAF50'}; font-weight:500;">${t.valor.toLocaleString('pt-BR', { minimumFractionDigits: 2 })}</td></tr>`).join('')}</tbody></table><div class="export-btns"><button id="btn-export-pdf"><i class="material-icons" style="font-size:1em; margin-right: 0.5em;">picture_as_pdf</i>Exportar PDF</button><button id="btn-export-excel"><i class="material-icons" style="font-size:1em; margin-right: 0.5em;">table_chart</i>Exportar Excel</button></div>`; document.getElementById('btn-export-pdf')?.addEventListener('click', exportarParaPDF); document.getElementById('btn-export-excel')?.addEventListener('click', exportarParaExcel); };
    const exportarParaPDF = () => { const { jsPDF } = window.jspdf; const doc = new jsPDF(); doc.setFontSize(18); doc.text("Extrato da Conta - Calisto Bank", 14, 22); doc.setFontSize(11); doc.setTextColor(100); doc.text(`Cliente: ${dados.cliente.nome}`, 14, 30); doc.text(`Data de Emissão: ${new Date().toLocaleDateString('pt-BR')}`, 14, 36); const head = [['Data', 'Descrição', 'Valor (R$)']]; const body = dados.extrato.map(t => [t.data, t.desc, t.valor.toLocaleString('pt-BR', { minimumFractionDigits: 2 })]); doc.autoTable({ head: head, body: body, startY: 50, theme: 'striped', headStyles: { fillColor: [38, 10, 4] }, didDrawCell: (data) => { if (data.column.index === 2 && data.cell.text[0].includes('-')) { data.cell.styles.textColor = [244, 67, 54]; } } }); doc.save(`extrato_${dados.cliente.nome.split(' ')[0]}_${new Date().toISOString().slice(0,10)}.pdf`); };
    const exportarParaExcel = () => { const dadosFormatados = dados.extrato.map(t => ({ 'Data': t.data, 'Descrição': t.desc, 'Valor': t.valor })); const ws = XLSX.utils.json_to_sheet(dadosFormatados); ws['!cols'] = [{wch:12}, {wch:40}, {wch:15}]; dadosFormatados.forEach((_row, index) => { const cellRef = XLSX.utils.encode_cell({c: 2, r: index + 1}); ws[cellRef].z = 'R$ #,##0.00'; }); const wb = XLSX.utils.book_new(); XLSX.utils.book_append_sheet(wb, ws, "Extrato"); XLSX.writeFile(wb, "Extrato_Calisto_Bank.xlsx"); };
    const renderCartoes = () => { setActiveQuickAccess('btn-cartoes'); mainContent.innerHTML = `<h2>Meus Cartões</h2><p>Aqui você poderá visualizar e gerenciar seus cartões.</p>`; };
    const renderTransferir = () => { setActiveQuickAccess('btn-transferir'); mainContent.innerHTML = `<h2>Transferir</h2><form id="form-transfer"><label for="valor-transf">Valor:</label><input type="text" id="valor-transf" placeholder="R$ 0,00" data-mask="currency" autocomplete="off"><button type="submit">Transferir</button></form>`; };
    const renderDepositar = () => { setActiveQuickAccess('btn-depositar'); mainContent.innerHTML = `<h2>Depositar</h2><form id="form-deposit"><label for="dep-valor">Valor:</label><input type="text" id="dep-valor" placeholder="R$ 0,00" data-mask="currency" autocomplete="off"><button type="submit">Gerar Boleto</button></form>`; };
    const renderSacar = () => { setActiveQuickAccess('btn-sacar'); mainContent.innerHTML = `<h2>Saque</h2><form id="form-withdraw"><label for="saq-valor">Valor:</label><input type="text" id="saq-valor" placeholder="R$ 0,00" data-mask="currency" autocomplete="off"><button type="submit">Sacar</button></form>`; };

    /**
     * ATUALIZADO: Função para renderizar os dados do cliente com os campos solicitados.
     */
    const renderMeusDados = () => {
        setActiveQuickAccess('btn-meus-dados');

        // Formata os dados para exibição
        const nomeCliente = dados.cliente.nome;
        const cpfMascarado = maskCPF(dados.cliente.cpf);
        const numeroConta = `${dados.cliente.agencia} / ${dados.cliente.conta}`;

        mainContent.innerHTML = `
            <h2>Meus Dados</h2>
            <p>Estas são suas informações de cadastro. Por segurança, elas não podem ser alteradas por aqui.</p>
            <form id="form-my-data" style="margin-top: 1.5rem;">
                
                <label for="user-name">Nome Completo:</label>
                <input type="text" id="user-name" value="${nomeCliente}" readonly>

                <label for="user-cpf">CPF:</label>
                <input type="text" id="user-cpf" value="${cpfMascarado}" readonly>

                <label for="user-account">Agência / Conta:</label>
                <input type="text" id="user-account" value="${numeroConta}" readonly>
                
            </form>
        `;
    };
    
    // --- EVENT LISTENERS --- //
    mainContent.addEventListener('input', (e) => { const input = e.target; const maskName = input.dataset.mask; if (maskName && masks[maskName]) { input.value = masks[maskName](input.value); } });
    mainContent.addEventListener('submit', (e) => { e.preventDefault(); alert(`Formulário "${e.target.id}" enviado (simulação).`); });
    
    document.getElementById('btn-extrato')?.addEventListener('click', (e) => { e.preventDefault(); renderExtrato(); });
    document.getElementById('btn-transferir')?.addEventListener('click', (e) => { e.preventDefault(); renderTransferir(); });
    document.getElementById('btn-depositar')?.addEventListener('click', (e) => { e.preventDefault(); renderDepositar(); });
    document.getElementById('btn-sacar')?.addEventListener('click', (e) => { e.preventDefault(); renderSacar(); });
    document.getElementById('btn-cartoes')?.addEventListener('click', (e) => { e.preventDefault(); renderCartoes(); });
    document.getElementById('btn-meus-dados')?.addEventListener('click', (e) => { e.preventDefault(); renderMeusDados(); });
    
    toggleBalanceBtn?.addEventListener('click', toggleSaldo);

    // --- INICIALIZAÇÃO DA PÁGINA --- //
    const init = () => {
        criarAnimacaoDeFundo();
        atualizarDisplaySaldo();
        renderExtrato();
    };
    
    init();
});