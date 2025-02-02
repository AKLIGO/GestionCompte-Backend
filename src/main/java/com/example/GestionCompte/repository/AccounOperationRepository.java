package com.example.GestionCompte.repository;

import com.example.GestionCompte.entites.AccountOperation;

import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;
import java.util.List;

public interface AccounOperationRepository extends JpaRepository<AccountOperation, Long> {
    public List<AccountOperation> findByBankAccountId(String accountId);

   Page<AccountOperation> findByBankAccountId(String accountId, Pageable pageable);
}
