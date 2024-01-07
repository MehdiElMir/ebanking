package org.mundiapolis.digitalbankbackend.services;


import org.mundiapolis.digitalbankbackend.entities.BankAccount;
import org.mundiapolis.digitalbankbackend.entities.CurrentAccount;
import org.mundiapolis.digitalbankbackend.entities.SavingAccount;
import org.mundiapolis.digitalbankbackend.repositories.BankAccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class BankService {
    @Autowired
    private BankAccountRepository bankAccountRepository;
    public void consulter(){
        BankAccount bankAccount = bankAccountRepository.findById("18e0e159-9740-4b00-9973-50e7e0dd80d1").orElse(null);
        if(bankAccount != null){
            System.out.println("************************");
            System.out.println(bankAccount.getId());
            System.out.println(bankAccount.getBalance());
            System.out.println(bankAccount.getCreatedAt());
            System.out.println(bankAccount.getStatus());
            System.out.println(bankAccount.getCustomer().getName());
            System.out.println(bankAccount.getClass().getSimpleName());
            if (bankAccount instanceof CurrentAccount){
                System.out.println("Over Draft =>"+((CurrentAccount)bankAccount).getOverDraft());
            } else if (bankAccount instanceof SavingAccount) {
                System.out.println("Rate =>"+((SavingAccount)bankAccount).getInterestRate());
            }
            bankAccount.getAccountOperations().forEach(op -> {
                System.out.println(op.getType()+"\t"+op.getOperationDate()+"\t"+op.getAmount());
            });
        }
    }
}
