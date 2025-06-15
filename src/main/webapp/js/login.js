 let isFuncionarioMode = false;

    function toggleMode() {
    const body = document.body;
    const clienteForm = document.getElementById('cliente-form');
    const funcionarioForm = document.getElementById('funcionario-form');
    const otpForm = document.getElementById('otp-form');

    // Esconder formulário OTP se estiver visível
    if (otpForm.style.display === 'flex') {
    otpForm.style.display = 'none';
}

    isFuncionarioMode = !isFuncionarioMode;

    if (isFuncionarioMode) {
    body.classList.add('funcionario-mode');
    // Atualizar textos da seção de boas-vindas
    document.getElementById('welcome-subtitle').textContent = 'Portal do Funcionário';
    document.getElementById('welcome-description').textContent = 'Acesso restrito para colaboradores';
    document.getElementById('toggle-text').textContent = 'Acesso para clientes?';
    document.getElementById('toggle-btn').textContent = 'Acesso do Cliente';

    // Transição suave dos formulários
    clienteForm.style.display = 'none';
    setTimeout(() => {
    funcionarioForm.style.display = 'flex';
}, 400);
} else {
    body.classList.remove('funcionario-mode');
    // Atualizar textos da seção de boas-vindas
    document.getElementById('welcome-subtitle').textContent = 'Acesso do Cliente';
    document.getElementById('welcome-description').textContent = 'Gerencie suas finanças com segurança';
    document.getElementById('toggle-text').textContent = 'Acesso para funcionários?';
    document.getElementById('toggle-btn').textContent = 'Portal do Funcionário';

    // Transição suave dos formulários
    funcionarioForm.style.display = 'none';
    setTimeout(() => {
    clienteForm.style.display = 'flex';
}, 400);
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

    // Configurar inputs OTP
    function setupOTPInputs() {
    const otpInputs = document.querySelectorAll('.otp-input');
    if (otpInputs.length === 0) return;

    otpInputs.forEach((input, index) => {
    input.addEventListener('input', (e) => {
    e.target.value = e.target.value.replace(/[^0-9]/g, '');

    if (e.target.value.length === 1 && index < otpInputs.length - 1) {
    otpInputs[index + 1].focus();
}

    updateOTPHiddenField();
});

    input.addEventListener('keydown', (e) => {
    if (e.key === 'Backspace' && e.target.value.length === 0 && index > 0) {
    otpInputs[index - 1].focus();
}
});
});
}

    function updateOTPHiddenField() {
    const otpInputs = document.querySelectorAll('.otp-input');
    const hiddenField = document.getElementById('otp');
    if (hiddenField) {
    let otp = '';
    otpInputs.forEach(input => {
    otp += input.value;
});
    hiddenField.value = otp;
}
}

    // Inicialização
    document.addEventListener('DOMContentLoaded', function() {
    // Configurar formatação de CPF para ambos os campos
    const cpfCliente = document.getElementById('cpf-cliente');
    const cpfFunc = document.getElementById('cpf-func');

    if (cpfCliente) {
    cpfCliente.addEventListener('input', function () {
    formatCPF(this);
});
}

    if (cpfFunc) {
    cpfFunc.addEventListener('input', function () {
    formatCPF(this);
});
}

        function togglePassword(fieldId) {
            console.log('=== DEBUG TOGGLE PASSWORD ===');
            console.log('fieldId recebido:', fieldId);

            const field = document.getElementById(fieldId);
            const iconId = 'icon-' + fieldId;
            const icon = document.getElementById(iconId);

            console.log('Campo encontrado:', field);
            console.log('ID do ícone procurado:', iconId);
            console.log('Ícone encontrado:', icon);
            console.log('Tipo atual do campo:', field ? field.type : 'campo não encontrado');

            // Listar todos os elementos com ID para debug
            console.log('Todos os elementos com ID na página:');
            document.querySelectorAll('[id]').forEach(el => {
                console.log('- ID:', el.id, 'Elemento:', el.tagName);
            });

            if (field && icon) {
                console.log('Ambos elementos encontrados, fazendo toggle...');

                if (field.type === 'password') {
                    console.log('Mudando para texto visível');
                    field.type = 'text';
                    icon.src = '../img/iconeeyeclosed.png';
                    icon.alt = 'Ocultar senha';
                    console.log('Novo src do ícone:', icon.src);
                } else {
                    console.log('Mudando para senha oculta');
                    field.type = 'password';
                    icon.src = '../img/iconeyeopen.png';
                    icon.alt = 'Mostrar senha';
                    console.log('Novo src do ícone:', icon.src);
                }

                console.log('Tipo final do campo:', field.type);
            } else {
                console.log('ERRO: Elementos não encontrados!');
                if (!field) console.log('Campo não encontrado!');
                if (!icon) console.log('Ícone não encontrado!');
            }

            console.log('=== FIM DEBUG ===');
        }

    // Configurar OTP
    setupOTPInputs();

    // Verificar se precisa mostrar OTP
    const urlParams = new URLSearchParams(window.location.search);
    const otpParam = urlParams.get('otp_true');

    if (otpParam === 'true') {
    document.getElementById('cliente-form').style.display = 'none';
    document.getElementById('funcionario-form').style.display = 'none';
    document.getElementById('otp-form').style.display = 'flex';
}

    // Fade-in animation
    document.body.style.opacity = '0';
    setTimeout(() => {
    document.body.style.transition = 'opacity 0.5s ease';
    document.body.style.opacity = '1';
}, 100);
});

    // Atalho de teclado (Ctrl + M)
    document.addEventListener('keydown', function (e) {
    if (e.ctrlKey && e.key.toLowerCase() === 'm') {
    e.preventDefault();
    toggleMode();
}
});

 function togglePassword(fieldId) {
     const field = document.getElementById(fieldId);
     const iconId = 'icon-' + fieldId;
     const icon = document.getElementById(iconId);

     if (field && icon) {
         if (field.type === 'password') {
             field.type = 'text';
             icon.src = '../img/iconeyeclosed.png';
             icon.alt = 'Ocultar senha';
         } else {
             field.type = 'password';
             icon.src = '../img/iconeyeopen.png';
             icon.alt = 'Mostrar senha';
         }
     }
 }