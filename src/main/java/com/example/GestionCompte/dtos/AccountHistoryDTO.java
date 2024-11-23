package com.example.GestionCompte.dtos;

import lombok.Data;

import java.util.List;

@Data
public class AccountHistoryDTO {

    private String accountId;

    private double balance;

    private int currentPage;
    private int totalPages;

    private int sizePage;

    private List<AccountHistoryDTO> accountHistoryDTOS;
}
