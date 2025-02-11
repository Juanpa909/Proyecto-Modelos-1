const documentoStr = localStorage.getItem("Documento");
document.addEventListener("DOMContentLoaded", () => {
    cargarDocumento();
    cargarEventos()
});

function cargarEventos(){
    const documento = JSON.parse(documentoStr);
    let data = {
        "iddocumento": documento.iddocumento
    }
    fetch('http://localhost:8080/Libreria/documento/eventos', {
        method: 'POST',
        headers: {
            'Authorization': `Bearer ${localStorage.getItem("token")}`,
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(data)
    }).then(response =>{
        if (response.status === 401) {
            alert("Sesión expirada. Por favor, inicia sesión de nuevo.");
            localStorage.removeItem("token");
            window.location.href = "../LoginRegistro/login.html";
            throw new Error("Usuario no autorizado (401)");
        }
        if (!response.ok) {
            throw new Error(`Error HTTP: ${response.status}`);
        }
        return response.json();
    }).then(data => {
        let listaEventos = data;
        const listaHistorial = document.getElementById("listaHistorial");

        listaEventos.forEach(evento => {
            const li = document.createElement("li");
            li.textContent = evento;
            listaHistorial.appendChild(li);
        });
    })
    .catch(error => {
        console.error('Error:', error);
    });
}

function cargarDocumento() {
    if (!documentoStr) {
        alert("No hay documento cargado.");
        return;
    }

    const documento = JSON.parse(documentoStr);
    const formulario = document.getElementById("formulario");
    let contenido = `
        <div class='campo'><label>Título:</label><input type='text' value='${documento.titulo}' readonly></div>
        <div class='campo'><label>Fecha de Publicación:</label><input type='date' value='${documento.fechaPublicacion}' readonly></div>
        <div class='campo'><label>Autores:</label><input type='text' value='${documento.autores}' readonly></div>
        <div class='campo'><label>Editorial:</label><input type='text' value='${documento.editorial}' readonly></div>
    `;

    if (documento.tipo === "libro") {
        contenido += `
            <div class='campo'><label>ISBN:</label><input type='text' value='${documento.isbn ?? ""}' readonly></div>
            <div class='campo'><label>Número de Páginas:</label><input type='number' value='${documento.numPaginas ?? ""}' readonly></div>
        `;
    } else if (documento.tipo === "articulo") {
        contenido += `<div class='campo'><label>SSN:</label><input type='text' value='${documento.ssn ?? ""}' readonly></div>`;
    } else if (documento.tipo === "ponencia") {
        contenido += `
            <div class='campo'><label>Congreso:</label><input type='text' value='${documento.nombreCongreso ?? ""}' readonly></div>
            <div class='campo'><label>ISBN:</label><input type='text' value='${documento.isbn ?? ""}' readonly></div>
        `;
    }

    contenido += `
        <div class='campo'>
            <label></label>
            <a href="#" onclick="verPropietario('${documento.propietario}')">Haz clic aquí para ver los datos del propietario</a>
        </div>
    `;

    contenido += `<button class="botonSalir" onclick="window.location.href='../PaginaPrincipal/paginaPrincipal.html'">Salir</button>`;

    if (documento.estado === "Disponible") {
        contenido += `
            <button class="botonModificacionDocumento" onclick="window.location.href='../Documentos/formularioDoc.html'">Ir a Modificar</button>
            <button class="botonReservaDocumento" onclick="reservarDocumento()">Reservar</button>
        `;
    } else if (documento.estado === "Reservado") {
        contenido += `<button class="botonModificacionDocumento" onclick="window.location.href='../Documentos/formularioDoc.html'">Ir a Modificar</button>`;
    }

    formulario.innerHTML = contenido;
}

function verPropietario(propietario) {
    if (!propietario) {
        alert("No hay datos del propietario.");
        return;
    }

    localStorage.setItem("usuario", propietario);
    window.location.href = "../PaginaPrincipal/informacionConsulta.html";
}


async function reservarDocumento() {
    try {
        const documento = JSON.parse(localStorage.getItem("Documento")); 
        let data = {
            "documento": documento.iddocumento
        };

        const reservarResponse = await fetch('http://localhost:8080/Libreria/documento/reservar', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${localStorage.getItem("token")}`,
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data)
        });

        if (reservarResponse.status === 401) {
            alert("Sesión expirada. Por favor, inicia sesión de nuevo.");
            localStorage.removeItem("token");
            window.location.href = "../LoginRegistro/login.html";
            throw new Error("Usuario no autorizado (401)");
        }
        if (!reservarResponse.ok) {
            throw new Error('Error al modificar el documento');
        }

        const reservarData = await reservarResponse.json();
        if (reservarData.mensaje === "Actualizado") {
            alert("Documento Reservado");
        }

        data = {
            "iddocumento": documento.iddocumento
        }
        const obtenerResponse = await fetch('http://localhost:8080/Libreria/documento', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${localStorage.getItem("token")}`,
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data)
        });

        if (!obtenerResponse.ok) {
            throw new Error(`Error HTTP en obtener documento: ${obtenerResponse.status}`);
        }

        const obtenerData = await obtenerResponse.json();
        localStorage.setItem("Documento", JSON.stringify(obtenerData));
        location.replace("../HistorialUsuario/descripcionDocumento.html");

    } catch (error) {
        console.error('Error:', error);
    }
}
