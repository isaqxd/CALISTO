let isFuncionarioMode = false;

// Função para alternar o modo (Cliente/Funcionário)
function toggleMode() {
    const body = document.body;
    isFuncionarioMode = !isFuncionarioMode;

    if (isFuncionarioMode) {
        body.classList.add('funcionario-mode');
        document.getElementById('welcome-subtitle').textContent = 'Portal do Funcionário';
        document.getElementById('welcome-description').textContent = 'Acesso restrito para colaboradores';
        document.getElementById('toggle-text').textContent = 'Acesso para clientes?';
        document.getElementById('toggle-btn').textContent = 'Acesso do Cliente';
    } else {
        body.classList.remove('funcionario-mode');
        document.getElementById('welcome-subtitle').textContent = 'Acesso do Cliente';
        document.getElementById('welcome-description').textContent = 'Gerencie suas finanças com segurança';
        document.getElementById('toggle-text').textContent = 'Acesso para funcionários?';
        document.getElementById('toggle-btn').textContent = 'Portal do Funcionário';
    }
    // Quando muda de modo, garante que o OTP não esteja visível, a menos que a URL o exija
    const urlParams = new URLSearchParams(window.location.search);
    const otpParam = urlParams.get('otp_true');
    if (otpParam !== 'true') {
        hideOTPOverlay();
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

// Configurar inputs OTP - CORRIGIDO
function setupOTPInputs() {
    const otpInputs = document.querySelectorAll('.otp-input');
    if (otpInputs.length === 0) return;

    otpInputs.forEach((input, index) => {
        // Desabilitar autocomplete e histórico do navegador
        input.setAttribute('autocomplete', 'one-time-code'); // Sugere autocompletar com código OTP
        input.setAttribute('autocorrect', 'off');
        input.setAttribute('autocapitalize', 'off');
        input.setAttribute('spellcheck', 'false');
        // Remove data-form-type para tentar o preenchimento automático do navegador para OTPs

        // Event listener para digitação
        input.addEventListener('input', (e) => {
            // Apenas números
            e.target.value = e.target.value.replace(/[^0-9]/g, '');

            // Mover para próximo campo se digitou algo e o campo atual não está vazio
            if (e.target.value.length === 1 && index < otpInputs.length - 1) {
                otpInputs[index + 1].focus();
            } else if (e.target.value.length === 0 && index > 0) {
                // Se o campo foi esvaziado, move para o anterior
                otpInputs[index - 1].focus();
            }

            updateOTPHiddenField();
        });

        // Event listener para backspace
        input.addEventListener('keydown', (e) => {
            if (e.key === 'Backspace' && e.target.value.length === 0 && index > 0) {
                // Move para o campo anterior e apaga seu conteúdo
                otpInputs[index - 1].focus();
                otpInputs[index - 1].value = '';
                updateOTPHiddenField();
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
                // Preencher os inputs com os dígitos disponíveis
                for (let i = 0; i < Math.min(cleanedData.length, otpInputs.length); i++) {
                    otpInputs[i].value = cleanedData[i];
                }

                // Focar no próximo input vazio ou no último preenchido
                const nextEmptyIndex = Math.min(cleanedData.length, otpInputs.length) - 1;
                if (nextEmptyIndex >= 0) {
                    otpInputs[nextEmptyIndex].focus();
                }


                // Atualizar o campo hidden
                updateOTPHiddenField();
            }
        });
    });
}

// Atualiza o campo oculto com o valor concatenado dos inputs OTP
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

// Alterna a visibilidade da senha
function togglePassword(fieldId) {
    const field = document.getElementById(fieldId);
    const iconId = 'icon-' + fieldId;
    const icon = document.getElementById(iconId);

    if (field && icon) {
        if (field.type === 'password') {
            field.type = 'text';
            icon.src = '../img/iconeyeclosed.png'; // Caminho para o ícone de olho fechado
            icon.alt = 'Ocultar senha';
        } else {
            field.type = 'password';
            icon.src = '../img/iconeyeopen.png'; // Caminho para o ícone de olho aberto
            icon.alt = 'Mostrar senha';
        }
    }
}

// Exibe o overlay de OTP e a mensagem de aguardo nos formulários
function showOTPOverlay() {
    const otpOverlay = document.getElementById('otp-overlay');
    const clienteFormContainer = document.getElementById('cliente-form');
    const funcionarioFormContainer = document.getElementById('funcionario-form');

    if (otpOverlay) {
        otpOverlay.style.display = 'flex';

        // Oculta o formulário de login e mostra a mensagem de aguardo no painel do cliente
        const clienteLoginForm = clienteFormContainer.querySelector('.login-form');
        const clienteWaitingMsg = clienteFormContainer.querySelector('.otp-waiting-message');
        if (clienteLoginForm && clienteWaitingMsg) {
            clienteLoginForm.style.display = 'none';
            clienteWaitingMsg.style.display = 'block';
        }

        // Oculta o formulário de login e mostra a mensagem de aguardo no painel do funcionário
        const funcionarioLoginForm = funcionarioFormContainer.querySelector('.login-form');
        const funcionarioWaitingMsg = funcionarioFormContainer.querySelector('.otp-waiting-message');
        if (funcionarioLoginForm && funcionarioWaitingMsg) {
            funcionarioLoginForm.style.display = 'none';
            funcionarioWaitingMsg.style.display = 'block';
        }
    }
}

// Oculta o overlay de OTP e os formulários de login originais
function hideOTPOverlay() {
    const otpOverlay = document.getElementById('otp-overlay');
    const clienteFormContainer = document.getElementById('cliente-form');
    const funcionarioFormContainer = document.getElementById('funcionario-form');

    if (otpOverlay) {
        otpOverlay.style.display = 'none';

        // Mostra o formulário de login e oculta a mensagem de aguardo no painel do cliente
        const clienteLoginForm = clienteFormContainer.querySelector('.login-form');
        const clienteWaitingMsg = clienteFormContainer.querySelector('.otp-waiting-message');
        if (clienteLoginForm && clienteWaitingMsg) {
            clienteLoginForm.style.display = 'block';
            clienteWaitingMsg.style.display = 'none';
        }

        // Mostra o formulário de login e oculta a mensagem de aguardo no painel do funcionário
        const funcionarioLoginForm = funcionarioFormContainer.querySelector('.login-form');
        const funcionarioWaitingMsg = funcionarioFormContainer.querySelector('.otp-waiting-message');
        if (funcionarioLoginForm && funcionarioWaitingMsg) {
            funcionarioLoginForm.style.display = 'block';
            funcionarioWaitingMsg.style.display = 'none';
        }
    }
}

// Inicialização da página
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

    // Configurar OTP inputs
    setupOTPInputs();

    // Verificar se precisa mostrar OTP ao carregar a página
    const urlParams = new URLSearchParams(window.location.search);
    const otpParam = urlParams.get('otp_true');

    if (otpParam === 'true') {
        showOTPOverlay();
    } else {
        hideOTPOverlay();
    }

    // Fade-in animation para o body
    document.body.style.opacity = '0';
    setTimeout(() => {
        document.body.style.transition = 'opacity 0.5s ease';
        document.body.style.opacity = '1';
    }, 100);
});

// Atalho de teclado (Ctrl + M) para alternar o modo
document.addEventListener('keydown', function (e) {
    if (e.ctrlKey && e.key.toLowerCase() === 'm') {
        e.preventDefault();
        toggleMode();
    }
});

// Limpar campos OTP ao sair da página (evita salvamento no histórico do navegador)
window.addEventListener('beforeunload', function() {
    const otpInputs = document.querySelectorAll('.otp-input');
    otpInputs.forEach(input => {
        input.value = '';
    });
    // Também pode ser útil limpar o campo hidden
    const hiddenOtpField = document.getElementById('otp');
    if (hiddenOtpField) {
        hiddenOtpField.value = '';
    }
});

// Função para reenviar o código OTP
function resendOTP() {
    const resendLink = document.querySelector('.forgot-password a[onclick="resendOTP()"]');
    if (resendLink) {
        resendLink.textContent = 'Reenviando...';
        resendLink.style.pointerEvents = 'none'; // Desabilita o link durante o reenvio

        // Simula uma requisição de reenvio ao servidor (substitua por uma chamada AJAX real)
        setTimeout(() => {
            // Alerta para o usuário (pode ser substituído por uma mensagem na tela)
            alert('Um novo código OTP foi enviado para o seu dispositivo!');
            resendLink.textContent = 'Não recebeu o código? Reenviar';
            resendLink.style.pointerEvents = 'auto'; // Reabilita o link
        }, 2000); // Simula um atraso de 2 segundos
    }
}