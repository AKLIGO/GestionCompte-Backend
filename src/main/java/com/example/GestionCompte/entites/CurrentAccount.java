package com.example.GestionCompte.entites;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

@Entity
@DiscriminatorValue("CA")
public class CurrentAccount extends BankAccount{
    private double overDraft;

}
