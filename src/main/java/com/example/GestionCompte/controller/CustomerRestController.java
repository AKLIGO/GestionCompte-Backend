package com.example.GestionCompte.controller;

import com.example.GestionCompte.Exception.CustomerNotFoundException;
import com.example.GestionCompte.dtos.CustomerDTO;
import com.example.GestionCompte.entites.Customer;
import com.example.GestionCompte.services.BanKAcountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j

public class CustomerRestController {
    private BanKAcountService banKAcountService;

    @GetMapping("/customers")
    public List<CustomerDTO> customers(){
        return banKAcountService.listCustomer();
    }

    @GetMapping("customers/{id}")
    public CustomerDTO getCustomerById(@PathVariable (name="id") Long customerId) throws CustomerNotFoundException {
        return banKAcountService.getCustomer(customerId);
    }

    @PostMapping("/customers")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
            return banKAcountService.saveCustomer(customerDTO);
    }

    @PutMapping("/customers/{customerId}")
    public CustomerDTO updateCustomer(@PathVariable Long customerId, @RequestBody CustomerDTO customerDTO){
        customerDTO.setId(customerId);
        return banKAcountService.updateCustomer(customerDTO);
    }

    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable Long id) throws CustomerNotFoundException{
        log.info("Request to delete customer with id: {}", id);
        banKAcountService.deletCustomer(id);
    }




}
