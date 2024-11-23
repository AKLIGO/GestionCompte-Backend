package com.example.GestionCompte.dtos;

import com.example.GestionCompte.entites.AccountOperation;
import com.example.GestionCompte.entites.Customer;
import com.example.GestionCompte.enums.AccountStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Data

public  class CurrentBankAccountDTO extends BankAccountDTO{



    private String id;
    private String code;

    private double balance;

    private Date createdAt;

    private double overDraft;

    private AccountStatus status;


    private CustomerDTO customerDTO;



}
