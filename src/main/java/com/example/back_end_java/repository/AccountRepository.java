package com.example.back_end_java.repository;

import com.example.back_end_java.entity.account.Account;
import com.example.back_end_java.entity.account.Type.AccountType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepository extends JpaRepository<Account, AccountType> {
    @Query("select a from Account a join fetch a.user u where u.email= :email and a.accountType = :accountType ")
    Account getAccountByEmailAndAccountType(@Param("email") String email ,@Param("accountType") AccountType accountType);
}
