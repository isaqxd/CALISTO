document.addEventListener('DOMContentLoaded', function() {
    const mainContent = document.getElementById('main-content');
    let contas = JSON.parse(sessionStorage.getItem('contas')) || [];
    let transacoes = JSON.parse(sessionStorage.getItem('transacoes')) || [];
    let funcionarios = JSON.parse(sessionStorage.getItem('funcionarios')) || [];

    // Cadastro prévio do gerente Luís Eduardo
    if (!funcionarios.some(f => f.codigo === "FUN0001")) {
        funcionarios.push({
            nome: "Luís Eduardo",
            email: "luis.eduardo@callisto.com",
            cargo: "Gerente",
            senha: "123456",
            codigo: "FUN0001"
        });
        sessionStorage.setItem('funcionarios', JSON.stringify(funcionarios));
    }

    function setActiveMenu(menuId) {
        document.querySelectorAll('.sidebar-nav li a').forEach(link => link.classList.remove('active'));
        const activeLink = document.getElementById(menuId);
        if (activeLink) activeLink.classList.add('active');
    }

    // DASHBOARD
    function renderDashboard() {
        setActiveMenu('menu-dashboard');
        mainContent.innerHTML = `
            <h2>Dashboard</h2>
            <div class="conta-box">
                <p>Bem-vindo ao painel do funcionário do Callisto Bank.</p>
                <ul>
                    <li><b>Total de contas:</b> ${contas.length}</li>
                    <li><b>Total de transações:</b> ${transacoes.length}</li>
                    <li><b>Total de funcionários:</b> ${funcionarios.length}</li>
                </ul>
            </div>
        `;
    }

    // CONTAS DE CLIENTES
    function renderListaContas() {
        setActiveMenu('menu-clientes');
        mainContent.innerHTML = `
            <h2>Contas de Clientes</h2>
            <div class="conta-box" style="margin-bottom:2rem;">
                <button id="btn-criar-conta" style="width:100%;">Criar Conta</button>
            </div>
            <div class="contas-lista">
                <h3>Clientes</h3>
                <ul>
                    ${
                        contas.length === 0
                        ? '<li>Nenhuma conta cadastrada.</li>'
                        : contas.map((c, i) =>
                            `<li style="display:flex;align-items:center;gap:1rem;">
                                <span style="flex:1;">${c.nome} (${c.cpf})</span>
                            </li>`
                        ).join('')
                    }
                </ul>
            </div>
        `;
        sessionStorage.setItem('contas', JSON.stringify(contas));
        document.getElementById('btn-criar-conta').onclick = function() {
            renderFormConta();
        };
    }

    // FORMULÁRIO DE CRIAÇÃO DE CONTA
    function renderFormConta() {
        setActiveMenu('menu-clientes');
        mainContent.innerHTML = `
            <h2>Criar Nova Conta</h2>
            <form id="form-criar-conta" class="conta-box" autocomplete="off">
                <label for="nome-cliente">Nome Completo</label>
                <input type="text" id="nome-cliente" required />

                <label for="cpf-cliente">CPF</label>
                <input type="text" id="cpf-cliente" required maxlength="14"/>

                <label for="senha-cliente">Senha</label>
                <input type="password" id="senha-cliente" required maxlength="6" minlength="6"/>

                <button type="submit" style="margin-top:1rem;width:100%;">Salvar Conta</button>
                <button type="button" id="btn-voltar" style="margin-top:1rem;width:100%;background:#ccc;color:#260A04;">Cancelar</button>
            </form>
        `;
        document.getElementById('form-criar-conta').onsubmit = function(e) {
            e.preventDefault();
            const nome = document.getElementById('nome-cliente').value.trim();
            const cpf = document.getElementById('cpf-cliente').value.trim();
            const senha = document.getElementById('senha-cliente').value.trim();
            if (!nome || !cpf || !senha) {
                alert('Preencha todos os campos.');
                return;
            }
            contas.push({ nome, cpf, senha });
            sessionStorage.setItem('contas', JSON.stringify(contas));
            renderListaContas();
        };
        document.getElementById('btn-voltar').onclick = renderListaContas;
    }

    // TRANSACOES
    function renderTransacoes() {
        setActiveMenu('menu-transacoes');
        mainContent.innerHTML = `
            <h2>Transações</h2>
            <div class="conta-box">
                <p>Funcionalidade de transações em desenvolvimento.</p>
                <ul>
                    ${
                        transacoes.length === 0
                        ? '<li>Nenhuma transação registrada.</li>'
                        : transacoes.map((t, i) =>
                            `<li>${t.tipo} - ${t.valor} - ${t.data}</li>`
                        ).join('')
                    }
                </ul>
            </div>
        `;
    }

    // ANALISE DE CONTA
    function renderAnaliseConta() {
        setActiveMenu('menu-analise');
        mainContent.innerHTML = `
            <h2>Análise da Conta</h2>
            <div class="conta-box">
                <p>Funcionalidade de análise de conta em desenvolvimento.</p>
            </div>
        `;
    }

    // RELATORIOS
    function renderRelatorios() {
        setActiveMenu('menu-relatorios');
        mainContent.innerHTML = `
            <h2>Relatórios</h2>
            <div class="conta-box">
                <p>Funcionalidade de relatórios em desenvolvimento.</p>
            </div>
        `;
    }

    // GERENCIAMENTO DE FUNCIONÁRIOS
    function gerarCodigoFuncionario() {
        if (funcionarios.length === 0) return "FUN0001";
        const codigos = funcionarios.map(f => parseInt(f.codigo.replace("FUN", ""))).filter(n => !isNaN(n));
        const max = codigos.length ? Math.max(...codigos) : 0;
        return "FUN" + String(max + 1).padStart(4, "0");
    }

    function renderPerfilFuncionario() {
        setActiveMenu('menu-perfil-funcionario');
        mainContent.innerHTML = `
            <h2>Gerenciamento de Funcionários</h2>
            <div class="conta-box" style="margin-bottom:2rem;">
                <button id="btn-cadastrar-func" style="width:100%;">Cadastrar Novo Funcionário</button>
            </div>
            <div class="funcionarios-lista">
                <h3>Funcionários</h3>
                <ul>
                    ${
                        funcionarios.length === 0
                        ? '<li>Nenhum funcionário cadastrado.</li>'
                        : funcionarios.map((f, i) =>
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
            <div id="form-funcionario-box" style="display:none; margin-top:2rem;">
                <h4>Cadastrar Funcionário</h4>
                <form id="form-funcionario">
                    <label>Código: <input type="text" id="func-codigo" value="${gerarCodigoFuncionario()}" readonly></label><br>
                    <label>Nome: <input type="text" id="func-nome" required></label><br>
                    <label>Email: <input type="email" id="func-email" required></label><br>
                    <label>Cargo:
                        <select id="func-cargo" required>
                            <option value="">Selecione</option>
                            <option value="Estagiário">Estagiário</option>
                            <option value="Assistente">Assistente</option>
                            <option value="Gerente">Gerente</option>
                        </select>
                    </label><br>
                    <label>Senha: <input type="password" id="func-senha" required></label><br>
                    <button type="submit">Salvar Funcionário</button>
                    <button type="button" id="btn-cancelar-func">Cancelar</button>
                </form>
            </div>
        `;

        document.getElementById('btn-cadastrar-func').onclick = function() {
            document.getElementById('form-funcionario-box').style.display = 'block';
            this.style.display = 'none';
            document.getElementById('func-codigo').value = gerarCodigoFuncionario();
        };

        document.getElementById('btn-cancelar-func').onclick = function() {
            document.getElementById('form-funcionario-box').style.display = 'none';
            document.getElementById('btn-cadastrar-func').style.display = 'block';
        };

        document.getElementById('form-funcionario').onsubmit = function(e) {
            e.preventDefault();
            const codigo = document.getElementById('func-codigo').value.trim();
            const nome = document.getElementById('func-nome').value.trim();
            const email = document.getElementById('func-email').value.trim();
            const cargo = document.getElementById('func-cargo').value;
            const senha = document.getElementById('func-senha').value.trim();
            if (!nome || !email || !cargo || !senha) {
                alert('Preencha todos os campos.');
                return;
            }
            funcionarios.push({ nome, email, cargo, senha, codigo });
            sessionStorage.setItem('funcionarios', JSON.stringify(funcionarios));
            renderPerfilFuncionario();
        };

        document.querySelectorAll('.btn-excluir-func').forEach(btn => {
            btn.onclick = function() {
                const idx = parseInt(this.getAttribute('data-idx'));
                if (confirm('Deseja realmente excluir este funcionário?')) {
                    funcionarios.splice(idx, 1);
                    sessionStorage.setItem('funcionarios', JSON.stringify(funcionarios));
                    renderPerfilFuncionario();
                }
            }
        });
    }

    // Sidebar navigation
    document.getElementById('menu-dashboard').onclick = function(e) { e.preventDefault(); renderDashboard(); };
    document.getElementById('menu-clientes').onclick = function(e) { e.preventDefault(); renderListaContas(); };
    document.getElementById('menu-transacoes').onclick = function(e) { e.preventDefault(); renderTransacoes(); };
    document.getElementById('menu-analise').onclick = function(e) { e.preventDefault(); renderAnaliseConta(); };
    document.getElementById('menu-relatorios').onclick = function(e) { e.preventDefault(); renderRelatorios(); };
    document.getElementById('menu-perfil-funcionario').onclick = function(e) { e.preventDefault(); renderPerfilFuncionario(); };

    // Inicialização
    renderDashboard();
});