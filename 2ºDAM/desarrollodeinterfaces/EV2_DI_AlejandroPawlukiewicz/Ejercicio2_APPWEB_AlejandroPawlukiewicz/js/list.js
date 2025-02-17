// Datos de los animes
const animeData = {
    animes: [
        {
            "nombre": "Fullmetal Alchemist: Brotherhood",
            "descripcion": "Dos hermanos buscan la piedra filosofal para recuperar sus cuerpos después de un fallido intento de resucitar a su madre usando alquimia.",
            "fechaPublicacion": "2009-04-05",
            "edadRecomendada": 14,
            "autor": "Hiromu Arakawa",
            "editorial": "Square Enix",
            "generos": ["Acción", "Aventura", "Drama", "Fantasía"],
            "imagen": "../img/fma.jpg",
            "tipo": "En posesion"
        },
        {
            "nombre": "Death Note",
            "descripcion": "Un estudiante encuentra un cuaderno sobrenatural que le permite matar a cualquier persona cuyo nombre escriba en él.",
            "fechaPublicacion": "2006-10-03",
            "edadRecomendada": 16,
            "autor": "Tsugumi Ohba",
            "editorial": "Shueisha",
            "generos": ["Misterio", "Sobrenatural", "Thriller", "Psicológico"],
            "imagen": "../img/deathnote.jpg",
            "tipo": "En uso"
        },
        {
            "nombre": "Attack on Titan",
            "descripcion": "La humanidad lucha por sobrevivir contra gigantes que devoran humanos dentro de ciudades rodeadas por enormes muros.",
            "fechaPublicacion": "2013-04-07",
            "edadRecomendada": 16,
            "autor": "Hajime Isayama",
            "editorial": "Kodansha",
            "generos": ["Acción", "Drama", "Fantasía", "Misterio"],
            "imagen": "../img/aot.jpg",
            "tipo": "Lista de deseados"
        },
        {
            "nombre": "My Hero Academia",
            "descripcion": "En un mundo donde el 80% de la población tiene superpoderes, un joven sin poderes lucha por convertirse en un héroe.",
            "fechaPublicacion": "2016-04-03",
            "edadRecomendada": 12,
            "autor": "Kohei Horikoshi",
            "editorial": "Shueisha",
            "generos": ["Acción", "Comedia", "Superpoderes"],
            "imagen": "../img/mha.jpg",
            "tipo": "En uso"
        },
        {
            "nombre": "Demon Slayer",
            "descripcion": "Un joven se convierte en cazador de demonios después de que su familia es asesinada y su hermana transformada en demonio.",
            "fechaPublicacion": "2019-04-06",
            "edadRecomendada": 14,
            "autor": "Koyoharu Gotouge",
            "editorial": "Shueisha",
            "generos": ["Acción", "Sobrenatural", "Drama"],
            "imagen": "../img/demonslayer.jpg",
            "tipo": "En posesion"
        },
        {
            "nombre": "Jujutsu Kaisen",
            "descripcion": "Estudiantes de una escuela de hechicería luchan contra maldiciones usando energía maldita.",
            "fechaPublicacion": "2020-10-03",
            "edadRecomendada": 16,
            "autor": "Gege Akutami",
            "editorial": "Shueisha",
            "generos": ["Acción", "Sobrenatural", "Horror"],
            "imagen": "../img/jjk.jpg",
            "tipo": "Lista de deseados"
        },
        {
            "nombre": "Spy x Family",
            "descripcion": "Un espía, una asesina y una telépata forman una familia falsa para cumplir una misión de paz mundial.",
            "fechaPublicacion": "2022-04-09",
            "edadRecomendada": 12,
            "autor": "Tatsuya Endo",
            "editorial": "Shogakukan",
            "generos": ["Comedia", "Acción", "Slice of Life"],
            "imagen": "../img/spyxfamily.jpg",
            "tipo": "En posesion"
        },
        {
            "nombre": "Chainsaw Man",
            "descripcion": "Un joven hace un pacto con un demonio motosierra para cazar otros demonios y pagar las deudas de su padre.",
            "fechaPublicacion": "2022-10-12",
            "edadRecomendada": 17,
            "autor": "Tatsuki Fujimoto",
            "editorial": "Shueisha",
            "generos": ["Acción", "Horror", "Sobrenatural"],
            "imagen": "../img/chainsawman.jpg",
            "tipo": "En uso"
        },
        {
            "nombre": "One Punch Man",
            "descripcion": "Un superhéroe que puede derrotar a cualquier enemigo de un solo golpe busca un verdadero desafío.",
            "fechaPublicacion": "2015-10-05",
            "edadRecomendada": 14,
            "autor": "ONE",
            "editorial": "Shogakukan",
            "generos": ["Acción", "Comedia", "Superpoderes"],
            "imagen": "../img/opm.jpg",
            "tipo": "Lista de deseados"
        },
        {
            "nombre": "Tokyo Revengers",
            "descripcion": "Un joven viaja al pasado para salvar a su ex novia y cambiar su futuro.",
            "fechaPublicacion": "2021-04-11",
            "edadRecomendada": 16,
            "autor": "Ken Wakui",
            "editorial": "Kodansha",
            "generos": ["Acción", "Drama", "Viajes en el tiempo"],
            "imagen": "../img/tokyorevengers.jpg",
            "tipo": "En posesion"
        }
    ]
};

// Función para crear el elemento HTML de un anime
function createAnimeElement(anime) {
    return `
        <div class="anime-item" data-tipo="${anime.tipo}">
            <img src="${anime.imagen}" alt="${anime.nombre}">
            <div class="anime-info">
                <h2>${anime.nombre}</h2>
                <p>Cap: ${anime.capitulos}</p>
                <div class="stars" data-rating="${anime.rating}">
                    ${createStars(anime.rating)}
                </div>
            </div>
        </div>
    `;
}

// Función para crear las estrellas de rating
function createStars(rating) {
    return Array(5).fill('').map((_, i) => 
        `<span class="star">${i < rating ? '★' : '☆'}</span>`
    ).join('');
}

// Función para filtrar animes por tipo
function filterAnimes(tipo) {
    const animeList = document.querySelector('.anime-list');
    const filteredAnimes = animeData.animes.filter(anime => anime.tipo === tipo);
    animeList.innerHTML = filteredAnimes.map(createAnimeElement).join('');
    setupStarRatings();
}

// Función para configurar el sistema de rating
function setupStarRatings() {
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
}

// Event Listeners
document.addEventListener('DOMContentLoaded', () => {
    // Setup de botones de filtrado
    const buttons = document.querySelectorAll('.tabs button');
    buttons.forEach(button => {
        button.addEventListener('click', (e) => {
            // Remover clase activa de todos los botones
            buttons.forEach(b => b.classList.remove('active'));
            // Agregar clase activa al botón clickeado
            e.target.classList.add('active');
            // Filtrar animes
            filterAnimes(e.target.textContent);
        });
    });

    // Mostrar "En posesion" por defecto
    filterAnimes('En posesion');
});