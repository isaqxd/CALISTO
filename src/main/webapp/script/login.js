let isFuncionarioMode = false;

function toggleMode() {
    const body = document.body;
    isFuncionarioMode = !isFuncionarioMode;

    if (isFuncionarioMode) {
        body.classList.add('funcionario-mode');
        updateContentForFuncionario();
    } else {
        body.classList.remove('funcionario-mode');
        updateContentForCliente();
    }
}

function updateContentForFuncionario() {
    document.getElementById('welcome-subtitle').textContent = 'Portal do Funcionário';
    document.getElementById('welcome-description').textContent = 'Acesso restrito para colaboradores';
    document.getElementById('toggle-text').textContent = 'Acesso para clientes?';
    document.getElementById('toggle-btn').textContent = 'Acesso do Cliente';
    document.getElementById('form-title').textContent = 'Login Funcionário';
    document.getElementById('identifier-label').textContent = 'Código do Funcionário';
    document.getElementById('identifier-input').placeholder = 'FUN00000';
    document.getElementById('login-btn').textContent = 'Entrar como Funcionário';
    document.getElementById('loginForm').reset();
}

function updateContentForCliente() {
    document.getElementById('welcome-subtitle').textContent = 'Acesso do Cliente';
    document.getElementById('welcome-description').textContent = 'Gerencie suas finanças com segurança';
    document.getElementById('toggle-text').textContent = 'Acesso para funcionários?';
    document.getElementById('toggle-btn').textContent = 'Portal do Funcionário';
    document.getElementById('form-title').textContent = 'Login Cliente';
    document.getElementById('identifier-label').textContent = 'CPF';
    document.getElementById('identifier-input').placeholder = '000.000.000-00';
    document.getElementById('loginForm').reset();
}

function togglePassword() {
    const field = document.getElementById('password-input');
    const icon = field.nextElementSibling;

    if (field.type === 'password') {
        field.type = 'text';
        icon.textContent = '🙈';
    } else {
        field.type = 'password';
        icon.textContent = '👁️';
    }
}

function formatCPF(cpf) {
    // Limit to 11 digits before formatting
    cpf = cpf.replace(/\D/g, '').substring(0, 11);
    if (cpf.length > 3) cpf = cpf.replace(/(\d{3})(\d)/, '$1.$2');
    if (cpf.length > 6) cpf = cpf.replace(/(\d{3})(\d)/, '$1.$2');
    if (cpf.length > 9) cpf = cpf.replace(/(\d{3})(\d{1,2})$/, '$1-$2');
    return cpf;
}

// Identifier input formatting
document.getElementById('identifier-input').addEventListener('input', function (e) {
    if (!isFuncionarioMode) {
        e.target.value = formatCPF(e.target.value);
    } else {
        let value = e.target.value.toUpperCase().replace(/[^A-Z0-9]/g, '');
        if (!value.startsWith('FUN')) {
            value = 'FUN' + value.replace(/\D/g, '');
        }
        e.target.value = value.substring(0, 8);
    }
});

// Find OTP section in a more compatible way
function findOTPSection() {
    // First try to find by a specific ID if available
    let otpSection = document.getElementById('otp-section');

    // If not found by ID, try to find by structure
    if (!otpSection) {
        const formGroups = document.querySelectorAll('.form-group');
        for (const group of formGroups) {
            if (group.querySelector('.otp-container')) {
                otpSection = group;
                break;
            }
        }
    }

    return otpSection;
}

// Hide OTP section initially
function hideOTPSection() {
    const otpGroup = findOTPSection();
    if (otpGroup) {
        otpGroup.style.display = 'none';
    }
}

// Show OTP section after successful login
function showOTPSection() {
    const otpGroup = findOTPSection();
    if (otpGroup) {
        otpGroup.style.display = 'block';
    }

    // Focus on first OTP input
    const firstOtpInput = document.getElementById('otp1');
    if (firstOtpInput) {
        firstOtpInput.focus();
    }

    // Setup OTP input behavior
    setupOTPInputs();
}

// Setup OTP input behavior (auto-focus next input)
function setupOTPInputs() {
    const otpInputs = document.querySelectorAll('.otp-input');

    otpInputs.forEach((input, index) => {
        // When a digit is entered, move to next input
        input.addEventListener('input', function() {
            if (this.value.length === 1) {
                if (index < otpInputs.length - 1) {
                    otpInputs[index + 1].focus();
                }
            }
        });

        // Handle backspace
        input.addEventListener('keydown', function(e) {
            if (e.key === 'Backspace' && this.value.length === 0 && index > 0) {
                otpInputs[index - 1].focus();
            }
        });
    });
}

// Validate OTP
function validateOTP() {
    const otpInputs = document.querySelectorAll('.otp-input');
    let otp = '';

    otpInputs.forEach(input => {
        otp += input.value;
    });

    // Check if OTP is complete (6 digits)
    if (otp.length !== 6 || !/^\d{6}$/.test(otp)) {
        document.getElementById('error-message').textContent = 'Por favor, insira um código OTP válido de 6 dígitos.';
        return false;
    }

    // Here you would normally validate the OTP with a server
    // For now, we'll just simulate a successful validation
    document.getElementById('success-message').textContent = 'OTP validado com sucesso!';
    document.getElementById('error-message').textContent = '';
    return true;
}

// Form submission - with login verification and OTP
document.getElementById('loginForm').addEventListener('submit', function (e) {
    e.preventDefault();

    const identifier = document.getElementById('identifier-input').value;
    const password = document.getElementById('password-input').value;
    const otpGroup = findOTPSection();

    // Check if OTP section is visible
    if (otpGroup && otpGroup.style.display !== 'none') {
        // OTP validation phase
        if (validateOTP()) {
            // Submit the form or redirect
            console.log('Login and OTP validated successfully');
            this.submit();
        }
    } else {
        // Initial login phase
        if (identifier && password) {
            // Here you would normally validate credentials with a server
            // For now, we'll just simulate a successful login
            console.log('Login credentials verified');
            document.getElementById('success-message').textContent = 'Credenciais verificadas. Por favor, insira o código OTP.';
            document.getElementById('error-message').textContent = '';

            // Show OTP section for second factor authentication
            showOTPSection();

            // Change button text
            document.querySelector('.login-btn').textContent = 'Verificar OTP';
        } else {
            document.getElementById('error-message').textContent = 'Por favor, preencha todos os campos.';
            document.getElementById('success-message').textContent = '';
        }
    }
});

// Initialize
window.addEventListener('load', function () {
    // Hide OTP section initially
    hideOTPSection();

    // Fade-in animation
    document.body.style.opacity = '0';
    setTimeout(() => {
        document.body.style.transition = 'opacity 0.5s ease';
        document.body.style.opacity = '1';
    }, 100);
});

// Keyboard shortcut for toggling mode
document.addEventListener('keydown', function (e) {
    if (e.key === 'Tab' && e.shiftKey && e.ctrlKey) {
        e.preventDefault();
        toggleMode();
    }
});
