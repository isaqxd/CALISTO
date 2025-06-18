document.addEventListener('DOMContentLoaded', function () {
    const mainContent = document.getElementById('main-content');

    function setActiveMenu(menuId) {
        document.querySelectorAll('.sidebar-nav a').forEach(link => link.classList.remove('active'));
        const selected = document.getElementById(menuId);
        if (selected) selected.classList.add('active');
    }

    function renderDashboard() {
        setActiveMenu('menu-dashboard');
        mainContent.innerHTML = `
            <h2>Bem-vindo ao Painel do Funcionário</h2>
            <p>Aqui você pode acessar as funcionalidades internas do banco.</p>
        `;
    }

    function renderClientes() {
        setActiveMenu('menu-clientes');
        mainContent.innerHTML = `
            <h2>Clientes</h2>
            <p>Lista de clientes será exibida aqui.</p>
        `;
    }

    function renderExtrato() {
        setActiveMenu('menu-extrato');
        const extrato = [
            { data: "2025-06-01", descricao: "Depósito", valor: 1200 },
            { data: "2025-06-03", descricao: "Saque", valor: -300 }
        ];
        mainContent.innerHTML = `
            <h2>Extrato</h2>
            <table style="width:100%;border-collapse:collapse;">
                <thead>
                    <tr>
                        <th>Data</th>
                        <th>Descrição</th>
                        <th>Valor</th>
                    </tr>
                </thead>
                <tbody>
                    ${
                        extrato.map(l => `
                            <tr>
                                <td>${l.data}</td>
                                <td>${l.descricao}</td>
                                <td style="color:${l.valor < 0 ? 'red' : 'green'};">
                                    R$ ${l.valor.toFixed(2)}
                                </td>
                            </tr>
                        `).join('')
                    }
                </tbody>
            </table>
        `;
    }

    function sair() {
        mainContent.innerHTML = `
            <h2>Logout efetuado com sucesso.</h2>
            <p>Até logo!</p>
        `;
    }

    document.getElementById('menu-dashboard').onclick = e => { e.preventDefault(); renderDashboard(); };
    document.getElementById('menu-clientes').onclick = e => { e.preventDefault(); renderClientes(); };
    document.getElementById('menu-extrato').onclick = e => { e.preventDefault(); renderExtrato(); };
    document.getElementById('menu-sair').onclick = e => { e.preventDefault(); sair(); };

    document.getElementById('currentYear').textContent = new Date().getFullYear();
    renderDashboard(); // inicia com dashboard
});
