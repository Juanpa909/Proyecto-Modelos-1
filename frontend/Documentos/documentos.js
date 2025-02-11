let libros = [];
let articulos = [];
let ponencias = [];
let indices = { libros: 0, articulos: 0, ponencias: 0 };

async function fetchDocuments() {
    let data={
        "titulo": localStorage.getItem("titulo")
    }
    try {
        const response = await fetch('http://localhost:8080/Libreria/documento/titulo', {
            method: 'POST',
            headers: {
                'Authorization': `Bearer ${localStorage.getItem("token")}`,
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(data)
        });

        if (response.status === 401) {
            alert("Sesión expirada. Por favor, inicia sesión de nuevo.");
            localStorage.removeItem("token");
            window.location.href = "../LoginRegistro/login.html";
            throw new Error("Usuario no autorizado (401)");
        }
        if (!response.ok) {
            throw new Error('Error al modificar el documento');
        }

        const documentos = await response.json();
        console.log("Documentos recibidos:", documentos);

        libros = documentos.filter(doc => doc.tipo === "libro");
        articulos = documentos.filter(doc => doc.tipo === "articulo");
        ponencias = documentos.filter(doc => doc.tipo === "ponencia");

        displayDocument("libros");
        displayDocument("articulos");
        displayDocument("ponencias");

    } catch (error) {
        console.error('Error al obtener documentos:', error);
    }
}

function displayDocument(tipo) {
    const container = document.getElementById(tipo + "Fields");
    container.innerHTML = "";
    const data = eval(tipo)[indices[tipo]] || null;

    if (data) {
        for (let key in data) {
            if (!["idDocumento", "tipo", "mesPublicacion", "diaPublicacion"].includes(key)) {
                let value = data[key];
                container.innerHTML += `<div><strong>${key.toUpperCase()}:</strong> ${value ? value : ""}</div>`;
            }
        }

        container.innerHTML += `<button class="botonVerDocumento" onclick="ver(${data.idDocumento})">Ver</button>`;
    } else {
        container.innerHTML = "<div>No hay documentos</div>";
    }
}

function nextDocument(tipo) {
    if (indices[tipo] < eval(tipo).length - 1) {
        indices[tipo]++;
    } else {
        indices[tipo] = 0;
    }
    displayDocument(tipo);
}

document.addEventListener("DOMContentLoaded", fetchDocuments);