package com.example.GestionCompte.services;

import com.example.GestionCompte.Exception.BalanceNotSuffissantException;
import com.example.GestionCompte.Exception.BankAccountNotFoundException;
import com.example.GestionCompte.Exception.CustomerNotFoundException;
import com.example.GestionCompte.dtos.*;
import com.example.GestionCompte.entites.BankAccount;
import com.example.GestionCompte.entites.CurrentAccount;
import com.example.GestionCompte.entites.Customer;
import com.example.GestionCompte.entites.SavingAccount;

import java.util.List;

public interface BanKAcountService {



    public CurrentBankAccountDTO saveCurrentBankAccount(double intitialBalance, Long customerId, double overDraft) throws CustomerNotFoundException;

    public SavingBankAccountDTO saveSavingtBankAccount(double intitialBalance, Long customerId, double interestRate) throws CustomerNotFoundException;
    public List<CustomerDTO> listCustomer();

    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;

    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSuffissantException;

    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException;

    public void transfert(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSuffissantException;


    List<BankAccountDTO> bankAccountList();

    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;

    CustomerDTO saveCustomer(CustomerDTO customerDTO);


    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    void deletCustomer(Long customerId);

    List<AccountOperationDTO> accountHistory(String accountId);


    AccountHistoryDTO getAccountHistory(String accountId, int page, int sizePage);
}
