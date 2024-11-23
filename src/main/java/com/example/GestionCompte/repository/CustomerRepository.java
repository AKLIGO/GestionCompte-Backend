package com.example.GestionCompte.repository;

import com.example.GestionCompte.entites.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
