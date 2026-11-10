const API_URL = "http://localhost:8080/api/tareas";

const formulario = document.getElementById("formulario-tarea");
const campoTitulo = document.getElementById("titulo");
const lista = document.getElementById("lista-tareas");
const contador = document.getElementById("contador");
const mensaje = document.getElementById("mensaje");

async function cargarTareas() {
    try {
        const respuesta = await fetch(API_URL);

        if (!respuesta.ok) {
            throw new Error("No fue posible cargar las tareas.");
        }

        const tareas = await respuesta.json();
        mostrarTareas(tareas);
        mensaje.textContent = "";
    } catch (error) {
        mensaje.textContent = "No se pudo conectar con el servidor. Verifica que Spring Boot esté ejecutándose.";
        lista.innerHTML = "";
        contador.textContent = "0 tareas";
    }
}

function mostrarTareas(tareas) {
    lista.innerHTML = "";
    contador.textContent = `${tareas.length} ${tareas.length === 1 ? "tarea" : "tareas"}`;

    if (tareas.length === 0) {
        const elemento = document.createElement("li");
        elemento.className = "vacio";
        elemento.textContent = "Todavía no tienes tareas. ¡Agrega la primera!";
        lista.appendChild(elemento);
        return;
    }

    tareas.forEach((tarea) => {
        const elemento = document.createElement("li");
        elemento.className = "tarea";

        const texto = document.createElement("span");
        texto.textContent = tarea.titulo;

        const boton = document.createElement("button");
        boton.type = "button";
        boton.textContent = "Eliminar";
        boton.addEventListener("click", () => eliminarTarea(tarea.id));

        elemento.append(texto, boton);
        lista.appendChild(elemento);
    });
}

formulario.addEventListener("submit", async (evento) => {
    evento.preventDefault();
    const titulo = campoTitulo.value.trim();

    if (!titulo) {
        mensaje.textContent = "Escribe el título de la tarea.";
        return;
    }

    try {
        const respuesta = await fetch(API_URL, {
            method: "POST",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify({ titulo })
        });

        if (!respuesta.ok) {
            throw new Error("No se pudo guardar la tarea.");
        }

        campoTitulo.value = "";
        mensaje.textContent = "";
        await cargarTareas();
        campoTitulo.focus();
    } catch (error) {
        mensaje.textContent = "No se pudo guardar. Verifica la conexión con el servidor.";
    }
});

async function eliminarTarea(id) {
    try {
        const respuesta = await fetch(`${API_URL}/${id}`, {
            method: "DELETE"
        });

        if (!respuesta.ok) {
            throw new Error("No se pudo eliminar la tarea.");
        }

        await cargarTareas();
    } catch (error) {
        mensaje.textContent = "No se pudo eliminar la tarea.";
    }
}

cargarTareas();
