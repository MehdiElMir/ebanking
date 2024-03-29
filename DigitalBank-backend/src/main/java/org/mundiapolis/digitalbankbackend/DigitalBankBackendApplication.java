package org.mundiapolis.digitalbankbackend;

import org.mundiapolis.digitalbankbackend.dtos.BankAccountDTO;
import org.mundiapolis.digitalbankbackend.dtos.CurrentBankAccountDTO;
import org.mundiapolis.digitalbankbackend.dtos.CustomerDTO;
import org.mundiapolis.digitalbankbackend.dtos.SavingBankAccountDTO;
import org.mundiapolis.digitalbankbackend.entities.*;
import org.mundiapolis.digitalbankbackend.enums.AccountStatus;
import org.mundiapolis.digitalbankbackend.enums.OperationType;
import org.mundiapolis.digitalbankbackend.exceptions.BalanceNotSufficientException;
import org.mundiapolis.digitalbankbackend.exceptions.BankAccountNotFoundException;
import org.mundiapolis.digitalbankbackend.exceptions.CustomerNotFoundException;
import org.mundiapolis.digitalbankbackend.repositories.AccountOperationRepository;
import org.mundiapolis.digitalbankbackend.repositories.BankAccountRepository;
import org.mundiapolis.digitalbankbackend.repositories.CustomerRepository;
import org.mundiapolis.digitalbankbackend.security.service.AccountService;
import org.mundiapolis.digitalbankbackend.services.BankAccountService;
import org.mundiapolis.digitalbankbackend.services.BankService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class DigitalBankBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(DigitalBankBackendApplication.class, args);
    }
    @Bean
    CommandLineRunner commandLineRunner(BankAccountService bankAccountService){
        return args -> {
            Stream.of("Hassan","Imane","Mohamed").forEach(name->{
                CustomerDTO customer = new CustomerDTO();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                bankAccountService.saveCustomer(customer);
            });
            bankAccountService.listCustomers().forEach(customer -> {
                try {
                    bankAccountService.saveCurrentBankAccount(Math.random()*90000,9000, customer.getId());
                    bankAccountService.saveSavingBankAccount(Math.random()*120000,5.5, customer.getId());


                } catch (CustomerNotFoundException e) {
                    e.printStackTrace();
                }
            });
            List<BankAccountDTO> bankAccounts = bankAccountService.bankAccountList();
            for ( BankAccountDTO bankAccount : bankAccounts){
                for (int i = 0; i < 10; i++) {
                    String accountId;
                    if(bankAccount instanceof SavingBankAccountDTO){
                        accountId = ((SavingBankAccountDTO) bankAccount).getId();
                    }
                    else{
                        accountId = ((CurrentBankAccountDTO) bankAccount).getId();
                    }
                    bankAccountService.credit(accountId, 10000+Math.random()*120000, "Credit");
                    bankAccountService.debit(accountId, 1000+Math.random()*9000,"Debit");
                }

            }
        };
    }

    @Bean
    CommandLineRunner commandLineRunnerUserDetails(AccountService accountService){
        return args -> {
            accountService.addnewRole("USER");
            accountService.addnewRole("ADMIN");
            accountService.addnewUser("user1","user1@gmail.com","1234","1234");
            accountService.addnewUser("user2","user2@gmail.com","1234","1234");
            accountService.addnewUser("admin","admin@gmail.com","1234","1234");

            accountService.addRoleToUser("user1","USER");
            accountService.addRoleToUser("user2","USER");
            accountService.addRoleToUser("admin","ADMIN");
            accountService.addRoleToUser("admin","USER");
            accountService.addRoleToUser("admin","USER");


        };
    }

    //@Bean
    CommandLineRunner start(CustomerRepository customerRepository,
                            BankAccountRepository bankAccountRepository,
                            AccountOperationRepository accountOperationRepository){
        return args -> {
            Stream.of("Hassan","Yassin","Aicha").forEach(name->{
                Customer customer = new Customer();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                customerRepository.save(customer);
            });
            customerRepository.findAll().forEach(customer -> {
                CurrentAccount currentAccount = new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setBalance(Math.random()*90000);
                currentAccount.setCreatedAt(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setCustomer(customer);
                currentAccount.setOverDraft(9000);
                bankAccountRepository.save(currentAccount);

                SavingAccount savingAccount = new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setBalance(Math.random()*90000);
                savingAccount.setCreatedAt(new Date());
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setCustomer(customer);
                savingAccount.setInterestRate(5.5);
                bankAccountRepository.save(savingAccount);
            });
            
            bankAccountRepository.findAll().forEach(acc ->
                    {
                        for (int i = 0; i < 5; i++) {
                            AccountOperation accountOperation = new AccountOperation();
                            accountOperation.setOperationDate(new Date());
                            accountOperation.setAmount(Math.random()*120000);
                            accountOperation.setType(Math.random()>0.5? OperationType.CREDIT : OperationType.DEBIT);
                            accountOperation.setBankAccount(acc);
                            accountOperationRepository.save(accountOperation);
                        }
                        }


                    );


        };
    }

}
