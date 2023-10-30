package com.aninfo.integration.cucumber;

import com.aninfo.Memo1BankApp;
import com.aninfo.exceptions.DepositNegativeSumException;
import com.aninfo.model.Account;
import com.aninfo.model.Deposit;
import com.aninfo.model.Transaction;
import com.aninfo.model.Withdraw;
import com.aninfo.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.web.WebAppConfiguration;

@ContextConfiguration(classes = Memo1BankApp.class)
@WebAppConfiguration
public class AccountIntegrationServiceTest {

    @Autowired
    AccountService accountService;

    Account createAccount(Double balance) {
        if (balance < 0) {
            throw new DepositNegativeSumException("Account cannot be created with negative balance");
        }
        var account = new Account(balance);
        var acc = accountService.createAccount(account);
        var transaction = new Deposit(balance, account.getCbu());
        var account1 = accountService.deposit(transaction, acc);
        return account1;
    }

    Account withdraw(Account account, Double sum) {
        var transaction = new Withdraw(-sum, account.getCbu());
        return accountService.withdraw(transaction);
    }

    Account deposit(Account account, Double sum) {
        var transaction = new Deposit(sum, account.getCbu());
        return accountService.deposit(transaction);
    }

}
