package com.example.back_end_java.repository;

import com.example.back_end_java.entity.account.Account;
import com.example.back_end_java.entity.account.Type.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AccountRepository extends JpaRepository<Account, AccountType> {
    @Query("select a from Account a join fetch a.user u")
    Account getAccountByEmailAndAccountType(String email , AccountType accountType);
}
