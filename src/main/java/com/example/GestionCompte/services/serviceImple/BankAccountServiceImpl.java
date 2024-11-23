package com.example.GestionCompte.services.serviceImple;

import com.example.GestionCompte.Exception.BalanceNotSuffissantException;
import com.example.GestionCompte.Exception.BankAccountNotFoundException;
import com.example.GestionCompte.Exception.CustomerNotFoundException;
import com.example.GestionCompte.dtos.*;
import com.example.GestionCompte.entites.*;
import com.example.GestionCompte.enums.OperationType;
import com.example.GestionCompte.mappers.BankAccountMappersImpl;
import com.example.GestionCompte.repository.AccounOperationRepository;
import com.example.GestionCompte.repository.BankAccountRepository;
import com.example.GestionCompte.repository.CustomerRepository;
import com.example.GestionCompte.services.BanKAcountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@Slf4j

public class BankAccountServiceImpl implements BanKAcountService {


    private CustomerRepository customerRepository;

    private BankAccountMappersImpl bankAccountMappers;
    private BankAccountRepository bankAccountRepository;


    private AccounOperationRepository accounOperationRepository;

    public BankAccountServiceImpl(CustomerRepository customerRepository, BankAccountMappersImpl bankAccountMappers,
                                  BankAccountRepository bankAccountRepository,
                                  AccounOperationRepository accounOperationRepository) {

        this.customerRepository = customerRepository;
        this.bankAccountMappers = bankAccountMappers;
        this.bankAccountRepository = bankAccountRepository;
        this.accounOperationRepository = accounOperationRepository;
    }

//    Log logger= (Logger) LoggerFactory.getLogger(this.getClass().getName());
//    @Override
//    public Customer saveCustomer(Customer customer) {
//        log.info("Saving new Customer");
//       Customer saveCustomer= customerRepository.save(customer);
//        return saveCustomer;
//    }

    @Override
    public CurrentBankAccountDTO saveCurrentBankAccount(double intitialBalance, Long customerId, double overDraft) throws CustomerNotFoundException {

        Customer customer=customerRepository.findById(customerId).orElseThrow();

        if (customer==null){
            throw new CustomerNotFoundException("Customer not Found");
        }

        CurrentAccount currentAccount=new CurrentAccount();


        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setCreatedAt(new Date());
        currentAccount.setBalance(intitialBalance);
        currentAccount.setOverDraft(overDraft);
        currentAccount.setCustomer(customer);

        CurrentAccount saveBankAccount =bankAccountRepository.save(currentAccount);
        return bankAccountMappers.fromCurrentBankAccount(saveBankAccount);
    }

    @Override
    public SavingBankAccountDTO saveSavingtBankAccount(double intitialBalance, Long customerId, double interestRate) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(customerId).orElseThrow();

        if (customer==null){
            throw new CustomerNotFoundException("Customer not Found");
        }

        SavingAccount savingAccount=new SavingAccount();


        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCreatedAt(new Date());
        savingAccount.setBalance(intitialBalance);
        savingAccount.setInterstRate(interestRate);
        savingAccount.setCustomer(customer);

        SavingAccount saveBankAccount =bankAccountRepository.save(savingAccount);
        return bankAccountMappers.fromSavingBankAccount(saveBankAccount);
    }


    @Override
    public List<CustomerDTO> listCustomer() {
        List<Customer> customers=customerRepository.findAll();

       List<CustomerDTO> listCustomersDTO =customers.stream()
               .map(customer -> bankAccountMappers.fromCustomerDTO(customer))
               .collect(Collectors.toList());

        //programation imperatif classique

//        List<CustomerDTO> customerDTOS=new ArrayList<>();
//
//        for (Customer customer:customers){
//            CustomerDTO customerDTO=bankAccountMappers.fromCustomer(customer);
//            customerDTOS.add(customerDTO);
//        }

        return listCustomersDTO;
    }

    @Override
    public BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException {
        BankAccount bankAccount=bankAccountRepository.findById(accountId)
                .orElseThrow(()-> new BankAccountNotFoundException("BankAccount not Found"));

        if (bankAccount instanceof SavingAccount){
            SavingAccount savingAccount=(SavingAccount) bankAccount;
            return bankAccountMappers.fromSavingBankAccount(savingAccount);
        }else {
            CurrentAccount currentAccount=(CurrentAccount) bankAccount;
            return bankAccountMappers.fromCurrentBankAccount(currentAccount);
        }

    }

    @Override
    public void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSuffissantException {

        BankAccount bankAccount=bankAccountRepository.findById(accountId)
                .orElseThrow(()-> new BankAccountNotFoundException("BankAccount not Found"));


        if(bankAccount.getBalance()<amount)

                throw new BalanceNotSuffissantException("Balance not suffissant");
            AccountOperation accountOperation=new AccountOperation();
            accountOperation.setOperationType(OperationType.DEBIT);
            accountOperation.setAmount(amount);
            accountOperation.setDescription(description);
            accountOperation.setOperationDate(new Date());
            accounOperationRepository.save(accountOperation);
            bankAccount.setBalance(bankAccount.getBalance()-amount);

    }

    @Override
    public void credit(String accountId, double amount, String description) throws BankAccountNotFoundException{

        BankAccount bankAccount=bankAccountRepository.findById(accountId)
                .orElseThrow(()-> new BankAccountNotFoundException("BankAccount not Found"));

        AccountOperation accountOperation=new AccountOperation();
        accountOperation.setOperationType(OperationType.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accounOperationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()+ amount);


    }

    @Override
    public void transfert(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSuffissantException {
        debit(accountIdSource,amount, "Transfer"+accountIdDestination);
        credit(accountIdDestination,amount,"Transfer from"+accountIdSource);

    }

    @Override
    public List<BankAccountDTO> bankAccountList(){
        List<BankAccount> bankAccounts=bankAccountRepository.findAll();
        List<BankAccountDTO> bankAccountListDTOs =  bankAccounts.stream().map(bankAccount -> {
            if (bankAccount instanceof SavingAccount){
                SavingAccount savingAccount=(SavingAccount) bankAccount;
                return bankAccountMappers.fromSavingBankAccount(savingAccount);
            }
            else {
                CurrentAccount currentAccount=(CurrentAccount) bankAccount;
                return bankAccountMappers.fromCurrentBankAccount(currentAccount);
            }
        }).collect(Collectors.toList());
        return bankAccountListDTOs;
    }

    @Override
    public CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException {
        Customer customer=  customerRepository.findById(customerId)
                .orElseThrow(()->new CustomerNotFoundException("Customer Not Found"));
        return bankAccountMappers.fromCustomerDTO(customer);
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        log.info("Saving new Customer");

        Customer customer=bankAccountMappers.fromCustomerDTO(customerDTO);
        Customer saveCustomer= customerRepository.save(customer);
        return bankAccountMappers.fromCustomerDTO(saveCustomer);
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        log.info("Saving new Customer");

        Customer customer=bankAccountMappers.fromCustomerDTO(customerDTO);
        Customer saveCustomer= customerRepository.save(customer);
        return bankAccountMappers.fromCustomerDTO(saveCustomer);
    }

    @Override
    public void deletCustomer(Long customerId){
        customerRepository.deleteById(customerId);
    }



    @Override
    public List<AccountOperationDTO> accountHistory(String accountId){
        List<AccountOperation> accountOperations=accounOperationRepository.findByBankAccountId(accountId);

      return   accountOperations.stream().map(op->bankAccountMappers.fromAccountOperation(op)).collect(Collectors.toList());
    }

    @Override
    public AccountHistoryDTO getAccountHistory(String accountId, int page, int sizePage) {
//       Page<AccountOperation> accountOperations= accounOperationRepository.findByBankAccountId(accountId, (Pageable) PageRequest.of(page, sizePage));
//
//        AccountHistoryDTO accountHistoryDTO=new AccountHistoryDTO();
//
//        List<AccountOperationDTO> accountOperationDTOList=accountOperations
//                .getContent().stream()
//                .map(op->bankAccountMappers
//                .fromAccountOperation(op))
//                .collect(Collectors.toList());
//
//        accountHistoryDTO.setAccountHistoryDTOS();




        return null;
    }


}
