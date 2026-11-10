package co.edu.uniajc.tareas.servicio;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import co.edu.uniajc.tareas.modelo.Tarea;
import co.edu.uniajc.tareas.repositorio.TareaRepositorio;

@Service
public class TareaServicio {
    private final TareaRepositorio repositorio;

    public TareaServicio(TareaRepositorio repositorio) {
        this.repositorio = repositorio;
    }

    public List<Tarea> listar() {
        return repositorio.listar();
    }

    public Tarea crear(String titulo) {
        if (titulo == null || titulo.isBlank() || titulo.length() > 100) {
            throw new ResponseStatusException(
                HttpStatus.BAD_REQUEST,
                "El título es obligatorio y debe tener máximo 100 caracteres."
            );
        }
        return repositorio.guardar(titulo.trim());
    }

    public void eliminar(Long id) {
        if (!repositorio.eliminar(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Tarea no encontrada.");
        }
    }
}
