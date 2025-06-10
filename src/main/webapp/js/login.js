
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
    icon.src = 'img/iconeyeclosed.png'; // Ícone para "ocultar senha"
    icon.alt = 'Ocultar senha';
} else {
    field.type = 'password';
    icon.src = 'img/iconeyeopen.png'; // Ícone para "mostrar senha"
    icon.alt = 'Mostrar senha';
}
};

    document.getElementById('funcionarioForm').addEventListener('submit', function(e) {
    e.preventDefault();
    alert('Login de funcionário processado!');
});

    // Smooth transitions on page load
    window.addEventListener('load', function() {
    // Define o modo inicial baseado na URL
    setInitialMode();

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