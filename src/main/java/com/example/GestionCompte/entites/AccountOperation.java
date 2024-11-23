package com.example.GestionCompte.entites;


import com.example.GestionCompte.enums.OperationType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
public class AccountOperation {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Date operationDate;

    private String description;
    private double amount;

    @Enumerated(EnumType.STRING)

    private OperationType operationType;
    @ManyToOne
    private BankAccount bankAccount;
}
