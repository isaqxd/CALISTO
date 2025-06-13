document.addEventListener('DOMContentLoaded', function() {
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

    // Máscara CPF
    function maskCPF(value) {
        value = value.replace(/\D/g, '').slice(0, 11);
        if (value.length > 9) return value.replace(/(\d{3})(\d{3})(\d{3})(\d{1,2})/, "$1.$2.$3-$4");
        if (value.length > 6) return value.replace(/(\d{3})(\d{3})(\d{1,3})/, "$1.$2.$3");
        if (value.length > 3) return value.replace(/(\d{3})(\d{1,3})/, "$1.$2");
        return value;
    }

    // Máscara telefone
    function maskTelefone(value) {
        value = value.replace(/\D/g, '').slice(0, 11);
        if (value.length > 10) return value.replace(/(\d{2})(\d{5})(\d{4})/, "($1) $2-$3");
        if (value.length > 6) return value.replace(/(\d{2})(\d{4,5})(\d{0,4})/, "($1) $2-$3");
        if (value.length > 2) return value.replace(/(\d{2})(\d{0,5})/, "($1) $2");
        return value;
    }

    // Máscara valor monetário
    function maskValor(value) {
        value = value.replace(/[^\d]/g, '');
        if (!value) return '';
        value = (parseInt(value, 10) / 100).toFixed(2) + '';
        return value.replace('.', ',').replace(/\B(?=(\d{3})+(?!\d))/g, ".");
    }

    // Renderiza dados do cliente (sem validação)
    function renderDados() {
        setActiveMenu('menu-dados');
        const cliente = JSON.parse(localStorage.getItem('cliente')) || {
            nome: "",
            cpf: "",
            email: "",
            telefone: ""
        };
        mainContent.innerHTML = `
            <h2>Meus Dados</h2>
            <form id="form-dados">
                <label>Nome: <input type="text" id="cli-nome" value="${cliente.nome || ''}"></label>
                <label>CPF: <input type="text" id="cli-cpf" maxlength="14" value="${maskCPF(cliente.cpf || '')}"></label>
                <label>Email: <input type="email" id="cli-email" value="${cliente.email || ''}"></label>
                <label>Telefone: <input type="text" id="cli-telefone" maxlength="15" value="${maskTelefone(cliente.telefone || '')}"></label>
                <button type="submit">Salvar Dados</button>
            </form>
        `;

        document.getElementById('cli-cpf').addEventListener('input', function(e) {
            let val = e.target.value.replace(/\D/g, '');
            e.target.value = maskCPF(val);
        });

        document.getElementById('cli-telefone').addEventListener('input', function(e) {
            let val = e.target.value.replace(/\D/g, '');
            e.target.value = maskTelefone(val);
        });

        document.getElementById('form-dados').onsubmit = function(e) {
            e.preventDefault();
            const nome = document.getElementById('cli-nome').value;
            const cpf = document.getElementById('cli-cpf').value.replace(/\D/g, '');
            const email = document.getElementById('cli-email').value;
            const telefone = document.getElementById('cli-telefone').value.replace(/\D/g, '');
            localStorage.setItem('cliente', JSON.stringify({ nome, cpf, email, telefone }));
            renderDados();
        };
    }

    // Renderiza extrato (sem validação)
    function renderExtrato() {
        setActiveMenu('menu-extrato');
        let extrato = JSON.parse(localStorage.getItem('extrato')) || [];
        let saldo = extrato.reduce((acc, t) => acc + t.valor, 0);

        mainContent.innerHTML = `
            <h2>Extrato</h2>
            <div style="margin-bottom:1rem;">
                <b>Saldo atual:</b> R$ <span id="saldo-atual">${saldo.toFixed(2)}</span>
            </div>
            <div style="display:flex;gap:1rem;flex-wrap:wrap;">
                <form id="form-deposito" style="flex:1;min-width:220px;">
                    <h4>Depósito</h4>
                    <label>Valor: <input type="text" id="dep-valor" maxlength="15" autocomplete="off"></label>
                    <button type="submit">Depositar</button>
                </form>
                <form id="form-saque" style="flex:1;min-width:220px;">
                    <h4>Saque</h4>
                    <label>Valor: <input type="text" id="saq-valor" maxlength="15" autocomplete="off"></label>
                    <button type="submit">Sacar</button>
                </form>
            </div>
            <div class="export-btns">
                <button id="btn-export-pdf">Exportar PDF</button>
                <button id="btn-export-excel">Exportar Excel</button>
            </div>
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

        document.getElementById('dep-valor').addEventListener('input', function(e) {
            let val = e.target.value.replace(/\D/g, '');
            e.target.value = maskValor(val);
        });

        document.getElementById('saq-valor').addEventListener('input', function(e) {
            let val = e.target.value.replace(/\D/g, '');
            e.target.value = maskValor(val);
        });

        document.getElementById('form-deposito').onsubmit = function(e) {
            e.preventDefault();
            let valor = document.getElementById('dep-valor').value.replace(/\D/g, '');
            if (!valor) return;
            valor = parseInt(valor, 10) / 100;
            extrato.push({
                data: new Date().toLocaleDateString(),
                desc: "Depósito",
                valor: valor
            });
            localStorage.setItem('extrato', JSON.stringify(extrato));
            renderExtrato();
        };

        document.getElementById('form-saque').onsubmit = function(e) {
            e.preventDefault();
            let valor = document.getElementById('saq-valor').value.replace(/\D/g, '');
            if (!valor) return;
            valor = parseInt(valor, 10) / 100;
            extrato.push({
                data: new Date().toLocaleDateString(),
                desc: "Saque",
                valor: -valor
            });
            localStorage.setItem('extrato', JSON.stringify(extrato));
            renderExtrato();
        };

        // Exportar PDF via API
        document.getElementById('btn-export-pdf').onclick = function() {
            fetch('/api/exportar-extrato/pdf', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ extrato })
            })
            .then(response => response.blob())
            .then(blob => {
                const url = window.URL.createObjectURL(blob);
                const a = document.createElement('a');
                a.href = url;
                a.download = 'extrato.pdf';
                document.body.appendChild(a);
                a.click();
                a.remove();
            });
        };

        // Exportar Excel via API
        document.getElementById('btn-export-excel').onclick = function() {
            fetch('/api/exportar-extrato/excel', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ extrato })
            })
            .then(response => response.blob())
            .then(blob => {
                const url = window.URL.createObjectURL(blob);
                const a = document.createElement('a');
                a.href = url;
                a.download = 'extrato.xlsx';
                document.body.appendChild(a);
                a.click();
                a.remove();
            });
        };
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
});

function renderPerfilFuncionario() {
    setActiveMenu('menu-perfil-funcionario');
    window.funcionarios = JSON.parse(sessionStorage.getItem('funcionarios')) || [];
    mainContent.innerHTML = `
        <h2>Gerenciamento de Funcionários</h2>
        <div class="conta-box" id="box-cadastrar-func" style="margin-bottom:2rem;">
            <button id="btn-cadastrar-func" style="width:100%;">Cadastrar Novo Funcionário</button>
        </div>
        <div id="form-funcionario-box" style="display:none; margin-top:2rem;">
            <h4>Cadastrar Funcionário</h4>
            <form id="form-funcionario">
                <label>Código: <input type="text" id="func-codigo" value="${gerarCodigoFuncionario()}" readonly></label><br>
                <label>Nome: <input type="text" id="func-nome"></label><br>
                <label>Email: <input type="email" id="func-email"></label><br>
                <label>Cargo:
                    <select id="func-cargo">
                        <option value="">Selecione</option>
                        <option value="Estagiário">Estagiário</option>
                        <option value="Assistente">Assistente</option>
                        <option value="Gerente">Gerente</option>
                    </select>
                </label><br>
                <label>Senha: <input type="password" id="func-senha"></label><br>
                <button type="submit">Salvar Funcionário</button>
                <button type="button" id="btn-cancelar-func">Cancelar</button>
            </form>
        </div>
        <div class="funcionarios-lista">
            <h3>Funcionários</h3>
            <ul>
                ${
                    window.funcionarios.length === 0
                    ? '<li>Nenhum funcionário cadastrado.</li>'
                    : window.funcionarios.map((f, i) =>
                        `<li style="display:flex;align-items:center;gap:1rem;">
                            <span style="flex:1;">
                                <b>${f.codigo}</b> - ${f.nome} (${f.cargo}) - ${f.email}
                            </span>
                            <button class="btn-excluir-func" data-idx="${i}" style="background:#bf2424;color:#fff;border:none;border-radius:6px;padding:0.3rem 0.8rem;cursor:pointer;">Excluir</button>
                        </li>`
                    ).join('')
                }
            </ul>
        </div>
    `;

    document.getElementById('btn-cadastrar-func').onclick = function() {
        document.getElementById('box-cadastrar-func').style.display = 'none';
        document.getElementById('form-funcionario-box').style.display = 'block';
        document.getElementById('func-codigo').value = gerarCodigoFuncionario();
    };

    document.getElementById('btn-cancelar-func').onclick = function() {
        document.getElementById('form-funcionario-box').style.display = 'none';
        document.getElementById('box-cadastrar-func').style.display = 'block';
    };

    document.getElementById('form-funcionario').onsubmit = function(e) {
        e.preventDefault();
        const codigo = document.getElementById('func-codigo').value.trim();
        const nome = document.getElementById('func-nome').value.trim();
        const email = document.getElementById('func-email').value.trim();
        const cargo = document.getElementById('func-cargo').value;
        const senha = document.getElementById('func-senha').value.trim();
        window.funcionarios.push({ nome, email, cargo, senha, codigo });
        sessionStorage.setItem('funcionarios', JSON.stringify(window.funcionarios));
        renderPerfilFuncionario();
    };

    document.querySelectorAll('.btn-excluir-func').forEach(btn => {
        btn.onclick = function() {
            const idx = parseInt(this.getAttribute('data-idx'));
            if (confirm('Deseja realmente excluir este funcionário?')) {
                window.funcionarios.splice(idx, 1);
                sessionStorage.setItem('funcionarios', JSON.stringify(window.funcionarios));
                renderPerfilFuncionario();
            }
        }
    });
}

// Funções para navegação rápida
document.getElementById('btn-sacar').onclick = function(e) {
    e.preventDefault();
    renderExtrato();
    setTimeout(() => {
        document.getElementById('saq-valor').focus();
    }, 100);
};

document.getElementById('btn-depositar').onclick = function(e) {
    e.preventDefault();
    renderExtrato();
    setTimeout(() => {
        document.getElementById('dep-valor').focus();
    }, 100);
};

const navButtons = document.querySelectorAll('.nav-btn');

navButtons.forEach(btn => {
  btn.addEventListener('click', function() {
    navButtons.forEach(b => b.classList.remove('active'));
    this.classList.add('active');
  });
});
