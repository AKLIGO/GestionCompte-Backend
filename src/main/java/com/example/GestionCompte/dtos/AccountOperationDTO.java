package com.example.GestionCompte.dtos;


import com.example.GestionCompte.entites.BankAccount;
import com.example.GestionCompte.enums.OperationType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Data

public class AccountOperationDTO {

    private Long id;

    private Date operationDate;

    private String description;
    private double amount;



    private OperationType operationType;


}
