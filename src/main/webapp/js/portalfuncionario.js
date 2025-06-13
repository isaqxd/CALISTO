document.addEventListener('DOMContentLoaded', function() {
    const mainContent = document.getElementById('main-content');

    // Utilitário para menu ativo
    function setActiveMenu(menuId) {
        document.querySelectorAll('nav a').forEach(link => link.classList.remove('active'));
        const activeLink = document.getElementById(menuId);
        if (activeLink) activeLink.classList.add('active');
    }

    // Atualiza ano do rodapé
    document.getElementById('currentYear').textContent = new Date().getFullYear();

    // Renderiza tela inicial
    function renderHome() {
        setActiveMenu('menu-home');
        mainContent.innerHTML = `
            <h2>Bem-vindo ao Portal do Cliente</h2>
            <p>Acesse seus dados, consulte extrato e muito mais.</p>
        `;
    }

    // Renderiza dados do cliente (exemplo)
    function renderDados() {
        setActiveMenu('menu-dados');
        // Simulação de dados
        const cliente = JSON.parse(localStorage.getItem('cliente')) || {
            nome: "João da Silva",
            cpf: "123.456.789-00",
            email: "joao@email.com",
            telefone: "11999999999"
        };
        mainContent.innerHTML = `
            <h2>Meus Dados</h2>
            <ul>
                <li><b>Nome:</b> ${cliente.nome}</li>
                <li><b>CPF:</b> ${cliente.cpf}</li>
                <li><b>Email:</b> ${cliente.email}</li>
                <li><b>Telefone:</b> ${cliente.telefone}</li>
            </ul>
        `;
    }

    // Renderiza extrato (exemplo)
    function renderExtrato() {
        setActiveMenu('menu-extrato');
        // Simulação de extrato
        const extrato = JSON.parse(localStorage.getItem('extrato')) || [
            { data: "2025-06-01", desc: "Depósito", valor: 1000.00 },
            { data: "2025-06-05", desc: "Saque", valor: -200.00 }
        ];
        mainContent.innerHTML = `
            <h2>Extrato</h2>
            <table style="width:100%;border-collapse:collapse;">
                <thead>
                    <tr>
                        <th style="border-bottom:1px solid #ccc;">Data</th>
                        <th style="border-bottom:1px solid #ccc;">Descrição</th>
                        <th style="border-bottom:1px solid #ccc;">Valor</th>
                    </tr>
                </thead>
                <tbody>
                    ${
                        extrato.length === 0
                        ? `<tr><td colspan="3" style="text-align:center;">Nenhuma movimentação.</td></tr>`
                        : extrato.map(t => `
                            <tr>
                                <td>${t.data}</td>
                                <td>${t.desc}</td>
                                <td style="color:${t.valor < 0 ? '#bf2424' : '#1976d2'};">
                                    R$ ${t.valor.toFixed(2)}
                                </td>
                            </tr>
                        `).join('')
                    }
                </tbody>
            </table>
        `;
    }

    // Sair
    function sair() {
        setActiveMenu('menu-sair');
        localStorage.removeItem('cliente');
        localStorage.removeItem('extrato');
        mainContent.innerHTML = `
            <h2>Você saiu do portal.</h2>
            <p>Até logo!</p>
        `;
    }

    // Handlers de menu
    document.getElementById('menu-home').onclick = function(e) { e.preventDefault(); renderHome(); };
    document.getElementById('menu-dados').onclick = function(e) { e.preventDefault(); renderDados(); };
    document.getElementById('menu-extrato').onclick = function(e) { e.preventDefault(); renderExtrato(); };
    document.getElementById('menu-sair').onclick = function(e) { e.preventDefault(); sair(); };

    // Inicialização
    renderHome();

    // Bolinhas animadas de fundo
    const bubbleConfigs = [
        { size: 120, left: '10%', top: '20%', color: '#F2B950', anim: 'float1 12s infinite alternate' },
        { size: 80,  left: '80%', top: '30%', color: '#BF7A24', anim: 'float2 14s infinite alternate' },
        { size: 100, left: '50%', top: '80%', color: '#8C480D', anim: 'float3 16s infinite alternate' },
        { size: 60,  left: '70%', top: '60%', color: '#F2B950', anim: 'float4 18s infinite alternate' },
        { size: 90,  left: '30%', top: '70%', color: '#BF7A24', anim: 'float5 15s infinite alternate' },
        { size: 70,  left: '20%', top: '50%', color: '#F2B950', anim: 'float6 13s infinite alternate' },
        { size: 50,  left: '60%', top: '15%', color: '#8C480D', anim: 'float7 17s infinite alternate' },
        { size: 110, left: '40%', top: '40%', color: '#BF7A24', anim: 'float8 19s infinite alternate' }
    ];

    const backgroundLayer = document.querySelector('.background-layer');
    if (backgroundLayer) {
        bubbleConfigs.forEach(cfg => {
            const bubble = document.createElement('div');
            bubble.className = 'bubble';
            bubble.style.width = `${cfg.size}px`;
            bubble.style.height = `${cfg.size}px`;
            bubble.style.left = cfg.left;
            bubble.style.top = cfg.top;
            bubble.style.background = cfg.color;
            bubble.style.opacity = '0.18';
            bubble.style.position = 'absolute';
            bubble.style.borderRadius = '50%';
            bubble.style.pointerEvents = 'none';
            bubble.style.animation = cfg.anim;
            backgroundLayer.appendChild(bubble);
        });
    }
});