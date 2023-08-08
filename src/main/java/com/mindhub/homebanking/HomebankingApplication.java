package com.mindhub.homebanking;

import com.mindhub.homebanking.models.Account;
import com.mindhub.homebanking.models.Client;
import com.mindhub.homebanking.models.Transaction;
import com.mindhub.homebanking.models.TransactionType;
import com.mindhub.homebanking.repositories.AccountRepository;
import com.mindhub.homebanking.repositories.ClientRepository;
import com.mindhub.homebanking.repositories.TransactionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;

@SpringBootApplication
public class HomebankingApplication {

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository) {
		return (args) -> {
			Client melba = new Client("Melba", "Morel","melba@mindhub.com");
			Client valentin = new Client("Valentin", "Pisani","valentin.pisani@hotmail.com");

			Account account1 = new Account("VIN001", LocalDate.now(),5000);
			Account account2 = new Account("VIN002", LocalDate.now().plusDays(1),7500);
			Account account3 = new Account("VIN003", LocalDate.now().plusDays(1),3000);
			Account account4 = new Account("VIN004", LocalDate.now(),4500);

			Transaction transaction1 = new Transaction(-1000f,"Debito1", TransactionType.debito);
			Transaction transaction2 = new Transaction(-6000f,"Debito2", TransactionType.debito);
			Transaction transaction3 = new Transaction(3000f,"Credito1", TransactionType.credito);
			Transaction transaction4 = new Transaction(2500f,"Credito2", TransactionType.credito);

			account1.addTransaction(transaction1);
			account1.addTransaction(transaction2);
			account1.addTransaction(transaction3);
			account2.addTransaction(transaction4);

			transaction1.setAccount(account1);
			transaction2.setAccount(account1);
			transaction3.setAccount(account1);
			transaction4.setAccount(account2);

			melba.addAccount(account1);
			melba.addAccount(account2);
			valentin.addAccount(account3);
			valentin.addAccount(account4);

			account1.setClient(melba);
			account2.setClient(melba);
			account3.setClient(valentin);
			account4.setClient(valentin);

			clientRepository.save(melba);
			clientRepository.save(valentin);

			accountRepository.save(account1);
			accountRepository.save(account2);
			accountRepository.save(account3);
			accountRepository.save(account4);

			transactionRepository.save(transaction1);
			transactionRepository.save(transaction2);
			transactionRepository.save(transaction3);
			transactionRepository.save(transaction4);

		};
	}
}
