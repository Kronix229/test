package proyecto.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import proyecto.model.Tarea;
import proyecto.model.Tarea;
import proyecto.repository.TareaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class TareaService {
    @Autowired
    private TareaRepository tareaRepository;
    public Tarea createTarea(Tarea tarea) {
        return tareaRepository.save(tarea);
    }
    public Collection<Tarea> getTareas() {
        return tareaRepository.findAll();
    }
    public List<Tarea> getTareasPorProyecto(Long id) { return tareaRepository.findByProyecto(id); }

    public Optional<Tarea> findById(Long id) {
        return tareaRepository.findById(id);
    }

    public void save(Tarea tarea) {
        tareaRepository.save(tarea);
    }

    public void deleteById(Long id) {
        tareaRepository.deleteById(id);
    }
}
