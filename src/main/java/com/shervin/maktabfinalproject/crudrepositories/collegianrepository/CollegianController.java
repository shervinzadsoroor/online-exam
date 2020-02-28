package com.shervin.maktabfinalproject.crudrepositories.collegianrepository;

import com.shervin.maktabfinalproject.crudrepositories.accountrepository.AccountService;
import com.shervin.maktabfinalproject.models.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/collegian")
@Controller
public class CollegianController {
    @Autowired
    private CollegianService collegianService;
    @Autowired
    private AccountService accountService;

    @GetMapping("/dashboard/{userName}")
    public String showCollegianDashboard(@PathVariable("userName") String username) {

        //if collegian account's status is not registered redirect to registrationForm.html
        //else if status is waiting redirect to registrationWaitingDetails.html
        //and else if status is registered show the dashboard to the collegian
        Account account = accountService.findAccountByUsername(username);
        System.out.println("username = " + username);
        return "collegianDashboard";
    }

}
