document.addEventListener('DOMContentLoaded', function() {
    const mainContent = document.getElementById('main-content');
    let contas = JSON.parse(sessionStorage.getItem('contas')) || [];
    window.transacoes = JSON.parse(sessionStorage.getItem('transacoes')) || [];

    // Gerenciamento de Funcionários
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

    // Utilitário para menu ativo
    function setActiveMenu(menuId) {
        document.querySelectorAll('.sidebar-nav li a').forEach(link => link.classList.remove('active'));
        const activeLink = document.getElementById(menuId);
        if (activeLink) activeLink.classList.add('active');
    }

    // Dashboard
    function renderDashboard() {
        setActiveMenu('menu-dashboard');
        mainContent.innerHTML = `
            <h2>Dashboard</h2>
            <div class="conta-box">
                <p>Bem-vindo ao painel do funcionário do Callisto Bank.</p>
                <ul>
                    <li><b>Total de contas:</b> ${contas.length}</li>
                    <li><b>Total de transações:</b> ${window.transacoes.length}</li>
                </ul>
            </div>
        `;
    }

    // Contas de clientes
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
            <div style="display: flex; gap: 2rem; margin-top:2rem;">
                <div class="conta-box" style="flex:1;">
                    <h4>Encerrar Conta</h4>
                    <input type="text" id="cpf-encerrar" placeholder="Digite o CPF" maxlength="14" style="margin-right:0.5rem; width:70%;">
                    <button id="btn-encerrar-conta">Encerrar</button>
                </div>
                <div class="conta-box" style="flex:1;">
                    <h4>Gerar Cartão</h4>
                    <input type="text" id="cpf-gerar-cartao" placeholder="Digite o CPF" maxlength="14" style="margin-right:0.5rem; width:70%;">
                    <button id="btn-gerar-cartao">Gerar Cartão</button>
                </div>
            </div>
        `;
        sessionStorage.setItem('contas', JSON.stringify(contas));

        document.getElementById('btn-criar-conta').onclick = function() {
            renderFormConta();
        };

        // Máscara CPF para os campos
        document.getElementById('cpf-encerrar').addEventListener('input', function(e) {
            let v = e.target.value.replace(/\D/g, '');
            if (v.length > 3) v = v.replace(/^(\d{3})(\d)/, '$1.$2');
            if (v.length > 6) v = v.replace(/^(\d{3})\.(\d{3})(\d)/, '$1.$2.$3');
            if (v.length > 9) v = v.replace(/^(\d{3})\.(\d{3})\.(\d{3})(\d)/, '$1.$2.$3-$4');
            e.target.value = v;
        });
        document.getElementById('cpf-gerar-cartao').addEventListener('input', function(e) {
            let v = e.target.value.replace(/\D/g, '');
            if (v.length > 3) v = v.replace(/^(\d{3})(\d)/, '$1.$2');
            if (v.length > 6) v = v.replace(/^(\d{3})\.(\d{3})(\d)/, '$1.$2.$3');
            if (v.length > 9) v = v.replace(/^(\d{3})\.(\d{3})\.(\d{3})(\d)/, '$1.$2.$3-$4');
            e.target.value = v;
        });

        // Encerrar conta por CPF
        document.getElementById('btn-encerrar-conta').onclick = function() {
            const cpf = document.getElementById('cpf-encerrar').value.replace(/\D/g, '');
            const idx = contas.findIndex(c => c.cpf.replace(/\D/g, '') === cpf);
            if (idx === -1) {
                alert('Conta não encontrada para este CPF.');
                return;
            }
            if (confirm('Tem certeza que deseja encerrar esta conta?')) {
                contas.splice(idx, 1);
                sessionStorage.setItem('contas', JSON.stringify(contas));
                renderListaContas();
            }
        };

        // Gerar cartão por CPF
        document.getElementById('btn-gerar-cartao').onclick = function() {
            const cpf = document.getElementById('cpf-gerar-cartao').value.replace(/\D/g, '');
            const idx = contas.findIndex(c => c.cpf.replace(/\D/g, '') === cpf);
            if (idx === -1) {
                alert('Conta não encontrada para este CPF.');
                return;
            }
            renderGerarCartao(idx);
        };
    }

    // Formulário de criação de conta
    function renderFormConta() {
        setActiveMenu('menu-clientes');
        mainContent.innerHTML = `
            <h2>Criar Nova Conta</h2>
            <form id="form-criar-conta" class="conta-box" autocomplete="off">
                <input type="hidden" id="tipo_usuario" value="CLIENTE" />
                <label for="nome-cliente">Nome Completo</label>
                <input type="text" id="nome-cliente" required />

                <label for="cpf-cliente">CPF</label>
                <input name="cpf" type="text" id="cpf-cliente" required placeholder="000.000.000-00" maxlength="14"/>

                <label for="nascimento-cliente">Data de Nascimento</label>
                <input name="data_nascimento" type="date" id="nascimento-cliente" required />

                <label for="telefone-cliente">Telefone</label>
                <input name="telefone" type="tel" id="telefone-cliente" required placeholder="Somente números, ex: 11999999999" maxlength="11"/>

                <label for="cep-cliente">CEP</label>
                <input type="text" id="cep-cliente" required placeholder="00000-000" maxlength="9"/>

                <label for="endereco-cliente">Endereço</label>
                <input name="" type="text" id="endereco-cliente" required placeholder="Endereço completo" />

                <div id="endereco-boxes">
                    <div>
                        <label for="rua-cliente">Rua</label>
                        <input type="text" id="rua-cliente" required placeholder="Rua"/>
                    </div>
                    <div>
                        <label for="numero-cliente">Número</label>
                        <input type="text" id="numero-cliente" required placeholder="Número"/>
                    </div>
                    <div>
                        <label for="complemento-cliente">Complemento</label>
                        <input type="text" id="complemento-cliente" placeholder="Complemento"/>
                    </div>
                </div>

                <label for="senha-cliente">Senha (6 dígitos, não sequencial)</label>
                <input name="senha" type="password" id="senha-cliente" required placeholder="Ex: 135790" maxlength="6" minlength="6"/>

                <button type="submit" style="margin-top:1rem;width:100%;">Salvar Conta</button>
                <button type="button" id="btn-voltar" style="margin-top:1rem;width:100%;background:#ccc;color:#260A04;">Cancelar</button>
            </form>
        `;

        // Máscara CPF
        document.getElementById('cpf-cliente').addEventListener('input', function(e) {
            let v = e.target.value.replace(/\D/g, '');
            if (v.length > 3) v = v.replace(/^(\d{3})(\d)/, '$1.$2');
            if (v.length > 6) v = v.replace(/^(\d{3})\.(\d{3})(\d)/, '$1.$2.$3');
            if (v.length > 9) v = v.replace(/^(\d{3})\.(\d{3})\.(\d{3})(\d)/, '$1.$2.$3-$4');
            e.target.value = v;
        });

        // Máscara telefone
        document.getElementById('telefone-cliente').addEventListener('input', function(e) {
            e.target.value = e.target.value.replace(/\D/g, '').slice(0, 11);
        });

        // Máscara CEP
        document.getElementById('cep-cliente').addEventListener('input', function(e) {
            let v = e.target.value.replace(/\D/g, '');
            if (v.length > 5) v = v.replace(/^(\d{5})(\d)/, '$1-$2');
            e.target.value = v;
        });

        // Busca ViaCEP e preenche o campo endereço
        document.getElementById('cep-cliente').addEventListener('blur', function(e) {
            const cep = e.target.value.replace(/\D/g, '');
            if (cep.length === 8) {
                fetch(`https://viacep.com.br/ws/${cep}/json/`)
                    .then(resp => resp.json())
                    .then(data => {
                        if (data.erro) {
                            alert('CEP não encontrado.');
                            return;
                        }
                        // Preenche o campo endereço completo
                        const enderecoCompleto = `${data.logradouro || ''}${data.bairro ? ', ' + data.bairro : ''}${data.localidade ? ' - ' + data.localidade : ''}${data.uf ? '/' + data.uf : ''}`;
                        document.getElementById('endereco-cliente').value = enderecoCompleto.trim();
                        document.getElementById('rua-cliente').value = data.logradouro || '';
                    })
                    .catch(() => alert('Erro ao buscar o CEP.'));
            }
        });

        document.getElementById('form-criar-conta').onsubmit = function(e) {
            e.preventDefault();
            const nome = document.getElementById('nome-cliente').value.trim();
            const cpf = document.getElementById('cpf-cliente').value.trim();
            const agencia = document.getElementById('agencia').value.trim();
            const nascimento = document.getElementById('nascimento-cliente').value;
            const telefone = document.getElementById('telefone-cliente').value.trim();
            const cep = document.getElementById('cep-cliente').value.trim();
            const endereco = document.getElementById('endereco-cliente').value.trim();
            const rua = document.getElementById('rua-cliente').value.trim();
            const numero = document.getElementById('numero-cliente').value.trim();
            const complemento = document.getElementById('complemento-cliente').value.trim();
            const senha = document.getElementById('senha-cliente').value.trim();

            if (!nome || !cpf || !agencia || !nascimento || !telefone || !cep || !endereco || !rua || !numero || !senha) {
                alert('Preencha todos os campos obrigatórios.');
                return;
            }
            contas.push({
                nome, cpf, agencia, nascimento, telefone, cep, endereco, rua, numero, complemento, senha, cartao: null
            });
            sessionStorage.setItem('contas', JSON.stringify(contas));
            renderListaContas();
        };

        document.getElementById('btn-voltar').onclick = renderListaContas;
    }

    // Função para gerar código sequencial de funcionário
    function gerarCodigoFuncionario() {
        if (!window.funcionarios || window.funcionarios.length === 0) return "FUN0001";
        const codigos = window.funcionarios.map(f => parseInt(f.codigo.replace("FUN", ""))).filter(n => !isNaN(n));
        const max = codigos.length ? Math.max(...codigos) : 0;
        return "FUN" + String(max + 1).padStart(4, "0");
    }

    // Gerenciamento de Funcionários
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

        // Ao clicar, esconde a box do botão e mostra o formulário
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
            if (!nome || !email || !cargo || !senha) {
                alert('Preencha todos os campos.');
                return;
            }
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

    // TRANSACOES
    function renderTransacoes() {
        setActiveMenu('menu-transacoes');
        mainContent.innerHTML = `
            <h2>Registrar Transação</h2>
            <form id="form-transacao" class="conta-box" autocomplete="off">
                <label>CPF do Cliente</label>
                <input type="text" id="trans-cpf" required placeholder="000.000.000-00" maxlength="14"/>
                <label>Tipo de Transação</label>
                <select id="trans-tipo" required>
                    <option value="">Selecione</option>
                    <option value="Depósito">Depósito</option>
                    <option value="Saque">Saque</option>
                    <option value="Transferência">Transferência</option>
                </select>
                <div id="transferencia-campos" style="display:none;">
                    <label>CPF do Destinatário</label>
                    <input type="text" id="trans-cpf-destino" placeholder="000.000.000-00" maxlength="14"/>
                </div>
                <label>Descrição</label>
                <input type="text" id="trans-desc" required placeholder="Descrição da transação"/>
                <label>Valor</label>
                <input type="number" id="trans-valor" required step="0.01" min="0"/>
                <button type="submit" style="margin-top:1rem;width:100%;">Registrar</button>
            </form>
            <div id="transacao-msg" style="margin-top:1rem;"></div>
        `;

        document.getElementById('trans-cpf').addEventListener('input', function(e) {
            let v = e.target.value.replace(/\D/g, '');
            if (v.length > 3) v = v.replace(/^(\d{3})(\d)/, '$1.$2');
            if (v.length > 6) v = v.replace(/^(\d{3})\.(\d{3})(\d)/, '$1.$2.$3');
            if (v.length > 9) v = v.replace(/^(\d{3})\.(\d{3})\.(\d{3})(\d)/, '$1.$2.$3-$4');
            e.target.value = v;
        });

        document.getElementById('trans-tipo').addEventListener('change', function(e) {
            const tipo = e.target.value;
            const campos = document.getElementById('transferencia-campos');
            if (tipo === 'Transferência') {
                campos.style.display = 'block';
            } else {
                campos.style.display = 'none';
            }
        });
        document.getElementById('trans-cpf-destino')?.addEventListener('input', function(e) {
            let v = e.target.value.replace(/\D/g, '');
            if (v.length > 3) v = v.replace(/^(\d{3})(\d)/, '$1.$2');
            if (v.length > 6) v = v.replace(/^(\d{3})\.(\d{3})(\d)/, '$1.$2.$3');
            if (v.length > 9) v = v.replace(/^(\d{3})\.(\d{3})\.(\d{3})(\d)/, '$1.$2.$3-$4');
            e.target.value = v;
        });

        document.getElementById('form-transacao').onsubmit = function(e) {
            e.preventDefault();
            const cpf = document.getElementById('trans-cpf').value.replace(/\D/g, '');
            const tipo = document.getElementById('trans-tipo').value;
            const descricao = document.getElementById('trans-desc').value.trim();
            const valor = parseFloat(document.getElementById('trans-valor').value);
            const contaOrigem = contas.find(c => c.cpf.replace(/\D/g, '') === cpf);

            if (!contaOrigem) {
                document.getElementById('transacao-msg').innerHTML = `<span style="color:#bf2424;">Conta de origem não encontrada.</span>`;
                return;
            }
            if (!descricao || isNaN(valor) || !tipo) {
                document.getElementById('transacao-msg').innerHTML = `<span style="color:#bf2424;">Preencha todos os campos.</span>`;
                return;
            }
            if (valor <= 0) {
                document.getElementById('transacao-msg').innerHTML = `<span style="color:#bf2424;">Valor inválido.</span>`;
                return;
            }

            if (tipo === 'Depósito') {
                transacoes.push({
                    cpf: contaOrigem.cpf,
                    descricao,
                    valor,
                    tipo: 'Depósito',
                    data: new Date().toLocaleString()
                });
                document.getElementById('transacao-msg').innerHTML = `<span style="color:green;">Depósito registrado!</span>`;
            } else if (tipo === 'Saque') {
                const extrato = (transacoes || []).filter(t => t.cpf === contaOrigem.cpf);
                const saldo = extrato.reduce((acc, t) => {
                    if (t.tipo === 'Depósito' || t.tipo === 'Crédito') return acc + t.valor;
                    if (t.tipo === 'Saque' || t.tipo === 'Débito') return acc - t.valor;
                    if (t.tipo === 'Transferência' && t.cpf === contaOrigem.cpf && t.valor) return acc - t.valor;
                    if (t.tipo === 'Transferência Recebida' && t.cpf === contaOrigem.cpf && t.valor) return acc + t.valor;
                    return acc;
                }, 0);
                if (saldo < valor) {
                    document.getElementById('transacao-msg').innerHTML = `<span style="color:#bf2424;">Saldo insuficiente para saque.</span>`;
                    return;
                }
                transacoes.push({
                    cpf: contaOrigem.cpf,
                    descricao,
                    valor,
                    tipo: 'Saque',
                    data: new Date().toLocaleString()
                });
                document.getElementById('transacao-msg').innerHTML = `<span style="color:green;">Saque registrado!</span>`;
            } else if (tipo === 'Transferência') {
                const cpfDestino = document.getElementById('trans-cpf-destino').value.replace(/\D/g, '');
                const contaDestino = contas.find(c => c.cpf.replace(/\D/g, '') === cpfDestino);
                if (!contaDestino) {
                    document.getElementById('transacao-msg').innerHTML = `<span style="color:#bf2424;">Conta de destino não encontrada.</span>`;
                    return;
                }
                if (cpfDestino === cpf) {
                    document.getElementById('transacao-msg').innerHTML = `<span style="color:#bf2424;">CPF de destino deve ser diferente do de origem.</span>`;
                    return;
                }
                const extrato = (transacoes || []).filter(t => t.cpf === contaOrigem.cpf);
                const saldo = extrato.reduce((acc, t) => {
                    if (t.tipo === 'Depósito' || t.tipo === 'Crédito') return acc + t.valor;
                    if (t.tipo === 'Saque' || t.tipo === 'Débito') return acc - t.valor;
                    if (t.tipo === 'Transferência' && t.cpf === contaOrigem.cpf && t.valor) return acc - t.valor;
                    if (t.tipo === 'Transferência Recebida' && t.cpf === contaOrigem.cpf && t.valor) return acc + t.valor;
                    return acc;
                }, 0);
                if (saldo < valor) {
                    document.getElementById('transacao-msg').innerHTML = `<span style="color:#bf2424;">Saldo insuficiente para transferência.</span>`;
                    return;
                }
                transacoes.push({
                    cpf: contaOrigem.cpf,
                    descricao: descricao + ' (para ' + contaDestino.nome + ')',
                    valor,
                    tipo: 'Transferência',
                    data: new Date().toLocaleString()
                });
                transacoes.push({
                    cpf: contaDestino.cpf,
                    descricao: descricao + ' (de ' + contaOrigem.nome + ')',
                    valor,
                    tipo: 'Transferência Recebida',
                    data: new Date().toLocaleString()
                });
                document.getElementById('transacao-msg').innerHTML = `<span style="color:green;">Transferência registrada!</span>`;
            }
            document.getElementById('form-transacao').reset();
            document.getElementById('transferencia-campos').style.display = 'none';
            sessionStorage.setItem('transacoes', JSON.stringify(transacoes));
        };
    }

    // ANALISE DE CONTA
    function renderAnaliseConta() {
        setActiveMenu('menu-analise');
        mainContent.innerHTML = `
            <h2>Análise de Conta</h2>
            <div style="margin-bottom:1rem;">
                <input type="text" id="analise-cpf" placeholder="Digite o CPF do cliente" maxlength="14" style="padding:0.5rem; border-radius:6px; border:1px solid #ccc;">
                <button id="btn-analise" style="margin-left:0.5rem; padding:0.5rem 1.2rem; border-radius:6px; background:linear-gradient(135deg,#595348 0%,#260A04 100%);color:#fff;border:none;cursor:pointer;">Analisar</button>
            </div>
            <div id="analise-resultado"></div>
        `;

        document.getElementById('analise-cpf').addEventListener('input', function(e) {
            let v = e.target.value.replace(/\D/g, '');
            if (v.length > 3) v = v.replace(/^(\d{3})(\d)/, '$1.$2');
            if (v.length > 6) v = v.replace(/^(\d{3})\.(\d{3})(\d)/, '$1.$2.$3');
            if (v.length > 9) v = v.replace(/^(\d{3})\.(\d{3})\.(\d{3})(\d)/, '$1.$2.$3-$4');
            e.target.value = v;
        });

        document.getElementById('btn-analise').onclick = function() {
            const cpfFiltro = document.getElementById('analise-cpf').value.replace(/\D/g, '');
            if (!cpfFiltro || cpfFiltro.length !== 11) {
                document.getElementById('analise-resultado').innerHTML = `<span style="color:#bf2424;">Digite um CPF válido.</span>`;
                return;
            }
            const conta = contas.find(c => c.cpf.replace(/\D/g, '') === cpfFiltro);
            if (!conta) {
                document.getElementById('analise-resultado').innerHTML = `<span style="color:#bf2424;">Conta não encontrada.</span>`;
                return;
            }
            const extrato = (transacoes || []).filter(t => t.cpf === conta.cpf);
            const saldo = extrato.reduce((acc, t) => {
                if (t.tipo === 'Depósito' || t.tipo === 'Crédito' || t.tipo === 'Transferência Recebida') return acc + t.valor;
                if (t.tipo === 'Saque' || t.tipo === 'Débito' || t.tipo === 'Transferência') return acc - t.valor;
                return acc;
            }, 0);
            document.getElementById('analise-resultado').innerHTML = `
                <div class="conta-box">
                    <p><b>Nome:</b> ${conta.nome}</p>
                    <p><b>CPF:</b> ${conta.cpf}</p>
                    <p><b>Saldo atual:</b> R$ ${saldo.toFixed(2)}</p>
                    <p><b>Total de transações:</b> ${extrato.length}</p>
                </div>
            `;
        };
    }

    // RELATORIOS
    function renderRelatorios() {
        setActiveMenu('menu-relatorios');
        mainContent.innerHTML = `
            <h2>Relatório de Extrato</h2>
            <div style="margin-bottom:1rem;">
                <input type="text" id="filtro-cpf" placeholder="Digite o CPF do cliente" maxlength="14" style="padding:0.5rem; border-radius:6px; border:1px solid #ccc;">
                <button id="btn-filtrar" style="margin-left:0.5rem; padding:0.5rem 1.2rem; border-radius:6px; background:linear-gradient(135deg,#595348 0%,#260A04 100%);color:#fff;border:none;cursor:pointer;">Buscar Extrato</button>
            </div>
            <div id="relatorio-extrato"></div>
        `;

        document.getElementById('filtro-cpf').addEventListener('input', function(e) {
            let v = e.target.value.replace(/\D/g, '');
            if (v.length > 3) v = v.replace(/^(\d{3})(\d)/, '$1.$2');
            if (v.length > 6) v = v.replace(/^(\d{3})\.(\d{3})(\d)/, '$1.$2.$3');
            if (v.length > 9) v = v.replace(/^(\d{3})\.(\d{3})\.(\d{3})(\d)/, '$1.$2.$3-$4');
            e.target.value = v;
        });

        document.getElementById('btn-filtrar').onclick = function() {
            const cpfFiltro = document.getElementById('filtro-cpf').value.replace(/\D/g, '');
            if (!cpfFiltro || cpfFiltro.length !== 11) {
                alert('Digite um CPF válido.');
                return;
            }
            const conta = contas.find(c => c.cpf.replace(/\D/g, '') === cpfFiltro);
            if (!conta) {
                document.getElementById('relatorio-extrato').innerHTML = `<div style="color:#bf2424;">Conta não encontrada.</div>`;
                return;
            }
            const extrato = (transacoes || []).filter(t => t.cpf === conta.cpf);
            let extratoHtml = `
                <h3>Extrato de ${conta.nome} (${conta.cpf})</h3>
                <table id="tabela-extrato" style="width:100%;border-collapse:collapse;">
                    <thead>
                        <tr>
                            <th style="border:1px solid #ccc;padding:0.5rem;">Data</th>
                            <th style="border:1px solid #ccc;padding:0.5rem;">Descrição</th>
                            <th style="border:1px solid #ccc;padding:0.5rem;">Valor</th>
                            <th style="border:1px solid #ccc;padding:0.5rem;">Tipo</th>
                        </tr>
                    </thead>
                    <tbody>
                        ${
                            extrato.length === 0
                            ? `<tr><td colspan="4" style="text-align:center;">Nenhuma transação encontrada.</td></tr>`
                            : extrato.map(t => `
                                <tr>
                                    <td style="border:1px solid #ccc;padding:0.5rem;">${t.data || ''}</td>
                                    <td style="border:1px solid #ccc;padding:0.5rem;">${t.descricao || ''}</td>
                                    <td style="border:1px solid #ccc;padding:0.5rem;">${t.valor != null ? 'R$ ' + Number(t.valor).toFixed(2) : ''}</td>
                                    <td style="border:1px solid #ccc;padding:0.5rem;">${t.tipo || ''}</td>
                                </tr>
                            `).join('')
                        }
                    </tbody>
                </table>
            `;
            document.getElementById('relatorio-extrato').innerHTML = extratoHtml;
        };
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