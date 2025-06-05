        console.log("Script starting...");

        function showDashboardHome() {
            console.log("showDashboardHome called");
            document.querySelectorAll('.functional-section').forEach(sec => sec.style.display = 'none');
            const dashboardHome = document.getElementById('dashboard-home-elements');
            if (dashboardHome) {
                dashboardHome.style.display = 'block';
                console.log("#dashboard-home-elements display set to block");
                 // Re-apply loading animations to dashboard elements if they were hidden/reset
                applyLoadingAnimations(true); // Pass a flag to indicate it's a re-show
            } else {
                console.error("#dashboard-home-elements not found in showDashboardHome");
            }
            window.scrollTo(0,0);
        }

        function navigateTo(sectionId, subSection = null) {
            console.log(`MapsTo called for section: ${sectionId}, subsection: ${subSection}`);
            const dashboardHome = document.getElementById('dashboard-home-elements');
            const targetSection = document.getElementById(sectionId);

            if (dashboardHome) dashboardHome.style.display = 'none';
            document.querySelectorAll('.functional-section').forEach(sec => sec.style.display = 'none');

            if (targetSection) {
                targetSection.style.display = 'block';
                window.scrollTo(0,0);
                if (subSection) {
                    if (sectionId === 'consulta-dados') showConsultaSubSection(subSection, false);
                    if (sectionId === 'alteracao-dados') showAlteracaoSubSection(subSection, false);
                } else {
                     if (sectionId === 'consulta-dados') document.querySelectorAll('.consulta-subsection').forEach(s => s.style.display = 'none');
                     if (sectionId === 'alteracao-dados') document.querySelectorAll('.alteracao-subsection').forEach(s => s.style.display = 'none');
                }
                // Try to get title for notification more reliably
                let notificationTitle = sectionId;
                const menuItemCard = document.querySelector(`.menu-item-card[onclick*="'${sectionId}'"] .menu-item-title`);
                if (menuItemCard) {
                    notificationTitle = menuItemCard.textContent;
                } else {
                    const functionalSectionTitle = targetSection.querySelector('.content-title');
                    if (functionalSectionTitle) notificationTitle = functionalSectionTitle.textContent;
                }
                showNotification(`Carregando: ${notificationTitle}`);
            } else {
                showNotification(`Seção '${sectionId}' não implementada. Retornando ao início.`, 'error');
                showDashboardHome();
            }
        }
        
        function handleFormReset(formId) {
            const form = document.getElementById(formId);
            if (form) {
                form.reset();
                if (formId === 'form-abertura-conta') {
                    toggleAccountFields();
                    document.getElementById('numero-conta-preview').textContent = 'Será gerado automaticamente';
                }
                 if (formId === 'form-cadastro-funcionario') {
                    document.getElementById('codigo-funcionario-preview').textContent = 'Será gerado: FUNC###';
                }
                // Add other form-specific reset actions if needed
                 // For forms with sub-sections, ensure results are hidden
                if (form.closest('.functional-section')?.id === 'consulta-dados') {
                    document.querySelectorAll('#consulta-dados .info-card').forEach(el => el.style.display = 'none');
                }
                 if (form.closest('.functional-section')?.id === 'encerramento-conta') {
                    document.getElementById('saldo-negativo-warning').style.display = 'none';
                }
                if (form.closest('.functional-section')?.id === 'relatorios') {
                    document.getElementById('resultado-relatorio').style.display = 'none';
                }


            }
        }

        function toggleAccountFields() {
            const tipo = document.getElementById('tipo-conta').value;
            document.getElementById('campos-poupanca').style.display = tipo === 'poupanca' ? 'grid' : 'none';
            document.getElementById('campos-corrente').style.display = tipo === 'corrente' ? 'grid' : 'none';
            document.getElementById('campos-investimento').style.display = tipo === 'investimento' ? 'grid' : 'none';
            if (tipo) {
                const randomNum = Math.floor(Math.random() * 90000) + 10000;
                document.getElementById('numero-conta-preview').textContent = `Exemplo: ${randomNum}-${calculateDigit(randomNum)}`;
            } else {
                document.getElementById('numero-conta-preview').textContent = 'Será gerado automaticamente';
            }
        }

        function calculateDigit(num) { return (11 - (num.toString().split('').reduce((s,d,i)=>s+(parseInt(d)*(i+2)),0)%11))%11 < 2 ? 0 : (11-(num.toString().split('').reduce((s,d,i)=>s+(parseInt(d)*(i+2)),0)%11)); }

        document.getElementById('form-abertura-conta').addEventListener('submit', function(e) {
            e.preventDefault(); if (!this.checkValidity()) { showNotification('Preencha campos obrigatórios.', 'error'); return; }
            showNotification(`Conta para ${document.getElementById('nome').value} criada!`, 'success'); handleFormReset('form-abertura-conta');
        });

        function consultarContaParaEncerramento() {
            const num = document.getElementById('numero-conta-encerrar').value; if (!num) { showNotification('Digite o núm. da conta.', 'error'); return; }
            document.getElementById('saldo-negativo-warning').style.display = Math.random() > 0.7 ? 'block' : 'none';
            showNotification(document.getElementById('saldo-negativo-warning').style.display === 'block' ? `Conta ${num} com saldo negativo!` : `Conta ${num} OK.`);
        }
        document.getElementById('form-encerramento-conta').addEventListener('submit', function(e) {
            e.preventDefault(); if (!this.checkValidity()) { showNotification('Preencha campos obrigatórios.', 'error'); return; }
            showNotification(`Conta ${document.getElementById('numero-conta-encerrar').value} encerrada.`, 'success'); handleFormReset('form-encerramento-conta');
        });

        function showConsultaSubSection(type, fromButtonClick = true) {
            document.querySelectorAll('.consulta-subsection').forEach(s => s.style.display = 'none');
            const subSectionEl = document.getElementById(`consulta-${type}-sub`);
            if (subSectionEl) subSectionEl.style.display = 'block'; else console.error(`Subseção consulta-${type}-sub não encontrada.`);
            if(fromButtonClick) showNotification(`Exibindo Consulta de ${type.charAt(0).toUpperCase() + type.slice(1)}`);
        }
        function showAlteracaoSubSection(type, fromButtonClick = true) {
            document.querySelectorAll('.alteracao-subsection').forEach(s => s.style.display = 'none');
            const subSectionEl = document.getElementById(`alteracao-${type}-sub`);
            if(subSectionEl) subSectionEl.style.display = 'block'; else console.error(`Subseção alteracao-${type}-sub não encontrada.`);
            if(fromButtonClick) showNotification(`Exibindo Alteração de ${type.charAt(0).toUpperCase() + type.slice(1)}`);
        }

        function mockFill(fields) { for (const id in fields) { const el=document.getElementById(id); if(el) el.textContent = fields[id]; else console.warn(`Element ${id} not found for mockFill`);}}
        function consultarDadosConta() {
            const num = document.getElementById('numero-conta-consulta').value; if (!num) { showNotification("Digite o núm. da conta.", 'error'); return; }
            mockFill({'consulta-conta-numero-display': `(${num})`, 'consulta-conta-tipo': 'Corrente Gold', 'consulta-conta-saldo': 'R$ 12.345,67', 'consulta-conta-limite': 'R$ 5.000,00', 'consulta-conta-projecao': 'R$ 150,00'});
            document.getElementById('historico-conta-table').querySelector('tbody').innerHTML = `<tr><td>01/06/2025</td><td>DEPOSITO</td><td>+R$500</td><td>R$11.845,67</td><td>R$12.345,67</td></tr>`;
            document.getElementById('resultado-consulta-conta').style.display = 'block'; showNotification('Dados da conta carregados.');
        }
        function consultarDadosFuncionario(){
            const cod = document.getElementById('codigo-funcionario-consulta').value; if (!cod) { showNotification("Digite o cód. funcionário.", 'error'); return; }
            mockFill({'consulta-funcionario-codigo-display': cod, 'consulta-funcionario-nome': 'Maria Oliveira', 'consulta-funcionario-cpf': '111.222.333-44', 'consulta-funcionario-nascimento': '15/08/1990', 'consulta-funcionario-telefone': '(11)98877-6655', 'consulta-funcionario-endereco': 'R. Palmeiras, 100', 'consulta-funcionario-cargo': 'Atendente Sr.', 'consulta-funcionario-desempenho': 'R$150.000,00'});
            document.getElementById('resultado-consulta-funcionario').style.display = 'block'; showNotification('Dados do funcionário consultados.');
        }
        function consultarDadosCliente(){
            const cpf = document.getElementById('cpf-cliente-consulta').value; if (!cpf) { showNotification("Digite o CPF do cliente.", 'error'); return; }
            mockFill({'consulta-cliente-cpf-display': `(CPF: ${cpf})`, 'consulta-cliente-nome-display': 'Carlos Santana', 'consulta-cliente-nascimento': '20/04/1985', 'consulta-cliente-telefone-display': '(21)97766-5544', 'consulta-cliente-endereco-display': 'Av. Copacabana, 500', 'score-credito-cliente': '750 (Bom)', 'contas-ativas-cliente': '2', 'contas-inativas-cliente': '1'});
            document.getElementById('resultado-consulta-cliente').style.display = 'block'; showNotification('Dados do cliente consultados.');
        }

        ['form-alteracao-conta', 'form-alteracao-funcionario', 'form-alteracao-cliente'].forEach(formId => {
            const form = document.getElementById(formId);
            if (form) {
                form.addEventListener('submit', function(e){
                    e.preventDefault(); if(!this.checkValidity()){showNotification('Preencha campos obrigatórios.','error');return;}
                    let idField;
                    if (formId === 'form-alteracao-conta') idField = document.getElementById('numero-conta-alterar').value;
                    else if (formId === 'form-alteracao-funcionario') idField = document.getElementById('codigo-funcionario-alterar').value;
                    else if (formId === 'form-alteracao-cliente') idField = document.getElementById('cpf-cliente-alterar').value;
                    
                    if (formId === 'form-alteracao-cliente' && document.getElementById('nova-senha-cliente').value !== document.getElementById('confirmar-nova-senha-cliente').value) {
                        showNotification('As novas senhas do cliente não coincidem!', 'error'); return;
                    }
                    showNotification(`Dados de ${idField} alterados.`, 'success'); handleFormReset(formId);
                });
            } else { console.warn(`Form ${formId} not found for event listener.`); }
        });

        document.getElementById('form-cadastro-funcionario').addEventListener('submit', function(e) {
            e.preventDefault();
            if(document.getElementById('senha-func').value !== document.getElementById('confirmar-senha-func').value){ showNotification('Senhas não coincidem!', 'error'); return; }
            if(!this.checkValidity()){ showNotification('Preencha campos obrigatórios.','error'); return; }
            const nome = document.getElementById('nome-func').value;
            const codigo = `FUNC${String(Math.floor(Math.random() * 899) + 100).padStart(3, '0')}`;
            document.getElementById('codigo-funcionario-preview').textContent = `Gerado: ${codigo}`;
            showNotification(`Funcionário ${nome} (${codigo}) cadastrado!`, 'success'); handleFormReset('form-cadastro-funcionario');
        });
        
        function gerarRelatorio(tipo) {
            if (!document.getElementById('data-inicio-rel').value || !document.getElementById('data-fim-rel').value) { showNotification('Selecione o período.', 'error'); return; }
            document.getElementById('tipo-relatorio-display').textContent = tipo.replace(/^\w/, c => c.toUpperCase());
            const head = document.getElementById('tabela-relatorio-head'); const body = document.getElementById('tabela-relatorio-body');
            head.innerHTML = ''; body.innerHTML = ''; let h=[], d=[];
            if (tipo==='movimentacoes') {h=['Data','Conta','Tipo','Valor']; d=[['03/06/25','123','Dep.','R$500'],['02/06/25','456','Saq.','R$150']];}
            else if (tipo==='inadimplencia') {h=['Cliente','CPF','Conta','Dias Atraso','Valor']; d=[['J.Silva','111','789','32','R$850']];}
            else {h=['Func/Ag.','Período','Contas Novas','Captação']; d=[['Ag.001','05/25','45','R$3.2M']];}
            const trH=head.insertRow(); h.forEach(x => trH.insertCell().textContent=x);
            d.forEach(r => { const trB=body.insertRow(); r.forEach(c => trB.insertCell().textContent=c);});
            document.getElementById('resultado-relatorio').style.display = 'block'; showNotification(`Relatório de ${tipo} gerado.`);
        }
        function exportarPDF() { if(document.getElementById('resultado-relatorio').style.display==='none'){showNotification('Gere um relatório.','warning');return;} showNotification('Exportando PDF... (simulação)');}
        function exportarExcel() { if(document.getElementById('resultado-relatorio').style.display==='none'){showNotification('Gere um relatório.','warning');return;}showNotification('Exportando Excel... (simulação)');}
        
        function logout() {
            if (confirm('Tem certeza que deseja sair?')) {
                showNotification('Fazendo logout...');
                setTimeout(() => {
                    window.location.href = '/src/main/java/WebContent/outraspaginas/login.jsp';
                    alert("Logout efetuado! (Redirecionamento para página login)");
                    showDashboardHome(); // Back to main view
                }, 1500);
            }
        }
        function showNotification(message, type = 'success') {
            const oldNotif = document.querySelector('.notification-popup'); if(oldNotif) oldNotif.remove();
            const notif = document.createElement('div'); notif.className = `notification-popup ${type}`; notif.textContent = message;
            document.body.appendChild(notif);
            setTimeout(() => { notif.style.animation = 'slideOutNotification 0.3s ease forwards'; setTimeout(() => notif.remove(), 300); }, 3000);
        }
        
        function applyLoadingAnimations(isReshow = false) {
            console.log("applyLoadingAnimations called, isReshow:", isReshow);
            const loadingElements = document.querySelectorAll('#dashboard-home-elements .loading');
            if (loadingElements.length === 0) {
                console.warn("No .loading elements found in #dashboard-home-elements for animation.");
                // If dashboard is reshown and elements are already loaded, ensure they are visible.
                 if(isReshow) {
                    document.querySelectorAll('#dashboard-home-elements > div').forEach(el => {
                        if (el.classList.contains('loading')) el.style.opacity = '1'; // Ensure visible
                    });
                }
                return;
            }
            loadingElements.forEach((element, index) => {
                // Reset animation properties to allow re-triggering with delay
                element.style.animation = 'none'; // Remove existing animation instance
                element.style.opacity = '0'; // Explicitly set to start state of animation
                
                // Force reflow to apply the 'animation: none' and 'opacity: 0' before re-applying the animation
                void element.offsetWidth; 
                
                element.style.animationDelay = `${index * 0.15}s`;
                element.style.animationName = 'fadeInAnimation'; // Ensure this matches @keyframes name
                element.style.animationDuration = '0.8s';
                element.style.animationTimingFunction = 'ease';
                element.style.animationFillMode = 'forwards';

                console.log(`Animating element ${index}:`, element, `delay: ${element.style.animationDelay}`);
            });
        }

        window.addEventListener('load', function() {
    console.log(">>> WINDOW LOADED <<<");

    const dashboardHome = document.getElementById('dashboard-home-elements');
    if (!dashboardHome) {
        console.error("FATAL ERROR: #dashboard-home-elements NOT FOUND ON PAGE LOAD!");
        alert("FATAL ERROR: #dashboard-home-elements NOT FOUND. Contact support."); // Alerta para visibilidade imediata
        return;
    }
    console.log("Found #dashboard-home-elements:", dashboardHome);

    // Teste direto de visibilidade, sem animações por enquanto
    dashboardHome.style.display = 'block';
    dashboardHome.style.opacity = '1'; // Forçar opacidade
    console.log("#dashboard-home-elements display set to block, opacity to 1 directly.");

    // Comente as outras chamadas dentro do load por enquanto para isolar o problema
    // applyLoadingAnimations();
    // console.log("applyLoadingAnimations would be called here.");

    // const today = new Date().toISOString().split('T')[0];
    // const dataInicioRel = document.getElementById('data-inicio-rel'), dataFimRel = document.getElementById('data-fim-rel');
    // if (dataInicioRel && dataFimRel) {
    //     dataInicioRel.value = new Date(new Date().getFullYear(), new Date().getMonth(), 1).toISOString().split('T')[0];
    //     dataFimRel.value = today;
    // }
    // document.querySelectorAll('input[id*="cpf"], input[id*="CPF"]').forEach(i => {i.addEventListener('input', formatCPF); i.maxLength=14;});
    // document.querySelectorAll('input[type="tel"]').forEach(i => {i.addEventListener('input', formatPhone); i.maxLength=15;});
});

console.log("Script finished parsing - END OF SCRIPT TAG.");

        document.addEventListener('keydown', function(e) {
            if (e.altKey && !e.ctrlKey && !e.shiftKey) {
                let s=null, ss=null;
                switch(e.key) {
                    case '1': s='abertura-conta'; break; case '2': s='encerramento-conta'; break;
                    case '3': s='consulta-dados'; break; case '4': s='alteracao-dados'; break;
                    case '5': s='cadastro-funcionario'; break; case '6': s='relatorios'; break;
                    case 'h': case 'H': showDashboardHome(); e.preventDefault(); return;
                }
                if (s) { e.preventDefault(); navigateTo(s, ss); }
            }
        });

        function formatCPF(e) { /* ... (masking logic as before) ... */ let v=e.target.value.replace(/\D/g,'').substring(0,11), f=''; if(v.length>9)f=v.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/,'$1.$2.$3-$4'); else if(v.length>6)f=v.replace(/(\d{3})(\d{3})(\d{1,3})/,'$1.$2.$3'); else if(v.length>3)f=v.replace(/(\d{3})(\d{1,3})/,'$1.$2'); else f=v; e.target.value=f;}
        function formatPhone(e) { /* ... (masking logic as before) ... */ let v=e.target.value.replace(/\D/g,'').substring(0,11), f=''; if(v.length>10)f=v.replace(/(\d{2})(\d{5})(\d{4})/,'($1) $2-$3'); else if(v.length>6)f=v.replace(/(\d{2})(\d{4,5})/,'($1) $2'); else if(v.length>2)f=v.replace(/(\d{2})(\d*)/,'($1) $2'); else if(v.length>0)f=v.replace(/(\d*)/,'($1'); e.target.value=f;}

        console.log("Script finished parsing.");