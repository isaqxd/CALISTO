// Header scroll effect e Footer visibility
window.addEventListener('scroll', () => {
    const header = document.getElementById('header');
    const footer = document.querySelector('.footer');

    if (window.scrollY > 50) {
        header.classList.add('scrolled');
        footer.classList.add('visible');
    } else {
        header.classList.remove('scrolled');
        footer.classList.remove('visible');
    }
});

// Enhanced hover effects
document.querySelectorAll('.selection-card').forEach(card => {
    card.addEventListener('mouseenter', function() {
        // Add pulse effect to icon
        const icon = this.querySelector('.card-icon');
        icon.style.animation = 'pulse 1s infinite';
    });

    card.addEventListener('mouseleave', function() {
        const icon = this.querySelector('.card-icon');
        icon.style.animation = 'none';
    });
});

// Add pulse animation
const style = document.createElement('style');
style.textContent = `
            @keyframes pulse {
                0% { transform: scale(1); }
                50% { transform: scale(1.1); }
                100% { transform: scale(1); }
            }
        `;
document.head.appendChild(style);

// Add click feedback with sound simulation
document.querySelectorAll('.selection-card').forEach(card => {
    card.addEventListener('click', function() {
        this.style.transform = 'scale(0.98)';
        setTimeout(() => {
            this.style.transform = 'scale(1.02)';
        }, 100);
        setTimeout(() => {
            this.style.transform = '';
        }, 200);
    });
});

// Smooth scroll for anchor links
document.querySelectorAll('a[href^="#"]').forEach(anchor => {
    anchor.addEventListener('click', function (e) {
        if (this.getAttribute('href') === '#login' || this.getAttribute('href') === '#') return;

        e.preventDefault();
        const target = document.querySelector(this.getAttribute('href'));
        if (target) {
            target.scrollIntoView({
                behavior: 'smooth',
                block: 'start'
            });
        }
    });
});