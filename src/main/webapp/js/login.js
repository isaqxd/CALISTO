let isFuncionarioMode = false;

// Função para detectar qual modo usar baseado na URL
function detectModeFromURL() {
    const urlParams = new URLSearchParams(window.location.search);
    const mode = urlParams.get('mode');
    const hash = window.location.hash;

    // Verifica parâmetro ?mode=funcionario ou ?mode=employee
    if (mode === 'funcionario' || mode === 'employee') {
        return true;
    }

    // Verifica hash #funcionario ou #employee
    if (hash === '#funcionario' || hash === '#employee') {
        return true;
    }

    // Verifica se a URL contém palavras-chave
    const url = window.location.href.toLowerCase();
    if (url.includes('funcionario') || url.includes('employee') || url.includes('staff')) {
        return true;
    }

    return false; // Por padrão, cliente
}

// Função para definir o modo inicial
function setInitialMode() {
    const shouldBeFuncionarioMode = detectModeFromURL();

    if (shouldBeFuncionarioMode) {
        isFuncionarioMode = true;
        document.body.classList.remove('cliente-mode');
        document.body.classList.add('funcionario-mode');
    } else {
        isFuncionarioMode = false;
        document.body.classList.remove('funcionario-mode');
        document.body.classList.add('cliente-mode');
    }
}

function toggleMode() {
    const body = document.body;
    isFuncionarioMode = !isFuncionarioMode;

    if (isFuncionarioMode) {
        body.classList.remove('cliente-mode');
        body.classList.add('funcionario-mode');
        // Opcionalmente, atualiza a URL
        history.replaceState(null, null, '?mode=funcionario');
    } else {
        body.classList.remove('funcionario-mode');
        body.classList.add('cliente-mode');
        // Opcionalmente, atualiza a URL
        history.replaceState(null, null, '?mode=cliente');
    }
}

function togglePassword(fieldId) {
    const field = document.getElementById(fieldId);
    const icon = document.getElementById('icon-' + fieldId);

    if (field.type === 'password') {
        field.type = 'text';
        icon.src = '/src/main/webapp/imagens/iconeyeopen.png'; // Ícone para "ocultar senha"
        icon.alt = 'Ocultar senha';
    } else {
        field.type = 'password';
        icon.src = '/src/main/webapp/imagens/iconeyeclosed.png'; // Ícone para "mostrar senha"
        icon.alt = 'Mostrar senha';
    }
}

// Form submissions
document.getElementById('clienteForm').addEventListener('submit', function(e) {
    e.preventDefault();
    alert('Login de cliente processado!');
});

// Login do funcionário por código e senha
document.addEventListener('DOMContentLoaded', function() {
    // Garante cadastro prévio do gerente Luís Eduardo
    let funcionarios = JSON.parse(sessionStorage.getItem('funcionarios')) || [];
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

    // Login do funcionário por código e senha
    const funcionarioForm = document.getElementById('funcionarioForm');
    if (funcionarioForm) {
        funcionarioForm.onsubmit = function(e) {
            e.preventDefault();
            const codigo = document.getElementById('funcionarioCodigo').value.trim().toUpperCase();
            const senha = document.getElementById('funcionarioPassword').value.trim();

            let funcionarios = JSON.parse(sessionStorage.getItem('funcionarios')) || [];
            const funcionario = funcionarios.find(f => f.codigo === codigo && f.senha === senha);
            if (!funcionario) {
                alert('Credencial ou senha inválida.');
                return;
            }

            // Salva o login e redireciona para o portal do funcionário
            sessionStorage.setItem('funcionarioLogado', JSON.stringify(funcionario));
            window.location.href = '/src/main/webapp/outraspaginas/portalfuncionario.jsp';
            // ou, se não funcionar, tente:
            // window.location.href = 'outraspaginas/portalfuncionario.jsp';
        };
    }

    // Define o modo inicial baseado na URL
    setInitialMode();
});

// Smooth transitions on page load
window.addEventListener('load', function() {
    document.body.style.opacity = '0';
    setTimeout(() => {
        document.body.style.transition = 'opacity 0.5s ease';
        document.body.style.opacity = '1';
    }, 100);
});

// Add keyboard navigation
document.addEventListener('keydown', function(e) {
    if (e.key === 'Tab' && e.shiftKey && e.ctrlKey) {
        e.preventDefault();
        toggleMode();
    }
});

// Add subtle hover effects to form inputs
document.querySelectorAll('.form-input').forEach(input => {
    input.addEventListener('focus', function() {
        this.parentElement.style.transform = 'translateY(-2px)';
    });

    input.addEventListener('blur', function() {
        this.parentElement.style.transform = 'translateY(0)';
    });
});