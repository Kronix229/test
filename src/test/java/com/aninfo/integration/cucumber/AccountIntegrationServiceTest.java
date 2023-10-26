package com.aninfo.integration.cucumber;

import com.aninfo.Memo1BankApp;
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
        return accountService.createAccount(new Account());
    }

    Account withdraw(Account account, Double sum) {
        var transaction = new Withdraw();
        return accountService.withdraw(transaction);
    }

    Account deposit(Account account, Double sum) {
        var transaction = new Deposit();
        return accountService.deposit(transaction);
    }

}
