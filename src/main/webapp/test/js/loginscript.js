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

// Adicionar formatação de CPF aos campos quando existirem
document.addEventListener('DOMContentLoaded', function() {
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
});

// Toggle de visibilidade da senha
document.querySelectorAll('.password-toggle').forEach(button => {
    button.addEventListener('click', function () {
        const input = this.previousElementSibling;
        if (input.type === 'password') {
            input.type = 'text';
            this.textContent = '🙈';
        } else {
            input.type = 'password';
            this.textContent = '👁️';
        }
    });
});

// Função para configurar inputs OTP (caso existam)
function setupOTPInputs() {
    const otpInputs = document.querySelectorAll('.otp-input');
    if (otpInputs.length === 0) return;

    otpInputs.forEach((input, index) => {
        input.addEventListener('input', (e) => {
            // Only allow numbers
            e.target.value = e.target.value.replace(/[^0-9]/g, '');

            // Move to next input
            if (e.target.value.length === 1 && index < otpInputs.length - 1) {
                otpInputs[index + 1].focus();
            }

            // Update hidden field if it exists
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

// Inicializar OTP inputs quando o DOM estiver carregado
document.addEventListener('DOMContentLoaded', function() {
    setupOTPInputs();

    // Fade-in animation
    document.body.style.opacity = '0';
    setTimeout(() => {
        document.body.style.transition = 'opacity 0.5s ease';
        document.body.style.opacity = '1';
    }, 100);
});

// Atalho de teclado para alternar entre modos (Ctrl + M)
document.addEventListener('keydown', function (e) {
    if (e.ctrlKey && e.key.toLowerCase() === 'm') {
        e.preventDefault();
        const body = document.body;
        if (body.classList.contains('funcionario-mode')) {
            toggleForm('cliente');
        } else {
            toggleForm('funcionario');
        }
    }
});