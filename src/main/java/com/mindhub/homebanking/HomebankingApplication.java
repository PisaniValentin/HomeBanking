package com.mindhub.homebanking;

import com.mindhub.homebanking.models.*;
import com.mindhub.homebanking.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.List;


@SpringBootApplication
public class HomebankingApplication {

	//@Autowired
	//private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(HomebankingApplication.class, args);
	}

	@Bean
	public CommandLineRunner initData(ClientRepository clientRepository, AccountRepository accountRepository, TransactionRepository transactionRepository
	, LoanRepository loanRepository, ClientLoanRepository clientLoanRepository, CardRepository cardRepository) {
		return (args) -> {
/**
			Client melba = new Client("Melba", "Morel","melba@mindhub.com", passwordEncoder.encode("melba"));
			Client valentin = new Client("Valentin", "Pisani","valentin.pisani@hotmail.com",passwordEncoder.encode("valentin"));
			melba.setAuthority("CLIENT");
			valentin.setAuthority("ADMIN");

			Account account1 = new Account("VIN001", LocalDate.now(),5000);
			Account account2 = new Account("VIN002", LocalDate.now().plusDays(1),7500);
			Account account3 = new Account("VIN003", LocalDate.now().plusDays(1),3000);
			Account account4 = new Account("VIN004", LocalDate.now(),4500);

			Transaction transaction1 = new Transaction(-1000f,"Debito1", TransactionType.DEBIT);
			Transaction transaction2 = new Transaction(-6000f,"Debito2", TransactionType.DEBIT);
			Transaction transaction3 = new Transaction(3000f,"Credito1", TransactionType.CREDIT);
			Transaction transaction4 = new Transaction(2500f,"Credito2", TransactionType.CREDIT);

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

			Loan prestamo1 = new Loan("Hipotecario",500000);
			List<Integer> cuotas1 = List.of(12,24,36,48,60);
			prestamo1.setPayments(cuotas1);

			Loan prestamo2 = new Loan("Personal",100000);
			List<Integer> cuotas2 = List.of(6,12,24);
			prestamo2.setPayments(cuotas2);

			Loan prestamo3 = new Loan("Automotriz",300000);
			List<Integer> cuotas3 = List.of(6,12,24,36);
			prestamo3.setPayments(cuotas3);

			ClientLoan clientLoan1 = new ClientLoan(400000,60);
			ClientLoan clientLoan2 = new ClientLoan(50000,12);
			ClientLoan clientLoan3 = new ClientLoan(100000,24);
			ClientLoan clientLoan4 = new ClientLoan(200000,36);

			melba.addClientLoan(clientLoan1);
			melba.addClientLoan(clientLoan2);
			valentin.addClientLoan(clientLoan3);
			valentin.addClientLoan(clientLoan4);

			prestamo1.addClientLoan(clientLoan1);
			prestamo2.addClientLoan(clientLoan2);
			prestamo2.addClientLoan(clientLoan3);
			prestamo3.addClientLoan(clientLoan4);

			Card card1 = new Card(CardColor.GOLD,CardType.DEBIT,"melba","123-123",123,LocalDate.now(),
					LocalDate.now().plusYears(5));
			Card card2 = new Card(CardColor.TITANIUM,CardType.CREDIT,"melba","123-321",124,LocalDate.now(),
					LocalDate.now().plusYears(5));
			Card card3 = new Card(CardColor.SILVER,CardType.CREDIT,"valentin","123-321",124,LocalDate.now(),
					LocalDate.now().plusYears(5));
			card1.setClient(melba);
			card2.setClient(melba);
			card3.setClient(valentin);
			melba.addCard(card1);
			melba.addCard(card2);
			valentin.addCard(card3);

			loanRepository.save(prestamo1);
			loanRepository.save(prestamo2);
			loanRepository.save(prestamo3);

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


			clientLoanRepository.save(clientLoan1);
			clientLoanRepository.save(clientLoan2);
			clientLoanRepository.save(clientLoan3);
			clientLoanRepository.save(clientLoan4);

			cardRepository.save(card1);
			cardRepository.save(card2);
			cardRepository.save(card3);

**/
		};
	}
}
