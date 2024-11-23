package com.example.GestionCompte.controller;


import com.example.GestionCompte.Exception.BankAccountNotFoundException;
import com.example.GestionCompte.dtos.AccountHistoryDTO;
import com.example.GestionCompte.dtos.AccountOperationDTO;
import com.example.GestionCompte.dtos.BankAccountDTO;
import com.example.GestionCompte.services.BanKAcountService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class BankAccountRestController {

    private BanKAcountService banKAcountService;

    public BankAccountRestController(BanKAcountService banKAcountService) {
        this.banKAcountService = banKAcountService;
    }


    @GetMapping("/accounts/{accountId}")
    public BankAccountDTO getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException {
        return banKAcountService.getBankAccount(accountId);

    }

    @GetMapping("/accounts")
    public List<BankAccountDTO> listAccounts(){
        return banKAcountService.bankAccountList();
    }

    @GetMapping("/accounts/{accountsId}/pageOperations")
    public AccountHistoryDTO getAccountHistory(@PathVariable String accountId,
                                               @RequestParam(name = "page", defaultValue = "0") int page,
                                               @RequestParam(name = "sizePage", defaultValue = "5") int sizePage, int total){
        return banKAcountService.getAccountHistory(accountId, page, sizePage);
    }
}
