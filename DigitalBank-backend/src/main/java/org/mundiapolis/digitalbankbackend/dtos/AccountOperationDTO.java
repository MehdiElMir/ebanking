package org.mundiapolis.digitalbankbackend.dtos;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.mundiapolis.digitalbankbackend.entities.BankAccount;
import org.mundiapolis.digitalbankbackend.enums.OperationType;

import java.util.Date;

@Data
public class AccountOperationDTO {

    private Long id;
    private Date operationDate;
    private double amount;
    private OperationType type;

    private String description;

}
