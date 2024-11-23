package com.example.GestionCompte.entites;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.*;

@Data @NoArgsConstructor @AllArgsConstructor @Getter @Setter

@Entity
@DiscriminatorValue("SA")
public class SavingAccount extends BankAccount{
    private double interstRate;

}
