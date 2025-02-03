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

// Variables globales para el estado de los filtros
const filtrosActuales = {
    edad: 'todas',
    duracion: 'todas',
    altura: 'todas'
};

// Función principal de filtrado mejorada
function filtrarAtracciones(filtros) {
    return atracciones.filter(atraccion => {
        // Filtro por edad
        const cumpleEdad = filtros.edad === 'todas' || (
            (filtros.edad === 'niños' && atraccion.edad <= 8) ||
            (filtros.edad === 'jovenes' && atraccion.edad > 8 && atraccion.edad <= 12) ||
            (filtros.edad === 'adultos' && atraccion.edad > 12)
        );

        // Filtro por duración - convertir string "X minutos" a número
        const duracionNum = parseInt(atraccion.tiempo);
        const cumpleDuracion = filtros.duracion === 'todas' || (
            (filtros.duracion === 'corta' && duracionNum <= 5) ||
            (filtros.duracion === 'media' && duracionNum > 5 && duracionNum <= 15) ||
            (filtros.duracion === 'larga' && duracionNum > 15)
        );

        // Filtro por altura
        const cumpleAltura = filtros.altura === 'todas' || (
            (filtros.altura === 'baja' && atraccion.altura <= 100) ||
            (filtros.altura === 'media' && atraccion.altura > 100 && atraccion.altura <= 130) ||
            (filtros.altura === 'alta' && atraccion.altura > 130)
        );

        return cumpleEdad && cumpleDuracion && cumpleAltura;
    });
}

// Función para actualizar filtros
function actualizarFiltro(tipo, valor) {
    filtrosActuales[tipo] = valor;
    const atraccionesFiltradas = filtrarAtracciones(filtrosActuales);
    actualizarVisualizacionMapa(atraccionesFiltradas);
    actualizarBotonesActivos();
    actualizarContadorResultados(atraccionesFiltradas.length);
}

function actualizarVisualizacionMapa(atraccionesFiltradas) {
    const puntos = document.querySelectorAll('.punto-interes');
    
    puntos.forEach(punto => {
        const tooltip = punto.querySelector('.tooltip');
        const nombreAtraccion = tooltip.querySelector('strong').textContent;
        const estaFiltrada = atraccionesFiltradas.some(a => a.nombre === nombreAtraccion);
        
        if (estaFiltrada) {
            punto.style.opacity = '1';
            punto.style.pointerEvents = 'auto';
            punto.style.transform = 'translate(-50%, -50%) scale(1)';
            punto.classList.remove('filtered');
        } else {
            punto.style.opacity = '0.3'; // Cambiado de 0.2 a 0.3 para mejor visibilidad
            punto.style.pointerEvents = 'none';
            punto.style.transform = 'translate(-50%, -50%) scale(0.8)';
            punto.classList.add('filtered');
        }
    });
}

// Función para actualizar el contador de resultados
function actualizarContadorResultados(cantidad) {
    const resultadosCount = document.getElementById('resultados-count');
    if (resultadosCount) {
        resultadosCount.textContent = `${cantidad} atracciones encontradas`;
    }
}

// Función para actualizar los botones activos
function actualizarBotonesActivos() {
    document.querySelectorAll('.filtro-btn').forEach(btn => {
        const tipo = btn.dataset.tipo;
        const valor = btn.dataset.valor;
        btn.classList.toggle('active', filtrosActuales[tipo] === valor);
    });
}

function resetearFiltros() {
    // Resetear el estado de los filtros
    filtrosActuales.edad = 'todas';
    filtrosActuales.duracion = 'todas';
    filtrosActuales.altura = 'todas';

    // Actualizar la visualización
    actualizarVisualizacionMapa(atracciones);
    
    // Actualizar los botones activos
    document.querySelectorAll('.filtro-btn').forEach(btn => {
        if (btn.dataset.valor === 'todas') {
            btn.classList.add('active');
        } else {
            btn.classList.remove('active');
        }
    });

    // Animar el icono del botón de reset
    const resetIcon = document.querySelector('.reset-btn ion-icon');
    resetIcon.style.animation = 'none';
    setTimeout(() => {
        resetIcon.style.animation = 'spin 0.3s ease-out';
    }, 10);

    // Actualizar el contador
    actualizarContadorResultados(atracciones.length);
}

document.addEventListener('DOMContentLoaded', () => {
    const resultadosCount = document.getElementById('resultados-count');
    let listaVisible = false;
    
    // Crear lista de resultados si no existe
    let resultadosLista = document.querySelector('.resultados-lista');
    if (!resultadosLista) {
        resultadosLista = document.createElement('div');
        resultadosLista.className = 'resultados-lista';
        resultadosCount.parentNode.insertBefore(resultadosLista, resultadosCount);
    }

    // Función para actualizar la lista de atracciones
    function actualizarListaAtracciones(atraccionesFiltradas) {
        resultadosLista.innerHTML = '';
        
        atraccionesFiltradas.forEach(atraccion => {
            const item = document.createElement('div');
            item.className = 'atraccion-item';
            item.innerHTML = `
                <div class="atraccion-info">
                    <div class="atraccion-nombre">${atraccion.nombre}</div>
                    <div class="atraccion-tipo">${atraccion.tipo}</div>
                    <div class="atraccion-ubicacion">${atraccion.ubicación}</div>
                </div>
            `;
            
            item.addEventListener('click', () => {
                mostrarAtraccion(atraccion.nombre);
            });
            
            resultadosLista.appendChild(item);
        });
    }

    // Toggle de la lista al hacer clic en el contador
    resultadosCount.addEventListener('click', () => {
        listaVisible = !listaVisible;
        resultadosLista.classList.toggle('active', listaVisible);
        
        if (listaVisible) {
            const atraccionesFiltradas = filtrarAtracciones(filtrosActuales);
            actualizarListaAtracciones(atraccionesFiltradas);
        }
    });

    // Cerrar lista al hacer clic fuera
    document.addEventListener('click', (e) => {
        if (!resultadosCount.contains(e.target) && !resultadosLista.contains(e.target)) {
            listaVisible = false;
            resultadosLista.classList.remove('active');
        }
    });

    // Actualizar la lista cuando cambien los filtros
    const originalActualizarFiltro = window.actualizarFiltro;
    window.actualizarFiltro = function(tipo, valor) {
        originalActualizarFiltro(tipo, valor);
        if (listaVisible) {
            const atraccionesFiltradas = filtrarAtracciones(filtrosActuales);
            actualizarListaAtracciones(atraccionesFiltradas);
        }
    };
});

// Función para actualizar la visualización de botones activos
function actualizarBotonesActivos() {
    document.querySelectorAll('.filtro-btn').forEach(btn => {
        const tipo = btn.dataset.tipo;
        const valor = btn.dataset.valor;
        btn.classList.toggle('active', filtrosActuales[tipo] === valor);
    });
}

function inicializarPuntos() {
    const mapContainer = document.querySelector('.map-container');
    const imagen = document.querySelector('.map-image');

    function crearPuntos() {
        // Limpiar puntos existentes
        document.querySelectorAll('.punto-interes').forEach(p => p.remove());

        atracciones.forEach(atraccion => {
            const punto = document.createElement('div');
            punto.className = 'punto-interes';
            punto.setAttribute('data-tipo', atraccion.tipo); // Añadir tipo para estilos
            
            // Buscar las coordenadas correspondientes
            const coordenadas = obtenerCoordenadas(atraccion.nombre);
            punto.style.left = `${coordenadas.x}%`;
            punto.style.top = `${coordenadas.y}%`;

            // Crear tooltip mejorado
            const tooltip = document.createElement('div');
            tooltip.className = 'tooltip';
            tooltip.innerHTML = `
                <strong>${atraccion.nombre}</strong>
                <br>
                <span style="font-size: 0.8em">${atraccion.tipo}</span>
            `;
            punto.appendChild(tooltip);

            // Añadir eventos
            punto.addEventListener('click', () => mostrarAtraccion(atraccion.nombre));
            punto.addEventListener('mouseenter', () => tooltip.style.opacity = '1');
            punto.addEventListener('mouseleave', () => tooltip.style.opacity = '0');

            mapContainer.appendChild(punto);
        });
    }

    function obtenerCoordenadas(nombre) {
        const coordenadas = {
            "Montaña Rusa del Terror": { x: 15, y: 63 },
            "Torre del Vertigo": { x: 42, y: 30 },
            "Cataratas Mágicas": { x: 42, y: 51 },
            "Río Tranquilo": { x: 20, y: 58 },
            "Safari Salvaje": { x: 36, y: 50 },
            "Teatro de los Sueños": { x: 58, y: 32 },
            "Laberinto Encantado": { x: 55, y: 50 },
            "Carrusel Clásico": { x: 13, y: 46 },
            "Simulador Espacial": { x: 68, y: 48.5 },
            "Zona Infantil Aventura": { x: 73, y: 30 }
        };
        return coordenadas[nombre];
    }

    // Crear puntos cuando la imagen esté cargada
    imagen.addEventListener('load', crearPuntos);
    crearPuntos();
}

document.addEventListener('DOMContentLoaded', inicializarPuntos);