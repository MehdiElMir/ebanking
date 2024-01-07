package org.mundiapolis.digitalbankbackend.web;


import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mundiapolis.digitalbankbackend.dtos.CustomerDTO;
import org.mundiapolis.digitalbankbackend.entities.Customer;
import org.mundiapolis.digitalbankbackend.exceptions.CustomerNotFoundException;
import org.mundiapolis.digitalbankbackend.services.BankAccountService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class CustomerRestController {
    private BankAccountService bankAccountService;

    @GetMapping("/customers")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<CustomerDTO> customers(){
        return bankAccountService.listCustomers();
    }
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @GetMapping("/customers/search")
    public List<CustomerDTO> searchCustomers(@RequestParam(name = "keyword",defaultValue = "") String keyword){

        return bankAccountService.searchCustomers(keyword);
    }

    @PreAuthorize("hasAuthority('SCOPE_USER')")
    @GetMapping("/customers/{id}")
    public CustomerDTO getCustomer(@PathVariable(name = "id") Long customerID) throws CustomerNotFoundException {
        return bankAccountService.getCustomer(customerID);
    }

    @PostMapping("/customers")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        return bankAccountService.saveCustomer(customerDTO);
    }
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @PutMapping ("/customers/{customerID}")
    public CustomerDTO updateCustomer(@PathVariable Long customerID ,@RequestBody CustomerDTO customerDTO){
        customerDTO.setId(customerID);
        return bankAccountService.updateCustomer(customerDTO);
    }
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable Long id){
        bankAccountService.deleteCustomer(id);
    }


}
