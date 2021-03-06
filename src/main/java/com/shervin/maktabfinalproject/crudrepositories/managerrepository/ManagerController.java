package com.shervin.maktabfinalproject.crudrepositories.managerrepository;

import com.shervin.maktabfinalproject.crudrepositories.accountrepository.AccountService;
import com.shervin.maktabfinalproject.models.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;

@RequestMapping("/manager")
@Controller
public class ManagerController {
    private final ManagerService managerService;
    private final AccountService accountService;

    @Autowired
    public ManagerController(ManagerService managerService, AccountService accountService) {
        this.managerService = managerService;
        this.accountService = accountService;
    }


    @GetMapping("/home")
    public String showWelcomePage() {
        return "managerWelcomePage";
    }


    @GetMapping("/dashboard/{userName}")
    public String showManagerDashboard(@PathVariable("userName") String username) {
        Account account = accountService.findAccountByUsername(username);
        account.setLastLoginDate(new Date());
        accountService.saveAccount(account);
        //the account is not been used.
        // TODO: 3/7/20 use manager account details in manager dashboard page
        return "managerDashboard";
    }


    @GetMapping("/setting")
    public String showSettingPage() {
        // TODO: 2/24/20
        return null;
    }

}
