document.addEventListener("DOMContentLoaded", function() {
    const loginForm = document.getElementById("loginForm");

    loginForm.addEventListener("submit", function(event) {
        event.preventDefault();

        const email = document.getElementById("email").value;
        const password = document.getElementById("password").value;

        if (email && password) {
            window.location.href = "interfaz.html";
        } else {
            alert("Por favor, ingrese un correo electrónico y una contraseña válidos.");
        }
    });

    loginForm.addEventListener("keydown", function(event) {
        if (event.key === "Enter") {
            event.preventDefault(); // Evita el comportamiento predeterminado del Enter
            const email = document.getElementById("email").value;
            const password = document.getElementById("password").value;

            if (email && password) {
                window.location.href = "interfaz.html"; // Redirige si los campos están llenos
            } else {
                alert("Por favor, ingrese un correo electrónico y una contraseña válidos.");
            }
        }
    });
});