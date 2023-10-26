package com.aninfo.model;

import javax.persistence.Entity;

@Entity
public class Withdraw extends Transaction{
    public Withdraw() {}
    public Withdraw(Double sum, Long cbu) {
        super(sum, cbu);
    }
}
