package com.shervin.maktabfinalproject.crudrepositories.accountrepository;

import com.shervin.maktabfinalproject.models.Account;
import com.shervin.maktabfinalproject.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    public Account saveAccount(Account account) {
       return  accountRepository.save(account);
    }

    public Optional<Account> findAccountById(Long id) {
        return accountRepository.findById(id);
    }

    public boolean isAccountExist(Account account){
        List<Account>list = accountRepository.findAllByUsername(account.getUsername());
        return list.size() > 0;
    }

    public boolean isPasswordValid(Account account){
        List<Account>list = accountRepository.findAllByPassword(account.getPassword());
        return list.size() > 0;
    }

    public boolean isAccountRegistered(Account account){
        List<Account>list = accountRepository.findAllByPerson(account.getPerson());
        return list.size() > 0;
    }
    public Account findAccountByUsername(String username){
       return accountRepository.findByUsername(username);
    }

    public List<Account> showWaitingAccountsList(){
       return accountRepository.findAllByStatus("waiting");
    }

}