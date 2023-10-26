package com.aninfo;

import com.aninfo.exceptions.DepositNegativeSumException;
import com.aninfo.exceptions.InexistantTransactionException;
import com.aninfo.exceptions.InsufficientFundsException;
import com.aninfo.model.Account;
import com.aninfo.model.Deposit;
import com.aninfo.model.Transaction;
import com.aninfo.model.Withdraw;
import com.aninfo.service.AccountService;
import com.aninfo.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@SpringBootApplication
@EnableSwagger2
public class Memo1BankApp {

	@Autowired
	private AccountService accountService;
	@Autowired
	private TransactionService transactionService;

	public static void main(String[] args) {
		SpringApplication.run(Memo1BankApp.class, args);
	}

	@PostMapping("/accounts/{sum}")
	@ResponseStatus(HttpStatus.CREATED)
	public Account createAccount(@PathVariable Double sum) {
		if (sum < 0) {
			throw new DepositNegativeSumException("Account cannot be created with negative balance");
		}
		var account = new Account(sum);
		var acc = accountService.createAccount(account);
		var transaction = new Deposit(sum, account.getCbu());
		var account1 = accountService.deposit(transaction, acc);
		return account1;
	}
	@GetMapping("/accounts")
	public Collection<Account> getAccounts() {
		return accountService.getAccounts();
	}
	@GetMapping("/transactions")
	public Collection<Transaction> getTransactions() {
		return transactionService.getTransactions();
	}
	@GetMapping("/accounts/{cbu}")
	public ResponseEntity<Account> getAccount(@PathVariable Long cbu) {
		Optional<Account> accountOptional = accountService.findById(cbu);
		return ResponseEntity.of(accountOptional);
	}
	@GetMapping("/transactions/{id}")
	public ResponseEntity<Transaction> getTransaction(@PathVariable Long id) {
		Optional<Transaction> transactionOptional = transactionService.findById(id);
		return ResponseEntity.of(transactionOptional);
	}
	@GetMapping()
	public ResponseEntity<List<Transaction>> getTransactionByAccount(@RequestParam Long cbu) {
		Optional<Account> accountOptional = accountService.findById(cbu);
		if (accountOptional.isEmpty()) {
			return ResponseEntity.notFound().build();
		}
		var acccount = accountOptional.get();
		var transactions = acccount.getTransactions();
		return ResponseEntity.ok(transactions);
	}
	@PutMapping("/accounts/{cbu}")
	public ResponseEntity<Account> updateAccount(@RequestBody Account account, @PathVariable Long cbu) {
		Optional<Account> accountOptional = accountService.findById(cbu);

		if (!accountOptional.isPresent()) {
			return ResponseEntity.notFound().build();
		}
		account.setCbu(cbu);
		accountService.save(account);
		return ResponseEntity.ok().build();
	}

	@DeleteMapping("/accounts/{cbu}")
	public void deleteAccount(@PathVariable Long cbu) {
		accountService.deleteById(cbu);
	}
	@DeleteMapping("/transactions/{id}")
	public void deleteTransaction(@PathVariable Long id) {
		Optional<Transaction> transactionOptional = transactionService.findById(id);
		if (transactionOptional.isEmpty()) {
			throw new InexistantTransactionException("Transaction doesn't exist");
		}
		var transaction = transactionOptional.get();
		var acc = accountService.findById(transaction.getTransactionOwner());
		var account = acc.get();
		account.deleteTransaction(transaction.getID());
		transactionService.deleteById(transaction.getID());
	}

	@PutMapping("/accounts/{cbu}/withdraw")
	public Account withdraw(@PathVariable Long cbu, @RequestParam Double sum) {
		var transaction = new Withdraw(-sum, cbu);
		return accountService.withdraw(transaction);
	}

	@PutMapping("/accounts/{cbu}/deposit")
	public Account deposit(@PathVariable Long cbu, @RequestParam Double sum) {
		var transaction = new Deposit(sum, cbu);
		return accountService.deposit(transaction);
	}

	@Bean
	public Docket apiDocket() {
		return new Docket(DocumentationType.SWAGGER_2)
			.select()
			.apis(RequestHandlerSelectors.any())
			.paths(PathSelectors.any())
			.build();
	}
}
