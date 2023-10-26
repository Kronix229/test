package com.aninfo.model;

import javax.persistence.Entity;

@Entity
public class Deposit extends Transaction{
    public Deposit() {}
    public Deposit(Double sum, Long cbu) {
        super(sum, cbu);
    }
}
