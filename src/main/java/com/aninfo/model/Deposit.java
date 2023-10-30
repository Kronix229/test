package com.aninfo.model;

import javax.persistence.Entity;

@Entity
public class Deposit extends Transaction {
    public Deposit() {}
    public Deposit(Double sum, Long cbu) {
            super(sum,cbu);
            applyPromo(sum);
    }
    private void applyPromo(Double sum) {
        if (sum >= 2000) {
            if (sum < 5000) {
                charge += sum/10;
            } else {
                charge += 500;
            }
        }
    }
}
