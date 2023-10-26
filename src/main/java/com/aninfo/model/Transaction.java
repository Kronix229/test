package com.aninfo.model;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private Long transactionOwner;
    private double charge;
    public Transaction(){
    }

    public Transaction(double charge, Long transactionOwner) {
        this.charge = charge;
        this.transactionOwner = transactionOwner;
    }
    public double getCharge() {return charge;}
    public Long getID() {return id;}


    public Long getTransactionOwner() {
        return transactionOwner;
    }
}
