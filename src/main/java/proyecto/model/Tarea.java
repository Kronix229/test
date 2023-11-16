package proyecto.model;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Duration;

@Entity
public class Tarea implements Completable, Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long proyecto;
    private String descripcion;
    private Estado estado;
    private Duration horasTrabajoPrevistas;
    private Duration horasTrabajoReales;

    public Tarea() {
    }

    public Tarea(String descripcion, Duration horasTrabajoPrevistas, Long proyecto) {
        this.descripcion = descripcion;
        this.horasTrabajoPrevistas = horasTrabajoPrevistas;
        this.estado = Estado.NO_INICIADO;
        this.horasTrabajoReales = Duration.ZERO;
        this.proyecto = proyecto;
    }

    public void iniciar() {
        estado = Estado.EN_PROGRESO;
    }

    public void terminar() {
        estado = Estado.TERMINADO;
    }

    public void descompletar() {
        estado = Estado.NO_INICIADO;
    }

    public Long getId() {
        return id;
    }
    @Override
    public String toString() {
        return "descripcion " + descripcion + " horas trabajo previstas " + horasTrabajoPrevistas.toString();
    }
}
