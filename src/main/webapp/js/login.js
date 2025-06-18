let isFuncionarioMode = false;

function toggleMode() {
    const body = document.body;
    isFuncionarioMode = !isFuncionarioMode;

    if (isFuncionarioMode) {
        body.classList.add('funcionario-mode');
        // Atualizar textos da seção de boas-vindas
        document.getElementById('welcome-subtitle').textContent = 'Portal do Funcionário';
        document.getElementById('welcome-description').textContent = 'Acesso restrito para colaboradores';
        document.getElementById('toggle-text').textContent = 'Acesso para clientes?';
        document.getElementById('toggle-btn').textContent = 'Acesso do Cliente';
    } else {
        body.classList.remove('funcionario-mode');
        // Atualizar textos da seção de boas-vindas
        document.getElementById('welcome-subtitle').textContent = 'Acesso do Cliente';
        document.getElementById('welcome-description').textContent = 'Gerencie suas finanças com segurança';
        document.getElementById('toggle-text').textContent = 'Acesso para funcionários?';
        document.getElementById('toggle-btn').textContent = 'Portal do Funcionário';
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
        // Event listener para digitação
        input.addEventListener('input', (e) => {
            e.target.value = e.target.value.replace(/[^0-9]/g, '');

            if (e.target.value.length === 1 && index < otpInputs.length - 1) {
                otpInputs[index + 1].focus();
            }

            updateOTPHiddenField();
        });

        // Event listener para backspace
        input.addEventListener('keydown', (e) => {
            if (e.key === 'Backspace' && e.target.value.length === 0 && index > 0) {
                otpInputs[index - 1].focus();
            }
        });

        // Event listener para colar código
        input.addEventListener('paste', (e) => {
            e.preventDefault();

            // Obter o texto colado
            const pastedData = (e.clipboardData || window.clipboardData).getData('text');

            // Limpar caracteres não numéricos
            const cleanedData = pastedData.replace(/[^0-9]/g, '');

            // Verificar se tem pelo menos alguns dígitos
            if (cleanedData.length > 0) {
                // Limpar todos os inputs primeiro
                otpInputs.forEach(input => input.value = '');

                // Preencher os inputs com os dígitos disponíveis
                for (let i = 0; i < Math.min(cleanedData.length, otpInputs.length); i++) {
                    otpInputs[i].value = cleanedData[i];
                }

                // Focar no próximo input vazio ou no último preenchido
                const nextEmptyIndex = Math.min(cleanedData.length, otpInputs.length - 1);
                otpInputs[nextEmptyIndex].focus();

                // Atualizar o campo hidden
                updateOTPHiddenField();
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