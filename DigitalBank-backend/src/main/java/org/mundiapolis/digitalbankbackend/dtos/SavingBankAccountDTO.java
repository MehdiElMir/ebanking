package org.mundiapolis.digitalbankbackend.dtos;

import lombok.Data;
import org.mundiapolis.digitalbankbackend.enums.AccountStatus;

import java.util.Date;
import java.util.List;


@Data
public class SavingBankAccountDTO extends BankAccountDTO {

    private String id;
    private double balance;
    private Date createdAt;
    private AccountStatus status;
    private CustomerDTO customerDTO;
    private double interestRate;
}
