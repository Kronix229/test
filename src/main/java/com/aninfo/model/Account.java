package com.aninfo.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cbu;
    @OneToMany()
    private Map<Long, Transaction> transactions;

    public Account() {
    }
    public Account(Double sum) {
        transactions = new HashMap<>();
    }
    public Long getCbu() {
        return cbu;
    }

    public void setCbu(Long cbu) {
        this.cbu = cbu;
    }

    public Double getBalance() {
        var balance = transactions.values().stream().mapToDouble(Transaction::getCharge).sum();
        return balance;
    }
    public void addTransaction(Transaction transaction) {this.transactions.put(transaction.getID(),transaction);}
    public void deleteTransaction(Long id) {transactions.remove(id);}
    public List<Transaction> getTransactions() {
        var transactionList = new ArrayList<Transaction>();
        for (Transaction transaction : transactions.values()) {
            transactionList.add(transaction);
        };
        return transactionList;
    }
}
