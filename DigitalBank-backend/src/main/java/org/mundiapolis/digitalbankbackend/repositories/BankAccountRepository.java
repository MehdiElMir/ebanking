package org.mundiapolis.digitalbankbackend.repositories;

import org.mundiapolis.digitalbankbackend.entities.BankAccount;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BankAccountRepository extends JpaRepository<BankAccount,String> {
}
