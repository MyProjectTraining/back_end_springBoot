package com.example.back_end_java.service;

import com.example.back_end_java.components.AccountDTO;
import com.example.back_end_java.entity.Type.Devise;
import com.example.back_end_java.entity.account.Account;
import com.example.back_end_java.entity.account.AccountRequest;
import com.example.back_end_java.entity.account.AccountResponse;
import com.example.back_end_java.entity.Type.AccountType;
import com.example.back_end_java.entity.user.User;
import com.example.back_end_java.repository.AccountRepository;
import com.example.back_end_java.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AccountService {
    @Value("${valeurDollar}")
    private int valeurDollar;
    @Value("${valeurEuro}")
    private int valeurEuro;
    private final AccountDTO accountDTO;
    private final AccountRepository accountRepository;
    private final UserRepository userRepository;

    public AccountService(AccountDTO accountDTO, AccountRepository accountRepository, UserRepository userRepository) {
        this.accountDTO = accountDTO;
        this.accountRepository = accountRepository;
        this.userRepository = userRepository;
    }

    public AccountResponse creationAccount(AccountRequest accountRequest, String email){
        User userExisting = userRepository.getByEmail(email);
        if (userExisting == null){
            throw new Error(email + "not existing");
        }
        Account existingtypeAccount = accountRepository.getAccountByEmailAndAccountType(email,accountRequest.getAccountType());
        if( existingtypeAccount != null ){
            int conversion = conversion(accountRequest) + existingtypeAccount.getMoney();
            existingtypeAccount.setMoney(conversion);
            return accountDTO.DTO(accountRepository.save(existingtypeAccount));
        }
        Account account = new Account();
        account.setAccountType(accountRequest.getAccountType());
        account.setMoney(accountRequest.getMoney());
        account.setDescription(accountRequest.getDescription());
        account.setDevise(accountRequest.getDevise());
        account.setUser(userExisting);
        Account saveAccount = accountRepository.save(account);
        return accountDTO.DTO(saveAccount);
    }

    public List<AccountResponse> getAll(String email){
        List<AccountResponse> accountResponseList = new ArrayList<>();
        User user = userRepository.getByEmail(email);
        if (user == null){
            throw new Error("no uer found" + user);
        }
        List<Account> accountOfEmails = user.getAccounts();
        for (Account account : accountOfEmails) {
           accountResponseList.add(accountDTO.DTO(account));
        }

        return accountResponseList;
    }

    public AccountResponse getAccountByUserAndUser(String email , AccountType accountType){
        return accountDTO.DTO(accountRepository.getAccountByEmailAndAccountType(email  ,accountType));
    }

    private int conversion(AccountRequest accountNew){
        int total = 0 ;
        if (accountNew.getDevise().equals(Devise.Dollar)){
            total = (accountNew.getMoney() * valeurDollar);
        }
        if (accountNew.getDevise().equals(Devise.Euro)){
           total = accountNew.getMoney() * valeurEuro;
        }
        if (accountNew.getDevise().equals(Devise.Ar)){
            total = accountNew.getMoney();
        }
        return total;
    }
}
