package proyecto.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import proyecto.model.Tarea;

@RepositoryRestResource
public interface TareaRepository extends CrudRepository<Tarea, Long> {

    Tarea findTareaByid(Long id);

    List<Tarea> findByProyecto(Long proyecto);

    @Override
    List<Tarea> findAll();

}