package co.edu.uniajc.tareas.repositorio;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import co.edu.uniajc.tareas.modelo.Tarea;

@Repository
public class TareaRepositorio {
    private final ConcurrentHashMap<Long, Tarea> tareas = new ConcurrentHashMap<>();
    private final AtomicLong contador = new AtomicLong(1);

    public List<Tarea> listar() {
        return new ArrayList<>(tareas.values());
    }

    public Tarea guardar(String titulo) {
        Long id = contador.getAndIncrement();
        Tarea tarea = new Tarea(id, titulo);
        tareas.put(id, tarea);
        return tarea;
    }

    public boolean eliminar(Long id) {
        return tareas.remove(id) != null;
    }
}
