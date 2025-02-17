// Función para cargar los datos
async function loadAnimeData() {
    try {
        const response = await fetch('../json/animes.json'); // Corregimos la ruta
        const data = await response.json();
        return data;
    } catch (error) {
        console.error('Error cargando los datos:', error);
        return { animes: [] };
    }
}

// Función para crear estrellas
function createStars(rating) {
    let stars = '';
    for (let i = 0; i < 5; i++) {
        stars += `<span class="star">${i < rating ? '★' : '☆'}</span>`;
    }
    return stars;
}

// Función para crear el elemento HTML de un anime
function createAnimeElement(anime) {
    return `
        <div class="anime-item" data-tipo="${anime.tipo}">
            <img src="${anime.imagen}" alt="${anime.nombre}">
            <div class="anime-info">
                <h2>${anime.nombre}</h2>
                <div class="anime-details">
                    <p><strong>Autor:</strong> ${anime.autor}</p>
                    <p><strong>Editorial:</strong> ${anime.editorial}</p>
                    <p><strong>Fecha:</strong> ${new Date(anime.fechaPublicacion).toLocaleDateString()}</p>
                    <p><strong>Edad:</strong> +${anime.edadRecomendada}</p>
                    <p><strong>Géneros:</strong> ${anime.generos.join(', ')}</p>
                    <p class="descripcion">${anime.descripcion}</p>
                </div>
                <div class="stars" data-rating="0">
                    ${createStars(0)}
                </div>
            </div>
        </div>
    `;
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

// Función para filtrar animes
async function filterAnimes(tipo = '') {
    const animeList = document.querySelector('.anime-list');
    const data = await loadAnimeData();
    
    const filteredAnimes = tipo && tipo !== 'Todos' ? 
        data.animes.filter(anime => anime.tipo === tipo) :
        data.animes;
        
    animeList.innerHTML = filteredAnimes.map(createAnimeElement).join('');
    setupStarRatings();
}

// Event Listeners
document.addEventListener('DOMContentLoaded', async () => {
    const buttons = document.querySelectorAll('.tabs button');
    
    buttons.forEach(button => {
        button.addEventListener('click', (e) => {
            buttons.forEach(b => b.classList.remove('active'));
            e.target.classList.add('active');
            
            const tipo = e.target.textContent;
            filterAnimes(tipo);
        });
    });

    await filterAnimes('Todos');
});