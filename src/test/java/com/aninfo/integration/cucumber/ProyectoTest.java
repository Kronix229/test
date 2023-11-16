package com.aninfo.integration.cucumber;

import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.Test;
import proyecto.model.Estado;
import proyecto.model.Proyecto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProyectoTest extends ProyectoIntegrationServiceTest {

    private Proyecto proyecto;

    private String nombre;
    private String descripcion;
    private String fecha;
    private LocalDateTime fechaFormateda;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm");
    private Exception ex;
    @Before
    public void setup() {
        System.out.println("Before any test execution");
    }

    @Given("Se envian los parametros: nombre ? (.*), descripcion ? (.*) y fecha ? (.*)")
    public void account_with_a_balance_of(String nombre, String descripcion, String fechaFinalizacionEsperada)  {
       this.nombre = nombre;
       this.descripcion = descripcion;
       fecha = fechaFinalizacionEsperada;
    }

    @When("^Se intenta crear el proyecto$")
    public void trying_to_withdraw() {
        try {
            fechaFormateda = LocalDateTime.parse(this.fecha, formatter);
            proyecto = new Proyecto(nombre, descripcion, fechaFormateda);
        } catch (DateTimeParseException ex) {
            this.ex = ex;
        }
    }

    @When("^Trying to deposit (.*)$")
    public void trying_to_deposit(int sum) {
    }

    @Then("^El proyecto queda guardado con todos los datos correspondientes$")
    public void account_balance_should_be() {
        assertEquals(nombre, proyecto.getNombre());
        assertEquals(descripcion, proyecto.getDescripcion());
        assertEquals(fechaFormateda, proyecto.getFechaFinalizacionEsperada());
        assertEquals(Estado.NO_INICIADO, proyecto.getEstado());
    }
    @Test(expected = DateTimeParseException.class)
    @Then("^El proyecto no se crea y se lanza una excepción$")
    public void operation_should_be_denied_due_to_insufficient_funds() {
        assertEquals(ex.getClass(), DateTimeParseException.class);
    }

    @Then("^Operation should be denied due to negative sum$")
    public void operation_should_be_denied_due_to_negative_sum() {
    }

    @And("^Account balance should remain (\\d+)$")
    public void account_balance_should_remain(int balance) {
    }

    @After
    public void tearDown() {
        System.out.println("After all test execution");
    }

}
