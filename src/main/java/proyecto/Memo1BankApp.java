package proyecto;

import proyecto.model.*;
import proyecto.service.ProyectoService;
import proyecto.service.TareaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.DateTimeException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@SpringBootApplication
@EnableSwagger2
public class Memo1BankApp {

	@Autowired
	private ProyectoService proyectoService;
	@Autowired
	private TareaService tareaService;


	public static void main(String[] args) {
		SpringApplication.run(Memo1BankApp.class, args);
	}

	@PostMapping("/proyectos/{nombre}")
	@ResponseStatus(HttpStatus.CREATED)
	public Proyecto crearProyecto(@PathVariable String nombre, @RequestParam String descripcion, @RequestParam String fechaFinalizacionEsperada) {
		var formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
		LocalDateTime fecha;
		try {
			fecha = LocalDateTime.parse(fechaFinalizacionEsperada, formatter);
			System.out.println(fecha.toString());
		} catch (DateTimeParseException ex) {
			System.out.println("si");
			throw new DateTimeException("La fecha tiene el formato incorrecto que debe ser 'yyyy/MM/dd HH:mm'") ;
		}
		var proyecto = new Proyecto(nombre, descripcion, fecha);
		var proyecto1 = proyectoService.createProyecto(proyecto);
		return proyecto1;
	}
	@PutMapping("/proyectos/{id}")
	public ResponseEntity<Proyecto> modificarEstadoProyecto(@PathVariable Long id, int estado) {
		Optional<Proyecto> proyectoOptional = proyectoService.findById(id);
		if (proyectoOptional.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		var proyecto = proyectoOptional.get();
		cambiarEstado(proyecto, estado);
		proyectoService.save(proyecto);
		return ResponseEntity.ok(proyecto);
	}
	@GetMapping("/proyectos/{id}")
	public ResponseEntity<Proyecto> getProyecto(@PathVariable Long id) {
		Optional<Proyecto> proyectoOptional = proyectoService.findById(id);
		if (!proyectoOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		var proyecto = proyectoOptional.get();
		System.out.println(proyecto);
		return ResponseEntity.of(proyectoOptional);
	}
    @GetMapping("/proyectos")
    public Collection<Proyecto> getProyectos() {
        return proyectoService.getProyectos();
    }
	@PostMapping("/tareas/{id}")
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<Tarea> crearTarea(@PathVariable Long id, String descripcion, @RequestParam String horasTrabajoPrevistas) {
		var formatter = Arrays.stream(horasTrabajoPrevistas.split(":")).mapToInt(x -> Integer.parseInt(x)).toArray();
		Duration fecha;
		try {
			fecha = Duration.ofSeconds(formatter[0]*3600+formatter[1]*60+formatter[2]);
			System.out.println(fecha.toString());
		} catch (DateTimeParseException ex) {
			System.out.println("si");
			return ResponseEntity.badRequest().build();
		}
		var proyectoOptional = proyectoService.findById(id);
		if (!proyectoOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		var proyecto = proyectoOptional.get();
		var tarea = new Tarea(descripcion, fecha, id);
		var tarea1 = tareaService.createTarea(tarea);
		System.out.println(tarea1);
		System.out.println(proyecto);
		proyectoService.save(proyecto);
		return ResponseEntity.ok(tarea1);
	}
	@PutMapping("/tarea/{id}")
	public ResponseEntity<Tarea> modificarEstadoTarea(@PathVariable Long id, int estado) {
		Optional<Tarea> tareaOptional = tareaService.findById(id);
		if (tareaOptional.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		var tarea = tareaOptional.get();
		cambiarEstado(tarea, estado);
		tareaService.save(tarea);
		return ResponseEntity.ok(tarea);
	}
	@GetMapping("/tareas/{id}")
	public ResponseEntity<Tarea> getTarea(@PathVariable Long id) {
		Optional<Tarea> tareaOptional = tareaService.findById(id);
		if (!tareaOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		var tarea = tareaOptional.get();
		System.out.println(tarea);
		return ResponseEntity.of(tareaOptional);
	}
	@RequestMapping(method = RequestMethod.GET)
	public ResponseEntity<List<Tarea>> getTareasDeProyecto(@RequestParam Long id) {
		List<Tarea> tareas = tareaService.getTareasPorProyecto(id);
		tareas.forEach(System.out::println);
		return ResponseEntity.ok().build();
	}
	@DeleteMapping("/accounts/{cbu}")
	public void cambiarEstado(Completable completable, int estado) {
		switch (estado) {
			case 1:
				completable.descompletar();
				break;
			case 2:
				completable.iniciar();
				break;
			case 3:
				completable.terminar();
				break;
		}
	}

	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
			.select()
			.apis(RequestHandlerSelectors.any())
			.paths(PathSelectors.any())
			.build();
	}
}
