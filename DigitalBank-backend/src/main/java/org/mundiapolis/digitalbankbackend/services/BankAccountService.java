package org.mundiapolis.digitalbankbackend.services;

import org.mundiapolis.digitalbankbackend.dtos.*;
import org.mundiapolis.digitalbankbackend.entities.BankAccount;
import org.mundiapolis.digitalbankbackend.entities.CurrentAccount;
import org.mundiapolis.digitalbankbackend.entities.Customer;
import org.mundiapolis.digitalbankbackend.entities.SavingAccount;
import org.mundiapolis.digitalbankbackend.exceptions.BalanceNotSufficientException;
import org.mundiapolis.digitalbankbackend.exceptions.BankAccountNotFoundException;
import org.mundiapolis.digitalbankbackend.exceptions.CustomerNotFoundException;

import java.util.List;

public interface BankAccountService {

    CustomerDTO saveCustomer(CustomerDTO customerDTO);

    CurrentBankAccountDTO saveCurrentBankAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
    SavingBankAccountDTO saveSavingBankAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException;
    List<CustomerDTO> listCustomers();
    BankAccountDTO getBankAccount(String accountId) throws BankAccountNotFoundException;
    void debit(String accountId, double amount, String description) throws BankAccountNotFoundException, BalanceNotSufficientException;
    void credit(String accountId, double amount, String description) throws BankAccountNotFoundException;
    void transfert(String accountIdSource, String accountIdDestination, double amount) throws BankAccountNotFoundException, BalanceNotSufficientException;
    List<BankAccountDTO> bankAccountList();

    CustomerDTO getCustomer(Long customerID) throws CustomerNotFoundException;

    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    void  deleteCustomer(Long customerID);

    List<AccountOperationDTO> accoutHistory(String accountId);

    AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws BankAccountNotFoundException;

    List<CustomerDTO> searchCustomers(String keyword);
}
