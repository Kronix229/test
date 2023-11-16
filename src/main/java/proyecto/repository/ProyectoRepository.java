package proyecto.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import proyecto.model.Proyecto;

@RepositoryRestResource
public interface ProyectoRepository extends CrudRepository<Proyecto, Long> {

    Proyecto findProyectotByid(Long id);

    @Override
    List<Proyecto> findAll();

}