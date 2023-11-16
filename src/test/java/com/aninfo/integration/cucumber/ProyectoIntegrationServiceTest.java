package com.aninfo.integration.cucumber;

import proyecto.ProyectoApp;
import proyecto.model.Proyecto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import proyecto.service.ProyectoService;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@ContextConfiguration(classes = ProyectoApp.class)
@WebAppConfiguration
public class ProyectoIntegrationServiceTest {

    @Autowired
    ProyectoService proyectoService;

    Proyecto crearProyecto(String nombre, String descripcion, String fechaFinalizacionEsperada) {
        var formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
        LocalDateTime fecha;
        try {
            fecha = LocalDateTime.parse(fechaFinalizacionEsperada, formatter);
        } catch (DateTimeParseException ex) {
            throw new DateTimeException("La fecha tiene el formato incorrecto que debe ser 'yyyy/MM/dd HH:mm'") ;
        }
        var proyecto = new Proyecto(nombre, descripcion, fecha);
        var proyecto1 = proyectoService.createProyecto(proyecto);
        return proyecto1;
    }

}
