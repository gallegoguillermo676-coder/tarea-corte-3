package co.edu.uniajc.tareas.controlador;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import co.edu.uniajc.tareas.modelo.Tarea;
import co.edu.uniajc.tareas.servicio.TareaServicio;

@RestController
@RequestMapping("/api/tareas")
@CrossOrigin(origins = {"http://localhost:5500", "http://127.0.0.1:5500"})
public class TareaControlador {
    private final TareaServicio servicio;

    public TareaControlador(TareaServicio servicio) {
        this.servicio = servicio;
    }

    @GetMapping
    public List<Tarea> listar() {
        return servicio.listar();
    }

    @PostMapping
    public ResponseEntity<Tarea> crear(@RequestBody Map<String, String> cuerpo) {
        Tarea nueva = servicio.crear(cuerpo.get("titulo"));
        return ResponseEntity.status(HttpStatus.CREATED).body(nueva);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        servicio.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}
