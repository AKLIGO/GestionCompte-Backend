package com.example.GestionCompte.dtos;

import com.example.GestionCompte.entites.BankAccount;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data

public class CustomerDTO {

    private Long id;
    private String name;
    private String email;



}
