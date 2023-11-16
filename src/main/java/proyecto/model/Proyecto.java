package proyecto.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Entity
public class Proyecto implements Completable, Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String nombre;
    private String descripcion;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFinalizacionEsperada;
    private LocalDateTime  getFechaFinalizacionReal;

    /* Esto al final lo hacemos?
        private LocalDateTime horasTrabajoEstimadas;
        private LocalDateTime HorasTrabajoReales;*/
    private Estado estado;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Map<Long, Tarea> tareas;

    public Proyecto() {
    }
    public Proyecto(String nombre, String descripcion, LocalDateTime fechaFinalizacionEsperada) {
        this.descripcion = descripcion;
        this.fechaInicio = LocalDateTime.now();
        this.fechaFinalizacionEsperada = fechaFinalizacionEsperada;
        this.nombre = nombre;
        this.estado = Estado.NO_INICIADO;
        this.tareas = new HashMap<>();
    }

    public void iniciar() {
        estado = Estado.EN_PROGRESO;
    }

    public void terminar() {
        estado = Estado.TERMINADO;
        getFechaFinalizacionReal = LocalDateTime.now();
    }

    public void descompletar() {
        estado = Estado.NO_INICIADO;
        getFechaFinalizacionReal = null;
    }
    @Override
    public String toString() {
        var tar = String.join(", " ,tareas.values().stream().map(x -> x.toString()).collect(Collectors.toList()));
        return "id: " + id +
                " nombre:" + nombre +
                " descripcion: " + descripcion +
                " fecha de inicio: " + fechaInicio.toString() +
                " fecha fin esperada:  " + fechaFinalizacionEsperada.toString() +
                " tarea: " + tar;
    }
    public String getNombre() {
        return nombre;
    }
    public String getDescripcion() {
        return descripcion;
    }
    public LocalDateTime getFechaFinalizacionEsperada() {
        return fechaFinalizacionEsperada;
    }

    public Estado getEstado() {
        return estado;
    }
}
