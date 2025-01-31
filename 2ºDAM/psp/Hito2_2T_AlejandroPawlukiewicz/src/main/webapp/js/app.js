// Variables globales
var API_URL = 'http://localhost:8080/ecomerch-rest/api';
var currentEndpoint = 'productos';

// Función para ejecutar peticiones
function executeRequest(method, url, data = null) {
    var options = {
        method: method,
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }
    };

    if (data && (method === 'POST' || method === 'PUT')) {
        options.body = JSON.stringify(data);
    }

    fetch(url, options)
        .then(function(response) {
            if (!response.ok) {
                throw new Error('Error en la petición: ' + response.status);
            }
            return response.json();
        })
        .then(function(responseData) {
            updateResponsePanel(responseData);
            // Recargar tabla inmediatamente
            loadTableData();
        })
        .catch(function(error) {
            console.error('Error:', error);
            updateResponsePanel({error: error.message});
        });
}

// Función para cargar datos en la tabla
function loadTableData() {
    fetch(API_URL + '/' + currentEndpoint + '/all')
        .then(function(response) {
            return response.json();
        })
        .then(function(data) {
            updateTable(data);
        })
        .catch(function(error) {
            console.error('Error:', error);
        });
}

// Manejador del formulario
function handleFormSubmit(e) {
    e.preventDefault();
    
    var method = document.getElementById('method').value;
    var id = document.getElementById('entityId').value;
    var requestData = document.getElementById('requestData').value;

    try {
        var jsonData = requestData ? JSON.parse(requestData) : null;
        var url = API_URL + '/' + currentEndpoint;
        
        if (id && (method === 'PUT' || method === 'DELETE')) {
            url += '/' + id;
        }

        executeRequest(method, url, jsonData);
        
        // Limpiar formulario después de enviar
        if (method !== 'GET') {
            document.getElementById('entityId').value = '';
            document.getElementById('requestData').value = '';
        }
    } catch (e) {
        updateResponsePanel({error: 'Error en formato JSON: ' + e.message});
    }
}

// Actualizar panel de respuesta
function updateResponsePanel(data) {
    var responseArea = document.getElementById('responseArea');
    responseArea.textContent = JSON.stringify(data, null, 2);
    responseArea.classList.add('updated');
    setTimeout(function() {
        responseArea.classList.remove('updated');
    }, 500);
}

// Añadir función de búsqueda por ID
function searchById() {
    var id = document.getElementById('searchId').value;
    if (!id) {
        updateResponsePanel({error: 'Por favor, ingrese un ID'});
        return;
    }

    fetch(API_URL + '/' + currentEndpoint + '/' + id)
        .then(function(response) {
            if (!response.ok) {
                throw new Error('No se encontró el elemento con ID: ' + id);
            }
            return response.json();
        })
        .then(function(data) {
            // Actualizar el panel de respuesta
            updateResponsePanel(data);
            
            // Actualizar la tabla con solo este elemento
            var dataArray = [data]; // Convertir el objeto único en array
            updateTable(dataArray);
            
            // Resaltar la fila encontrada
            highlightRow(id);
        })
        .catch(function(error) {
            updateResponsePanel({error: error.message});
            // Recargar tabla completa si hay error
            loadTableData();
        });
}

// Función para resaltar la fila
function highlightRow(id) {
    var rows = document.querySelectorAll('#tableBody tr');
    rows.forEach(function(row) {
        if (row.cells[0].textContent === id.toString()) {
            row.classList.add('table-primary');
            setTimeout(function() {
                row.classList.remove('table-primary');
            }, 2000);
        }
    });
}

// Añadir botón de "Ver todos" para restaurar la vista completa
function showAll() {
    document.getElementById('searchId').value = '';
    loadTableData();
}

// Actualizar tabla
function updateTable(data) {
    var tbody = document.getElementById('tableBody');
    var content = '';

    data.forEach(function(item) {
        content += '<tr>';
        if (currentEndpoint === 'productos') {
            content += `
                <td>${item.id || ''}</td>
                <td>${item.nombre || ''}</td>
                <td>${item.descripcion || ''}</td>
                <td>${item.precio || 0}€</td>
                <td>${item.stock || 0}</td>
            `;
        } else {
            content += `
                <td>${item.id || ''}</td>
                <td>${item.nombre || ''}</td>
                <td>${item.email || ''}</td>
                <td>${item.telefono || ''}</td>
                <td>${item.direccion || ''}</td>
            `;
        }
        content += `
            <td>
                <button class="btn btn-sm btn-primary me-2" onclick="editItem(${item.id})">
                    Editar
                </button>
                <button class="btn btn-sm btn-danger" onclick="deleteItem(${item.id})">
                    Eliminar
                </button>
            </td>
        </tr>`;
    });
    
    tbody.innerHTML = content;

    // Efecto visual de actualización
    var table = document.getElementById('dataTable');
    table.classList.add('table-update');
    setTimeout(function() {
        table.classList.remove('table-update');
    }, 500);
}

// Funciones CRUD
function editItem(id) {
    fetch(API_URL + '/' + currentEndpoint + '/' + id)
        .then(function(response) {
            return response.json();
        })
        .then(function(data) {
            document.getElementById('method').value = 'PUT';
            document.getElementById('entityId').value = id;
            document.getElementById('requestData').value = JSON.stringify(data, null, 2);
            updateResponsePanel(data);
        });
}

function deleteItem(id) {
    if (confirm('¿Confirma eliminar este elemento?')) {
        executeRequest('DELETE', API_URL + '/' + currentEndpoint + '/' + id);
    }
}

function loadProductos() {
    currentEndpoint = 'productos';
    document.getElementById('tableTitle').textContent = 'Productos';
    loadTableData();
}

function loadClientes() {
    currentEndpoint = 'clientes';
    document.getElementById('tableTitle').textContent = 'Clientes';
    loadTableData();
}

function showAddForm() {
    document.getElementById('method').value = 'POST';
    document.getElementById('entityId').value = '';
    var template = currentEndpoint === 'productos'
        ? {
            nombre: '',
            descripcion: '',
            precio: 0,
            stock: 0
          }
        : {
            nombre: '',
            email: '',
            telefono: '',
            direccion: ''
          };
    document.getElementById('requestData').value = JSON.stringify(template, null, 2);
}

// Inicialización
document.addEventListener('DOMContentLoaded', function() {
    // Vincular eventos
    document.getElementById('apiForm').addEventListener('submit', handleFormSubmit);
    
    // Cargar datos iniciales
    loadProductos();
});

document.head.insertAdjacentHTML('beforeend', `
    <style>
        .table-update {
            animation: tableHighlight 0.5s ease-out;
        }
        @keyframes tableHighlight {
            0% { background-color: #e8f4f8; }
            100% { background-color: transparent; }
        }
    </style>
`);