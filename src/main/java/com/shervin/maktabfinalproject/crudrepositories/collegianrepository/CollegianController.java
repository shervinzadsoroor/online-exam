package com.shervin.maktabfinalproject.crudrepositories.collegianrepository;

import com.shervin.maktabfinalproject.crudrepositories.accountrepository.AccountService;
import com.shervin.maktabfinalproject.models.Account;
import com.shervin.maktabfinalproject.models.Collegian;
import com.shervin.maktabfinalproject.models.Person;
import com.shervin.maktabfinalproject.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/collegian")
@Controller
public class CollegianController {
    @Autowired
    private CollegianService collegianService;
    @Autowired
    private AccountService accountService;

    @GetMapping("/dashboard/{userName}")
    public String showCollegianDashboard(@PathVariable("userName") String username, Model model) {

        //if collegian account's status is not registered redirect to registrationForm.html
        //else if status is waiting redirect to registrationWaitingDetails.html
        //and else if status is registered show the dashboard to the collegian
        Account account = accountService.findAccountByUsername(username);

        if (account.getStatus().equalsIgnoreCase("not registered")){

            Collegian collegian = new Collegian();
            collegian.setAccount(account);

            model.addAttribute("collegian", collegian);

            return "registrationFormForCollegian";

        }else if (account.getStatus().equalsIgnoreCase("waiting")){
            model.addAttribute("account", account);
            return "registrationWaitingDetails";

        }
//        else if (account.getStatus().equalsIgnoreCase("registered")){
//
//        }
        model.addAttribute("collegian", (Collegian)account.getPerson());
        return "collegianDashboard";
    }

    @PostMapping("/register")
    public String registerCollegian(@ModelAttribute Collegian collegian){
        collegian.getAccount().setStatus("waiting");
        collegianService.saveCollegian(collegian);
        return "registrationSuccessful";
    }

}
