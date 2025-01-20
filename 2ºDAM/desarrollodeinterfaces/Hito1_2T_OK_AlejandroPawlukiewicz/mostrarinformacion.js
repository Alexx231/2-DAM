const atracciones = [
    {
        "nombre": "Montaña Rusa del Terror",
        "descripción": "Una emocionante montaña rusa con giros y caídas vertiginosas.",
        "ubicación": "Zona Maquinismo",
        "edad": 12,
        "altura": 140,
        "tiempo": "2 minutos",
        "horario": "10:00 - 20:00",
        "imagen": "ruta_a_imagen/montana_rusa.jpg",
        "tipo": "Montaña Rusa"
    },
    {
        "nombre": "Cataratas Mágicas",
        "descripción": "Un recorrido acuático lleno de sorpresas y aventuras.",
        "ubicación": "Zona Naturaleza",
        "edad": 6,
        "altura": 100,
        "tiempo": "5 minutos",
        "horario": "10:00 - 19:00",
        "imagen": "ruta_a_imagen/cataratas.jpg",
        "tipo": "Acuático"
    },
    {
        "nombre": "Teatro de los Sueños",
        "descripción": "Un espectáculo lleno de magia y entretenimiento para toda la familia.",
        "ubicación": "Zona Central",
        "edad": 0,
        "altura": 0,
        "tiempo": "30 minutos",
        "horario": "12:00 - 18:00",
        "imagen": "ruta_a_imagen/teatro.jpg",
        "tipo": "Espectáculo"
    },
    {
        "nombre": "Río Tranquilo",
        "descripción": "Un paseo relajante por un río lleno de naturaleza.",
        "ubicación": "Zona Naturaleza",
        "edad": 3,
        "altura": 0,
        "tiempo": "10 minutos",
        "horario": "10:00 - 19:00",
        "imagen": "ruta_a_imagen/rio_tranquilo.jpg",
        "tipo": "Acuático"
    },
    {
        "nombre": "Laberinto Encantado",
        "descripción": "Un desafío divertido para encontrar la salida en este laberinto mágico.",
        "ubicación": "Zona Central",
        "edad": 5,
        "altura": 0,
        "tiempo": "15 minutos",
        "horario": "10:00 - 20:00",
        "imagen": "ruta_a_imagen/laberinto.jpg",
        "tipo": "Atracción Temática"
    },
    {
        "nombre": "Torre del Vertigo",
        "descripción": "Una caída libre desde lo más alto para los más valientes.",
        "ubicación": "Zona Maquinismo",
        "edad": 14,
        "altura": 150,
        "tiempo": "3 minutos",
        "horario": "11:00 - 20:00",
        "imagen": "ruta_a_imagen/torre_vertigo.jpg",
        "tipo": "Montaña Rusa"
    },
    {
        "nombre": "Zona Infantil Aventura",
        "descripción": "Un área segura y divertida para los más pequeños.",
        "ubicación": "Zona Infantil",
        "edad": 0,
        "altura": 0,
        "tiempo": "Ilimitado",
        "horario": "10:00 - 19:00",
        "imagen": "ruta_a_imagen/zona_infantil.jpg",
        "tipo": "Zona Infantil"
    },
    {
        "nombre": "Simulador Espacial",
        "descripción": "Experimenta un viaje a través del espacio en este simulador interactivo.",
        "ubicación": "Zona Futurista",
        "edad": 10,
        "altura": 130,
        "tiempo": "7 minutos",
        "horario": "10:00 - 18:00",
        "imagen": "ruta_a_imagen/simulador.jpg",
        "tipo": "Atracción Tecnológica"
    },
    {
        "nombre": "Safari Salvaje",
        "descripción": "Una aventura entre animales animatrónicos en su hábitat natural.",
        "ubicación": "Zona Naturaleza",
        "edad": 8,
        "altura": 120,
        "tiempo": "10 minutos",
        "horario": "10:00 - 18:30",
        "imagen": "ruta_a_imagen/safari.jpg",
        "tipo": "Atracción Temática"
    },
    {
        "nombre": "Carrusel Clásico",
        "descripción": "El clásico carrusel con caballos para toda la familia.",
        "ubicación": "Zona Central",
        "edad": 0,
        "altura": 0,
        "tiempo": "5 minutos",
        "horario": "10:00 - 20:00",
        "imagen": "ruta_a_imagen/carrusel.jpg",
        "tipo": "Atracción Infantil"
    }
]
;

function mostrarAtraccion(nombre) {
    const atraccion = atracciones.find(a => a.nombre === nombre);
    const modal = document.querySelector('ion-modal');
    const title = document.getElementById('modal-title');
    const content = document.getElementById('modal-content');
    
    title.textContent = atraccion.nombre;
    content.innerHTML = `
        <div class="ion-padding">
            <img src="${atraccion.imagen}" alt="${atraccion.nombre}" style="width: 100%; max-width: 300px;">
            <h2>${atraccion.nombre}</h2>
            <p>${atraccion.descripción}</p>
            <p><strong>Ubicación:</strong> ${atraccion.ubicación}</p>
            <p><strong>Edad mínima:</strong> ${atraccion.edad} años</p>
            <p><strong>Altura mínima:</strong> ${atraccion.altura}cm</p>
            <p><strong>Duración:</strong> ${atraccion.tiempo}</p>
            <p><strong>Horario:</strong> ${atraccion.horario}</p>
            <p><strong>Tipo:</strong> ${atraccion.tipo}</p>
        </div>
    `;
    
    modal.present();

    const closeButton = modal.querySelector('ion-button');
    closeButton.addEventListener('click', () => {
        modal.dismiss();
    });
    
}

function inicializarPuntos() {
    const mapContainer = document.querySelector('.map-container');
    const imagen = document.querySelector('.map-image');
    const atracciones = [
        { nombre: "Montaña Rusa del Terror", x: 15, y: 78 },
        { nombre: "Torre del Vertigo", x: 41.5, y: 30 },
        { nombre: "Cataratas Mágicas", x: 41.8, y: 65 },
        { nombre: "Río Tranquilo", x: 20, y: 72 },
        { nombre: "Safari Salvaje", x: 36, y: 60 },
        { nombre: "Teatro de los Sueños", x: 58, y: 38 },
        { nombre: "Laberinto Encantado", x: 55, y: 62 },
        { nombre: "Carrusel Clásico", x:12, y: 55 },
        { nombre: "Simulador Espacial", x: 68, y: 60 },
        { nombre: "Zona Infantil Aventura", x: 73, y: 35 }
    ];

    function crearPuntos() {
        // Limpiar puntos existentes
        document.querySelectorAll('.punto-interes').forEach(p => p.remove());

        atracciones.forEach(atraccion => {
            const punto = document.createElement('div');
            punto.className = 'punto-interes';
            punto.style.left = `${atraccion.x}%`;
            punto.style.top = `${atraccion.y}%`;

            const tooltip = document.createElement('div');
            tooltip.className = 'tooltip';
            tooltip.textContent = atraccion.nombre;
            punto.appendChild(tooltip);

            punto.addEventListener('click', () => mostrarAtraccion(atraccion.nombre));
            mapContainer.appendChild(punto);
        });
    }

    // Crear puntos iniciales
    imagen.addEventListener('load', crearPuntos);
    crearPuntos();
}

document.addEventListener('DOMContentLoaded', inicializarPuntos);