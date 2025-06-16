// Função para alternar submenus
document.addEventListener('DOMContentLoaded', function () {
    const menuToggles = document.querySelectorAll('.menu-toggle');
    const menuItems = document.querySelectorAll('.menu-item');
    const sections = document.querySelectorAll('.section');

    // Toggle submenus
    menuToggles.forEach(toggle => {
        toggle.addEventListener('click', function (e) {
            e.preventDefault();
            const submenuId = this.getAttribute('data-submenu');
            const submenu = document.getElementById(submenuId);

            // Fechar outros submenus
            document.querySelectorAll('.submenu').forEach(menu => {
                if (menu.id !== submenuId) {
                    menu.classList.remove('active');
                }
            });

            // Toggle submenu atual
            submenu.classList.toggle('active');
        });
    });

    // Navegação entre seções
    menuItems.forEach(item => {
        item.addEventListener('click', function (e) {
            e.preventDefault();
            const sectionId = this.getAttribute('data-section');

            if (sectionId) {
                // Remover classe active de todos os itens de menu
                menuItems.forEach(menuItem => {
                    menuItem.classList.remove('active');
                });

                // Adicionar classe active ao item clicado
                this.classList.add('active');

                // Ocultar todas as seções
                sections.forEach(section => {
                    section.classList.remove('active');
                });

                // Mostrar seção correspondente
                const targetSection = document.getElementById(sectionId);
                if (targetSection) {
                    targetSection.classList.add('active');
                }
            }
        });
    });

    // Definir ano atual no footer
    document.getElementById('currentYear').textContent = new Date().getFullYear();
});