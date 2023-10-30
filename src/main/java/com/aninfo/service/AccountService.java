package com.aninfo.service;

import com.aninfo.exceptions.DepositNegativeSumException;
import com.aninfo.exceptions.InsufficientFundsException;
import com.aninfo.model.Account;
import com.aninfo.model.Transaction;
import com.aninfo.repository.AccountRepository;
import com.aninfo.repository.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private TransactionRepository transactionRepository;
    public Account createAccount(Account account) {
        return accountRepository.save(account);
    }

    public Collection<Account> getAccounts() {
        return accountRepository.findAll();
    }

    public Optional<Account> findById(Long cbu) {
        return accountRepository.findById(cbu);
    }

    public void save(Account account) {
        accountRepository.save(account);
    }

    public void deleteById(Long cbu) {
        accountRepository.deleteById(cbu);
    }

    @Transactional
    public Account withdraw(Transaction transaction) {
        Account account = accountRepository.findAccountByCbu(transaction.getTransactionOwner());
        if (account.getBalance() < -transaction.getCharge()) {
            throw new InsufficientFundsException("Insufficient funds");
        }
        var transac = transactionRepository.save(transaction);
        account.addTransaction(transac);
        accountRepository.save(account);
        return account;
    }
    @Transactional
    public Account deposit(Transaction transaction) {
        if (transaction.getCharge() <= 0) {
            throw new DepositNegativeSumException("Must deposit a positive sum");
        }
        var transac = transactionRepository.save(transaction);
        Account account = accountRepository.findAccountByCbu(transac.getTransactionOwner());
        account.addTransaction(transac);
        accountRepository.save(account);

        return account;
    }
    @Transactional
    public Account deposit(Transaction transaction, Account account) {
        if (transaction.getCharge() < 0) {
            throw new DepositNegativeSumException("Cannot deposit negative sums");
        }
        var transac = transactionRepository.save(transaction);
        account.addTransaction(transac);
        accountRepository.save(account);

        return account;
    }

}
