package com.aninfo.integration.cucumber;

import org.springframework.web.bind.annotation.RequestParam;
import proyecto.Memo1BankApp;
import proyecto.exceptions.DepositNegativeSumException;
import proyecto.model.Account;
import proyecto.model.Deposit;
import proyecto.model.Proyecto;
import proyecto.model.Withdraw;
import proyecto.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;
import proyecto.service.ProyectoService;

import java.time.DateTimeException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@ContextConfiguration(classes = Memo1BankApp.class)
@WebAppConfiguration
public class AccountIntegrationServiceTest {

    @Autowired
    ProyectoService proyectoService;

    Proyecto createAccount(String nombre, String descripcion, String fechaFinalizacionEsperada) {
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

    Account withdraw(Account account, Double sum) {
        var transaction = new Withdraw(-sum, account.getCbu());
        return proyectoService.withdraw(transaction);
    }

    Account deposit(Account account, Double sum) {
        var transaction = new Deposit(sum, account.getCbu());
        return proyectoService.deposit(transaction);
    }

}
