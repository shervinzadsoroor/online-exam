package com.shervin.maktabfinalproject.crudrepositories.accountrepository;

import com.shervin.maktabfinalproject.models.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@RequestMapping("/account")
@Controller
public class AccountController {

    @Autowired
    private AccountService accountService;

    @GetMapping("/login")
    public String sendLoginForm(Model model) {
        model.addAttribute("account", new Account());
        return "loginPage";
    }

    @GetMapping("/signUp")
    public String sendSignUpForm(Model model) {
        model.addAttribute("account", new Account());
        return "signUpPage";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute Account account) {
        //checks the existence of the account by username
        boolean isAccountExist = accountService.isAccountExist(account);
        boolean isPasswordValid = accountService.isPasswordValid(account);
        if (isAccountExist && isPasswordValid) {
            Account loggedInAccount = accountService.findAccountByUsername(account.getUsername());
            loggedInAccount.setLastLoginDate(new Date());
            accountService.saveAccount(loggedInAccount);

            return "loginSuccessful";
        }
        return "errorInLogin";
    }

    @PostMapping("/signUp")
    public String signUp(@ModelAttribute Account account) {
        boolean isAccountExist = accountService.isAccountExist(account);
        if (isAccountExist) {
            return "errorInSignUp";
        }
        accountService.saveAccount(account);
        Account persistedAccount = accountService.findAccountByUsername(account.getUsername());
        persistedAccount.setActive(true);
        persistedAccount.setStatus("not registered");
        accountService.saveAccount(persistedAccount);
        return "signUpSuccessful";
    }
}
