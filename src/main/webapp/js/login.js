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
            
            // Clear form
            document.getElementById('loginForm').reset();
            clearOTPInputs();
        }

        function updateContentForCliente() {
            document.getElementById('welcome-subtitle').textContent = 'Acesso do Cliente';
            document.getElementById('welcome-description').textContent = 'Gerencie suas finanças com segurança';
            document.getElementById('toggle-text').textContent = 'Acesso para funcionários?';
            document.getElementById('toggle-btn').textContent = 'Portal do Funcionário';
            document.getElementById('form-title').textContent = 'Login Cliente';
            document.getElementById('identifier-label').textContent = 'CPF';
            document.getElementById('identifier-input').placeholder = '000.000.000-00';
            document.getElementById('login-btn').textContent = 'Entrar como Cliente';
            
            // Clear form
            document.getElementById('loginForm').reset();
            clearOTPInputs();
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
            cpf = cpf.replace(/\D/g, '');
            cpf = cpf.replace(/(\d{3})(\d)/, '$1.$2');
            cpf = cpf.replace(/(\d{3})(\d)/, '$1.$2');
            cpf = cpf.replace(/(\d{3})(\d{1,2})$/, '$1-$2');
            return cpf;
        }

        function setupOTPInputs() {
            const otpContainer = document.querySelector('.otp-container');
            otpContainer.addEventListener('input', function(e) {
                const target = e.target;
                if (target.matches('.otp-input') && target.value.length === 1) {
                    const nextInput = target.nextElementSibling;
                    if (nextInput) {
                        nextInput.focus();
                    }
                }
            });

            otpContainer.addEventListener('keydown', function(e) {
                const target = e.target;
                if (e.key === 'Backspace' && target.matches('.otp-input') && target.value === '') {
                    const prevInput = target.previousElementSibling;
                    if (prevInput) {
                        prevInput.focus();
                    }
                }
            });
        }

        function clearOTPInputs() {
            document.querySelectorAll('.otp-input').forEach(input => input.value = '');
        }

        function showMessage(type, message) {
            const errorDiv = document.getElementById('error-message');
            const successDiv = document.getElementById('success-message');
            
            // Hide both first
            errorDiv.style.display = 'none';
            successDiv.style.display = 'none';

            const messageDiv = type === 'error' ? errorDiv : successDiv;
            messageDiv.textContent = message;
            messageDiv.style.display = 'block';
            
            setTimeout(() => {
                messageDiv.style.display = 'none';
            }, 5000);
        }

        // Identifier input formatting
        document.getElementById('identifier-input').addEventListener('input', function(e) {
            if (!isFuncionarioMode) {
                e.target.value = formatCPF(e.target.value).substring(0, 14);
            } else {
                let value = e.target.value.toUpperCase().replace(/[^A-Z0-9]/g, '');
                if (!value.startsWith('FUN')) {
                    value = 'FUN' + value.replace(/\D/g, '');
                }
                e.target.value = value.substring(0, 8);
            }
        });

        // Form submission
        document.getElementById('loginForm').addEventListener('submit', function(e) {
            e.preventDefault();
            
            const identifier = document.getElementById('identifier-input').value;
            const password = document.getElementById('password-input').value;
            
            // Simple validation for demo
            if (!identifier || !password) {
                showMessage('error', 'Por favor, preencha todos os campos.');
                return;
            }
            
            showMessage('success', 'Login simulado com sucesso! Redirecionando...');
            
            // In a real app, you would send data to the server here.
            setTimeout(() => {
                console.log('Redirecionando para:', isFuncionarioMode ? 'Portal do Funcionário' : 'Dashboard do Cliente');
            }, 2000);
        });

        // Initialize
        window.addEventListener('load', function() {
            setupOTPInputs();
            
            // Smooth page load
            document.body.style.opacity = '0';
            setTimeout(() => {
                document.body.style.transition = 'opacity 0.5s ease';
                document.body.style.opacity = '1';
            }, 100);
        });

        // Keyboard shortcut for toggling mode
        document.addEventListener('keydown', function(e) {
            if (e.key === 'Tab' && e.shiftKey && e.ctrlKey) {
                e.preventDefault();
                toggleMode();
            }
        });

        console.log('🏦 Callisto Bank - Login System Initialized');