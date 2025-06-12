function toggleForm(tipo) {
    const body = document.body;
    const clienteForm = document.getElementById('cliente-form');
    const funcionarioForm = document.getElementById('funcionario-form');
    const welcomeSubtitle = document.getElementById('welcome-subtitle');

    if (tipo === 'funcionario') {
        body.classList.add('funcionario-mode');
        clienteForm.style.display = 'none';
        funcionarioForm.style.display = 'flex';
        welcomeSubtitle.textContent = 'Área do Funcionário';
    } else {
        body.classList.remove('funcionario-mode');
        clienteForm.style.display = 'flex';
        funcionarioForm.style.display = 'none';
        welcomeSubtitle.textContent = 'Área do Cliente';
    }
}

// Formatação de CPF
function formatCPF(input) {
    let value = input.value.replace(/\D/g, '');
    if (value.length > 11) value = value.slice(0, 11);
    value = value.replace(/(\d{3})(\d)/, '$1.$2');
    value = value.replace(/(\d{3})(\d)/, '$1.$2');
    value = value.replace(/(\d{3})(\d{1,2})$/, '$1-$2');
    input.value = value;
}

// Adicionar formatação de CPF aos campos
document.getElementById('cpf-cliente').addEventListener('input', function () {
    formatCPF(this);
});
document.getElementById('cpf-func').addEventListener('input', function () {
    formatCPF(this);
});

// Toggle de visibilidade da senha
document.querySelectorAll('.password-toggle').forEach(button => {
    button.addEventListener('click', function () {
        const input = this.previousElementSibling;
        if (input.type === 'password') {
            input.type = 'text';
            this.textContent = '👁️‍🗨️';
        } else {
            input.type = 'password';
            this.textContent = '👁️';
        }
    });
});