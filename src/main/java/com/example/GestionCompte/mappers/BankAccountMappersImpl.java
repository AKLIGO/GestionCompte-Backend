package com.example.GestionCompte.mappers;

import com.example.GestionCompte.dtos.AccountOperationDTO;
import com.example.GestionCompte.dtos.CurrentBankAccountDTO;
import com.example.GestionCompte.dtos.CustomerDTO;
import com.example.GestionCompte.dtos.SavingBankAccountDTO;
import com.example.GestionCompte.entites.AccountOperation;
import com.example.GestionCompte.entites.CurrentAccount;
import com.example.GestionCompte.entites.Customer;
import com.example.GestionCompte.entites.SavingAccount;
import com.fasterxml.jackson.databind.util.BeanUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

@Service
public class BankAccountMappersImpl {
    public CustomerDTO fromCustomerDTO(Customer customer){
        CustomerDTO customerDTO=new CustomerDTO();

        BeanUtils.copyProperties(customer,customerDTO);
//        customerDTO.setId(customer.getId());
//        customerDTO.setName(customer.getName());
//        customerDTO.setEmail(customer.getEmail());
        return customerDTO;
    };

    public Customer fromCustomerDTO(CustomerDTO customerDTO){
        Customer customer=new Customer();
        BeanUtils.copyProperties(customerDTO,customer);
        return customer;

    }

    public SavingBankAccountDTO fromSavingBankAccount(SavingAccount savingAccount){
        SavingBankAccountDTO savingBankAccountDTO=new SavingBankAccountDTO();
        BeanUtils.copyProperties(savingAccount, savingBankAccountDTO);
        savingBankAccountDTO.setCustomerDTO(fromCustomerDTO(savingAccount.getCustomer()));
        savingBankAccountDTO.setType(savingAccount.getClass().getSimpleName());

        return savingBankAccountDTO;
    }

    public SavingAccount fromSavingBankAccount(SavingBankAccountDTO savingBankAccountDTO){
        SavingAccount savingAccount=new SavingAccount();

        BeanUtils.copyProperties(savingBankAccountDTO,savingAccount);
        savingAccount.setCustomer(fromCustomerDTO(savingBankAccountDTO.getCustomerDTO()));
        return savingAccount;

    }

    public CurrentBankAccountDTO fromCurrentBankAccount(CurrentAccount currentAccount){

        CurrentBankAccountDTO currentBankAccountDTO=new CurrentBankAccountDTO();
        BeanUtils.copyProperties(currentAccount, currentBankAccountDTO);

        currentBankAccountDTO.setCustomerDTO(fromCustomerDTO(currentAccount.getCustomer()));

        currentBankAccountDTO.setType(currentAccount.getClass().getSimpleName());

        return  currentBankAccountDTO;

    }

    public CurrentAccount fromCurrentBankAccount(CurrentBankAccountDTO currentBankAccountDTO){

        CurrentAccount currentAccount=new CurrentAccount();
        BeanUtils.copyProperties(currentBankAccountDTO,currentAccount);

        currentAccount.setCustomer(fromCustomerDTO(currentBankAccountDTO.getCustomerDTO()));

        return currentAccount;

    }
    public AccountOperationDTO fromAccountOperation(AccountOperation accountOperation){
        AccountOperationDTO accountOperationDTO =new AccountOperationDTO();
        BeanUtils.copyProperties(accountOperation, accountOperationDTO);
        return accountOperationDTO;
    }


}
