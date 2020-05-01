package com.shervin.maktabfinalproject;

import com.shervin.maktabfinalproject.crudrepositories.accountrepository.AccountRepository;
import com.shervin.maktabfinalproject.crudrepositories.accountrepository.AccountService;
import com.shervin.maktabfinalproject.models.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import java.util.Optional;

//@ConfigurationProperties("application.properties")
//@TestPropertySource("../../../../../main/resources/application.properties")

@SpringBootTest
public class AccountTest {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountService accountService;


    @Test
    public void findAccountByUsernameAndPass() {

        String username = "manager";
        String password = "1111";
        Optional<Account> account = accountRepository.findByUsernameAndPassword(username, password);
        System.out.println("account.toString() = " + account.toString());
        Assertions.assertNotNull(account);
    }


}

