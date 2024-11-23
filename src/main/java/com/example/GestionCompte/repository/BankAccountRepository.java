package com.example.GestionCompte.repository;

import com.example.GestionCompte.entites.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount, String> {
}
