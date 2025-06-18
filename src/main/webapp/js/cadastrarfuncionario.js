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

// Máscara para CPF (apenas visual) - limitado a 11 dígitos
document.getElementById('cpf').addEventListener('input', function(e) {
    let value = e.target.value.replace(/\D/g, '');
    value = value.substring(0, 11); // Limita a 11 dígitos
    value = value.replace(/(\d{3})(\d)/, '$1.$2');
    value = value.replace(/(\d{3})(\d)/, '$1.$2');
    value = value.replace(/(\d{3})(\d{1,2})$/, '$1-$2');
    e.target.value = value;
});

// Máscara para telefone (apenas visual)
document.getElementById('telefone').addEventListener('input', function(e) {
    let value = e.target.value.replace(/\D/g, '');
    value = value.replace(/(\d{2})(\d)/, '($1) $2');
    value = value.replace(/(\d{4,5})(\d{4})$/, '$1-$2');
    e.target.value = value;
});

// Máscara para CEP (apenas visual)
document.getElementById('cep').addEventListener('input', function(e) {
    let value = e.target.value.replace(/\D/g, '');
    value = value.replace(/(\d{5})(\d)/, '$1-$2');
    e.target.value = value;
});

// Remove máscaras antes do envio do formulário
document.querySelector('form').addEventListener('submit', function(e) {
    // Remove máscara do CPF
    const cpfField = document.getElementById('cpf');
    cpfField.value = cpfField.value.replace(/\D/g, '');

    // Remove máscara do telefone
    const telefoneField = document.getElementById('telefone');
    telefoneField.value = telefoneField.value.replace(/\D/g, '');

    // Remove máscara do CEP
    const cepField = document.getElementById('cep');
    cepField.value = cepField.value.replace(/\D/g, '');
});