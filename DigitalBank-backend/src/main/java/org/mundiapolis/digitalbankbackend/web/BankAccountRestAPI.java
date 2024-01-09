package org.mundiapolis.digitalbankbackend.web;

import lombok.AllArgsConstructor;
import org.mundiapolis.digitalbankbackend.dtos.*;
import org.mundiapolis.digitalbankbackend.exceptions.BalanceNotSufficientException;
import org.mundiapolis.digitalbankbackend.exceptions.BankAccountNotFoundException;
import org.mundiapolis.digitalbankbackend.services.BankAccountService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin("*")
public class BankAccountRestAPI {

    private BankAccountService bankAccountService;

    public BankAccountRestAPI(BankAccountService bankAccountService) {
        this.bankAccountService = bankAccountService;
    }


    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    @GetMapping("/accounts/{accountId}")
    public BankAccountDTO getBankAccount(@PathVariable String accountId) throws BankAccountNotFoundException {
        return bankAccountService.getBankAccount(accountId);

    }


    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    @GetMapping("/accounts")
    public List<BankAccountDTO> listAccounts(){
        return bankAccountService.bankAccountList();
    }


    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    @GetMapping("accounts/{accountId}/operations")
    public List<AccountOperationDTO> getHistory(@PathVariable String accountId){
        return bankAccountService.accoutHistory(accountId);
    }


    @PreAuthorize("hasAuthority('SCOPE_ROLE_USER')")
    @GetMapping("accounts/{accountId}/pageOperations")
    public AccountHistoryDTO getAccountHistory(@PathVariable String accountId,
                                                     @RequestParam(name="page",defaultValue = "0") int page,
                                                     @RequestParam(name="size",defaultValue = "5")int size) throws BankAccountNotFoundException {
        return bankAccountService.getAccountHistory(accountId,page,size);
    }


    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @PostMapping("/accounts/debit")
    public DebitDTO debit(@RequestBody DebitDTO debitDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
        this.bankAccountService.debit(debitDTO.getAccountId(), debitDTO.getAmount(), debitDTO.getDescription());
        return debitDTO;
    }


    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @PostMapping("/accounts/credit")
    public CreditDTO debit(@RequestBody CreditDTO creditDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
        this.bankAccountService.debit(creditDTO.getAccountId(), creditDTO.getAmount(), creditDTO.getDescription());
        return creditDTO;
    }


    @PreAuthorize("hasAuthority('SCOPE_ROLE_ADMIN')")
    @PostMapping("/accounts/transfert")
    public TransfertRequestDTO transfert(@RequestBody TransfertRequestDTO transfertRequestDTO) throws BankAccountNotFoundException, BalanceNotSufficientException {
        this.bankAccountService.transfert(transfertRequestDTO.getAccountSource(),transfertRequestDTO.getAccountDestination(), transfertRequestDTO.getAmount());
        return transfertRequestDTO;
    }
}
