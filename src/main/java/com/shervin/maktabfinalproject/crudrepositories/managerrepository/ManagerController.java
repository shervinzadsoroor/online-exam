package com.shervin.maktabfinalproject.crudrepositories.managerrepository;

import com.shervin.maktabfinalproject.crudrepositories.accountrepository.AccountService;
import com.shervin.maktabfinalproject.models.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/manager")
@Controller
public class ManagerController {
    @Autowired
    private ManagerService managerService;
    @Autowired
    private AccountService accountService;


    @GetMapping("/home")
    public String showWelcomePage() {
        return "managerWelcomePage";
    }


    @GetMapping("/dashboard/{userName}")
    public String showCollegianDashboard(@PathVariable("userName") String username) {

        Account account = accountService.findAccountByUsername(username);
        System.out.println("username = " + username);
        return "managerDashboard";
    }


    @GetMapping("/setting")
    public String showSettingPage() {
        // TODO: 2/24/20
        return null;
    }

}
