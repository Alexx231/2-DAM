document.getElementById("loginForm").addEventListener("submit", function(event) {
    event.preventDefault();
    const isLogin = document.querySelector('.register-btn').textContent === 'INICIAR SESIÓN';
    
    if (isLogin) {
        window.location.href = "list.html";
    } else {
        window.location.href = "list.html";
    }
});

document.querySelectorAll(".stars").forEach(starsContainer => {
    const stars = starsContainer.querySelectorAll(".star");
    stars.forEach((star, index) => {
        star.addEventListener("click", () => {
            starsContainer.setAttribute("data-rating", index + 1);
            stars.forEach((s, i) => {
                s.textContent = i <= index ? "★" : "☆";
            });
        });
    });
});

document.querySelector('p a').addEventListener('click', function firstClick(e) {
    e.preventDefault();
    const loginForm = document.getElementById('loginForm');
    const registerBtn = document.querySelector('.register-btn');
    const linkText = document.querySelector('p');
    const emailField = document.getElementById('email').parentNode;
    const submitBtn = document.querySelector('.submit-btn');
    
    // Remover este listener después del primer click
    e.target.removeEventListener('click', firstClick);
    
    // Aplicar la animación y cambios
    loginForm.classList.add('fade-out');
    registerBtn.classList.add('fade-out');
    linkText.classList.add('fade-out');
    
    setTimeout(() => {
        registerBtn.textContent = 'INICIAR SESIÓN';
        submitBtn.textContent = 'ENTRAR';
        linkText.innerHTML = 'o si desea <a href="#">registrarse...</a>';
        emailField.style.display = 'none';
        
        loginForm.classList.remove('fade-out');
        loginForm.classList.add('fade-in');
        registerBtn.classList.remove('fade-out');
        registerBtn.classList.add('fade-in');
        linkText.classList.remove('fade-out');
        linkText.classList.add('fade-in');
        
        // Agregar el manejador permanente
        linkText.querySelector('a').addEventListener('click', handleFormSwitch);
    }, 500);
});

// Función que maneja la animación y cambio de formulario
function handleFormSwitch(e) {
    e.preventDefault();
    const loginForm = document.getElementById('loginForm');
    const registerBtn = document.querySelector('.register-btn');
    const linkText = document.querySelector('p');
    const emailField = document.getElementById('email').parentNode;
    const submitBtn = document.querySelector('.submit-btn');
    
    // Remover clases existentes para reiniciar animación
    loginForm.classList.remove('fade-in');
    registerBtn.classList.remove('fade-in');
    linkText.classList.remove('fade-in');
    
    // Aplicar animación de salida
    loginForm.classList.add('fade-out');
    registerBtn.classList.add('fade-out');
    linkText.classList.add('fade-out');
    
    setTimeout(() => {
        // Determinar estado actual
        const isLogin = registerBtn.textContent === 'INICIAR SESIÓN';
        
        // Actualizar contenido
        registerBtn.textContent = isLogin ? 'REGISTRO' : 'INICIAR SESIÓN';
        submitBtn.textContent = isLogin ? 'REGISTRARSE' : 'ENTRAR';
        linkText.innerHTML = isLogin ? 
            'o si desea <a href="#">iniciar sesión...</a>' : 
            'o si desea <a href="#">registrarse...</a>';
        emailField.style.display = isLogin ? 'block' : 'none';
        
        // Aplicar animación de entrada
        loginForm.classList.remove('fade-out');
        loginForm.classList.add('fade-in');
        registerBtn.classList.remove('fade-out');
        registerBtn.classList.add('fade-in');
        linkText.classList.remove('fade-out');
        linkText.classList.add('fade-in');
        
        // Remover evento anterior y agregar el nuevo
        const newLink = linkText.querySelector('a');
        newLink.removeEventListener('click', handleFormSwitch);
        newLink.addEventListener('click', handleFormSwitch);
    }, 500);
}

// Asignar el manejador al enlace inicial
document.querySelector('p a').addEventListener('click', handleFormSwitch);