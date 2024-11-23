package com.example.GestionCompte.repository;

import com.example.GestionCompte.entites.SavingAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SavingBankAccountRepository extends JpaRepository<SavingAccount, Long> {
}
