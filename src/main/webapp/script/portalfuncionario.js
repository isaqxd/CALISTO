document.addEventListener('DOMContentLoaded', function() {
    const mainContent = document.getElementById('main-content');
    let contas = JSON.parse(sessionStorage.getItem('contas')) || [];
    window.transacoes = JSON.parse(sessionStorage.getItem('transacoes')) || [];

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
                <input type="text" id="novo-nome" placeholder="Nome do cliente" style="margin-bottom:1rem;width:100%;" />
                <button id="btn-criar-conta" style="width:100%;">Criar Conta</button>
            </div>
            <div class="contas-lista">
                <h3>Clientes</h3>
                <ul>
                    ${
                        contas.length === 0
                        ? '<li>Nenhuma conta cadastrada.</li>'
                        : contas.map((c, i) =>
                            `<li style="display:flex;align-items:center;justify-content:space-between;gap:1rem;">
                                <a href="#" class="conta-link" data-idx="${i}" style="flex:1;">${c.nome}</a>
                                <button class="btn-cartao" data-idx="${i}" style="background:#1976d2;color:#fff;border:none;border-radius:6px;padding:0.3rem 0.8rem;cursor:pointer;">Gerar Cartão</button>
                                <button class="btn-encerrar" data-idx="${i}" style="background:#bf2424;color:#fff;border:none;border-radius:6px;padding:0.3rem 0.8rem;cursor:pointer;">Encerrar</button>
                            </li>`
                        ).join('')
                    }
                </ul>
            </div>
        `;
        sessionStorage.setItem('contas', JSON.stringify(contas));

        document.querySelectorAll('.btn-cartao').forEach(btn => {
            btn.onclick = function() {
                const idx = this.getAttribute('data-idx');
                renderGerarCartao(idx);
            }
        });
        document.getElementById('btn-criar-conta').onclick = function() {
            const nome = document.getElementById('novo-nome').value.trim();
            if (!nome) {
                alert('Digite o nome do cliente.');
                return;
            }
            renderFormConta(nome.toUpperCase());
        };
        document.querySelectorAll('.conta-link').forEach(link => {
            link.onclick = function(e) {
                e.preventDefault();
                const idx = this.getAttribute('data-idx');
                renderDetalhesConta(idx);
            }
        });
        document.querySelectorAll('.btn-encerrar').forEach(btn => {
            btn.onclick = function() {
                const idx = parseInt(this.getAttribute('data-idx'));
                if (confirm('Tem certeza que deseja encerrar esta conta?')) {
                    contas.splice(idx, 1);
                    sessionStorage.setItem('contas', JSON.stringify(contas));
                    renderListaContas();
                }
            }
        });
    }

    // Formulário de criação de conta com ViaCEP
    function renderFormConta(nomeCliente) {
        setActiveMenu('menu-clientes');
        mainContent.innerHTML = `
            <h2>Criar Nova Conta</h2>
            <form id="form-criar-conta" class="conta-box" autocomplete="off">
                <label for="nome-cliente">Nome Completo</label>
                <input type="text" id="nome-cliente" required value="${nomeCliente}" readonly />

                <label for="cpf-cliente">CPF</label>
                <input type="text" id="cpf-cliente" required placeholder="000.000.000-00" maxlength="14"/>

                <label for="agencia">Agência</label>
                <input type="text" id="agencia" required placeholder="Agência (4 dígitos)" maxlength="4" pattern="\\d{4}" />

                <label for="nascimento-cliente">Data de Nascimento</label>
                <input type="date" id="nascimento-cliente" required />

                <label for="telefone-cliente">Telefone</label>
                <input type="tel" id="telefone-cliente" required placeholder="Somente números, ex: 11999999999" maxlength="11"/>

                <label for="cep-cliente">CEP</label>
                <input type="text" id="cep-cliente" required placeholder="00000-000" maxlength="9"/>

                <label for="endereco-cliente">Endereço</label>
                <input type="text" id="endereco-cliente" required placeholder="Endereço completo" />

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
                <input type="password" id="senha-cliente" required placeholder="Ex: 135790" maxlength="6" minlength="6"/>

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

        // Busca ViaCEP
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
                        document.getElementById('rua-cliente').value = data.logradouro || '';
                        // Se quiser, adicione campos para bairro, cidade, estado e preencha aqui.
                    })
                    .catch(() => alert('Erro ao buscar o CEP.'));
            }
        });

        document.getElementById('form-criar-conta').onsubmit = function(e) {
            e.preventDefault();
            const nome = nomeCliente;
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

    // Gerar Cartão (fluxo integrado)
    function renderGerarCartao(idxConta) {
        const conta = contas[idxConta];
        if (!conta) return renderListaContas();

        mainContent.innerHTML = `
            <h2>Gerar Cartão</h2>
            <form id="form-cartao" class="conta-box" autocomplete="off">
                <label>Nome</label>
                <input type="text" id="cardName" required value="${conta.nome}" />
                <label>CPF</label>
                <input type="text" id="cardCpf" required maxlength="14" value="${conta.cpf}" />
                <label>Tipo de Cartão</label>
                <select id="cardType" required>
                    <option value="">Selecione</option>
                    <option value="platinum">Platinum</option>
                    <option value="gold">Gold</option>
                    <option value="black">Black</option>
                </select>
                <div id="beneficios-cartao" style="margin-top:1rem;"></div>
                <button type="submit" style="margin-top:1rem;width:100%;">Gerar Cartão</button>
            </form>
            <div id="cartao-visual" style="margin-top:2rem;display:none;"></div>
            <button class="btn-voltar" id="btn-voltar-cartao" style="display:none;">
                Voltar
            </button>
        `;

        // Máscara CPF e busca automática do nome
        const cpfInput = document.getElementById('cardCpf');
        const nomeInput = document.getElementById('cardName');
        if (cpfInput && nomeInput) {
            cpfInput.addEventListener('input', function(e) {
                let v = e.target.value.replace(/\D/g, '');
                if (v.length > 3) v = v.replace(/^(\d{3})(\d)/, '$1.$2');
                if (v.length > 6) v = v.replace(/^(\d{3})\.(\d{3})(\d)/, '$1.$2.$3');
                if (v.length > 9) v = v.replace(/^(\d{3})\.(\d{3})\.(\d{3})(\d)/, '$1.$2.$3-$4');
                e.target.value = v;

                // Busca nome automaticamente se CPF completo
                const cpfLimpo = v.replace(/\D/g, '');
                if (cpfLimpo.length === 11) {
                    const c = contas.find(c => c.cpf.replace(/\D/g, '') === cpfLimpo);
                    if (c) nomeInput.value = c.nome;
                }
            });
        }

        // Atualiza benefícios ao mudar tipo de cartão
        const beneficios = {
            platinum: "✔️ Limite alto<br>✔️ Programa de pontos<br>✔️ Atendimento exclusivo",
            gold: "✔️ Limite médio<br>✔️ Programa de pontos",
            black: "✔️ Limite premium<br>✔️ Concierge 24h<br>✔️ Salas VIP em aeroportos<br>✔️ Seguros e assistências"
        };
        document.getElementById('cardType').addEventListener('change', function() {
            const val = this.value;
            document.getElementById('beneficios-cartao').innerHTML = beneficios[val] || '';
        });

        document.getElementById('form-cartao').onsubmit = function(e) {
            e.preventDefault();
            const nome = nomeInput.value.trim();
            const cpf = cpfInput.value.trim();
            const tipo = document.getElementById('cardType').value;
            if (!nome || !cpf || !tipo) {
                alert('Preencha todos os campos.');
                return;
            }
            // Geração dos dados do cartão
            const numero = `${Math.floor(1000 + Math.random() * 9000)} ${Math.floor(1000 + Math.random() * 9000)} ${Math.floor(1000 + Math.random() * 9000)} ${Math.floor(1000 + Math.random() * 9000)}`;
            const validade = `${String(new Date().getMonth()+1).padStart(2,'0')}/${String(new Date().getFullYear()+4).toString().slice(-2)}`;
            const cvv = Math.floor(100 + Math.random() * 900);

            // Salva o cartão na conta
            contas[idxConta].cartao = { tipo, nome, cpf, numero, validade, cvv };
            sessionStorage.setItem('contas', JSON.stringify(contas));

            // Mostra o cartão visual
            document.getElementById('cartao-visual').style.display = 'block';
            document.getElementById('cartao-visual').innerHTML = renderCartaoVisual(contas[idxConta].cartao);

            // Esconde o form e mostra o botão voltar
            document.getElementById('form-cartao').style.display = 'none';
            document.getElementById('btn-voltar-cartao').style.display = 'block';
        };

        // Ao clicar em voltar, retorna para o dashboard (tela inicial)
        document.getElementById('btn-voltar-cartao').onclick = function() {
            renderDashboard();
        };
    }

    // Função para renderizar o cartão visual
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

    // Transações (Depósito, Saque, Transferência)
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

        // Máscara CPF origem
        document.getElementById('trans-cpf').addEventListener('input', function(e) {
            let v = e.target.value.replace(/\D/g, '');
            if (v.length > 3) v = v.replace(/^(\d{3})(\d)/, '$1.$2');
            if (v.length > 6) v = v.replace(/^(\d{3})\.(\d{3})(\d)/, '$1.$2.$3');
            if (v.length > 9) v = v.replace(/^(\d{3})\.(\d{3})\.(\d{3})(\d)/, '$1.$2.$3-$4');
            e.target.value = v;
        });

        // Máscara CPF destino (transferência)
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

            // Verificações e lógica para cada tipo
            if (tipo === 'Depósito') {
                window.transacoes.push({
                    cpf: contaOrigem.cpf,
                    descricao,
                    valor,
                    tipo: 'Depósito',
                    data: new Date().toLocaleString()
                });
                document.getElementById('transacao-msg').innerHTML = `<span style="color:green;">Depósito registrado!</span>`;
            } else if (tipo === 'Saque') {
                // Calcula saldo
                const extrato = (window.transacoes || []).filter(t => t.cpf === contaOrigem.cpf);
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
                window.transacoes.push({
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
                // Calcula saldo origem
                const extrato = (window.transacoes || []).filter(t => t.cpf === contaOrigem.cpf);
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
                // Lança transferência (débito origem)
                window.transacoes.push({
                    cpf: contaOrigem.cpf,
                    descricao: descricao + ' (para ' + contaDestino.nome + ')',
                    valor,
                    tipo: 'Transferência',
                    data: new Date().toLocaleString()
                });
                // Lança transferência recebida (crédito destino)
                window.transacoes.push({
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
            sessionStorage.setItem('transacoes', JSON.stringify(window.transacoes));
        };
    }

    // Análise de Conta
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

        // Máscara CPF
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
            // Simulação de análise: saldo, total de transações, etc
            const extrato = (window.transacoes || []).filter(t => t.cpf === conta.cpf);
            const saldo = extrato.reduce((acc, t) => {
                if (t.tipo === 'Depósito' || t.tipo === 'Crédito' || t.tipo === 'Transferência Recebida') return acc + t.valor;
                if (t.tipo === 'Saque' || t.tipo === 'Débito' || t.tipo === 'Transferência') return acc - t.valor;
                return acc;
            }, 0);
            let cartaoHtml = '';
            if (conta.cartao) {
                cartaoHtml = `
                    <h4>Cartão</h4>
                    ${renderCartaoVisual(conta.cartao)}
                `;
            }
            document.getElementById('analise-resultado').innerHTML = `
                <div class="conta-box">
                    <p><b>Nome:</b> ${conta.nome}</p>
                    <p><b>CPF:</b> ${conta.cpf}</p>
                    <p><b>Agência:</b> ${conta.agencia}</p>
                    <p><b>Telefone:</b> ${conta.telefone}</p>
                    <p><b>Saldo atual:</b> R$ ${saldo.toFixed(2)}</p>
                    <p><b>Total de transações:</b> ${extrato.length}</p>
                    ${cartaoHtml}
                </div>
            `;
        };
    }

    // Relatórios (com filtro por CPF e exportação)
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

        // Máscara de CPF
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
            // Extrato: transações da conta pelo CPF
            const extrato = (window.transacoes || []).filter(t => t.cpf === conta.cpf);
            let extratoHtml = `
                <h3>Extrato de ${conta.nome} (${conta.cpf})</h3>
                <button onclick="exportarRelatorioPDF('tabela-extrato')" style="margin-bottom:1rem; margin-right:0.5rem; border-radius:6px; background:linear-gradient(135deg,#595348 0%,#260A04 100%);color:#fff;border:none;cursor:pointer;padding:0.5rem 1.2rem;">Exportar PDF</button>
                <button onclick="exportarRelatorioExcel('tabela-extrato')" style="margin-bottom:1rem; border-radius:6px; background:linear-gradient(135deg,#595348 0%,#260A04 100%);color:#fff;border:none;cursor:pointer;padding:0.5rem 1.2rem;">Exportar Excel</button>
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

    // Exportação PDF
    window.exportarRelatorioPDF = function(tabelaId, nomeArquivo = 'extrato.pdf') {
        const doc = new window.jspdf.jsPDF();
        const tabela = document.getElementById(tabelaId);
        let y = 10;
        doc.setFontSize(14);
        doc.text('Extrato', 10, y);
        y += 10;
        for (let row of tabela.rows) {
            let linha = [];
            for (let cell of row.cells) {
                linha.push(cell.innerText);
            }
            doc.text(linha.join(' | '), 10, y);
            y += 8;
        }
        doc.save(nomeArquivo);
    };

    // Exportação Excel
    window.exportarRelatorioExcel = function(tabelaId, nomeArquivo = 'extrato.xlsx') {
        const tabela = document.getElementById(tabelaId);
        const wb = window.XLSX.utils.table_to_book(tabela, {sheet:"Extrato"});
        window.XLSX.writeFile(wb, nomeArquivo);
    };

    // Sidebar navigation
    document.getElementById('menu-dashboard').onclick = function(e) { e.preventDefault(); renderDashboard(); };
    document.getElementById('menu-clientes').onclick = function(e) { e.preventDefault(); renderListaContas(); };
    document.getElementById('menu-transacoes').onclick = function(e) { e.preventDefault(); renderTransacoes(); };
    document.getElementById('menu-analise').onclick = function(e) { e.preventDefault(); renderAnaliseConta(); };
    document.getElementById('menu-relatorios').onclick = function(e) { e.preventDefault(); renderRelatorios(); };

    // Inicialização
    renderDashboard();
});