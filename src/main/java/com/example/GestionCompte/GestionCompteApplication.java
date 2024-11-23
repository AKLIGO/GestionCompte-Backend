package com.example.GestionCompte;

import com.example.GestionCompte.entites.AccountOperation;
import com.example.GestionCompte.entites.CurrentAccount;
import com.example.GestionCompte.entites.Customer;
import com.example.GestionCompte.entites.SavingAccount;
import com.example.GestionCompte.enums.AccountStatus;
import com.example.GestionCompte.enums.OperationType;
import com.example.GestionCompte.repository.AccounOperationRepository;

import com.example.GestionCompte.repository.BankAccountRepository;
import com.example.GestionCompte.repository.CustomerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class GestionCompteApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionCompteApplication.class, args);
	}

	@Bean
	@Transactional // Ajout pour garantir des transactions cohérentes
	CommandLineRunner start(CustomerRepository customerRepository,
							AccounOperationRepository accountOperationRepository,
							BankAccountRepository bankAccountRepository) {

		return args -> {
			// Création des clients
			Stream.of("Hassan", "Yassan", "Aicha").forEach(name -> {
				Customer customer = new Customer();
				customer.setName(name);
				customer.setEmail(name + "@gmail.com");
				customerRepository.save(customer);
			});

			// Création des comptes pour chaque client
			customerRepository.findAll().forEach(cust -> {
				// Compte courant
				CurrentAccount currentAccount = new CurrentAccount();
				currentAccount.setId(UUID.randomUUID().toString());
				currentAccount.setBalance(Math.random() * 39990);
				currentAccount.setCreatedAt(new Date());
				currentAccount.setCode(UUID.randomUUID().toString());
				currentAccount.setStatus(AccountStatus.CREATED);
				currentAccount.setCustomer(cust);
				currentAccount.setOverDraft(40000);
				bankAccountRepository.save(currentAccount);

				// Compte épargne
				SavingAccount savingAccount = new SavingAccount();
				savingAccount.setId(UUID.randomUUID().toString());
				savingAccount.setBalance(Math.random() * 39990);
				savingAccount.setCreatedAt(new Date());
				savingAccount.setCode(UUID.randomUUID().toString());
				savingAccount.setStatus(AccountStatus.CREATED);
				savingAccount.setCustomer(cust);
				savingAccount.setInterstRate(4.5);
				bankAccountRepository.save(savingAccount);
			});

			bankAccountRepository.findAll().forEach(acc->{
				for (int i=0; i<5;i++){
					AccountOperation accountOperation=new AccountOperation();
					accountOperation.setOperationDate(new Date());
					accountOperation.setAmount(Math.random()*22323);
					accountOperation.setOperationType(Math.random()>0.5? OperationType.DEBIT:OperationType.CREDIT);
					accountOperation.setBankAccount(acc);

					accountOperationRepository.save(accountOperation);
				}
			});
		};
	}
}
