package com.shervin.maktabfinalproject.crudrepositories.accountrepository;

import com.shervin.maktabfinalproject.crudrepositories.collegianrepository.CollegianService;
import com.shervin.maktabfinalproject.crudrepositories.instructorrepository.InstructorService;
import com.shervin.maktabfinalproject.crudrepositories.personrepository.PersonService;
import com.shervin.maktabfinalproject.crudrepositories.rolerepository.RoleService;
import com.shervin.maktabfinalproject.models.*;
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
    @Autowired
    private PersonService personService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private CollegianService collegianService;
    @Autowired
    private InstructorService instructorService;

    @GetMapping("/login")
    public String sendLoginForm(Model model) {
        model.addAttribute("account", new Account());
        return "loginPage";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute Account account, Model model) {
        //checks the existence of the account by username
        boolean isAccountExist = accountService.isAccountExist(account);
        boolean isPasswordValid = accountService.isPasswordValid(account);
        if (isAccountExist && isPasswordValid) {
            Account loggedInAccount = accountService.findAccountByUsername(account.getUsername());
            loggedInAccount.setLastLoginDate(new Date());
            accountService.saveAccount(loggedInAccount);

            if (loggedInAccount.getStatus().equals("not registered")) {
                Person person = new Person();
                person.setAccount(loggedInAccount);
//                Collegian collegian  = new Collegian();
//                collegian.setAccount(loggedInAccount);
                model.addAttribute("person", person);
                return "registrationForm";
            }
            if (loggedInAccount.getStatus().equals("waiting for confirmation")) {

            }
            if (loggedInAccount.getStatus().equals("registered")) {

            }
            return "loginSuccessful";
        }
        return "errorInLogin";
    }


    @GetMapping("/signUp")
    public String sendSignUpForm(Model model) {
        model.addAttribute("account", new Account());
        return "signUpPage";
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

    @PostMapping("/register")
    public String register(@ModelAttribute Person person) {
//        accountService.saveAccount(person.getAccount());
        Account account = accountService.findAccountById(person.getAccount().getId()).get();
        Role role = roleService.findRoleByd(person.getAccount().getRole().getId());
        account.setRole(role);
        accountService.saveAccount(account);

        if (role.getId() == 3) {
            Collegian collegian = new Collegian();

            collegian.setAccount(account);
            collegian.setEmail(person.getEmail());
            collegian.setFirstName(person.getFirstName());
            collegian.setLastName(person.getLastName());
            collegian.setPhoneNumber(person.getPhoneNumber());

            Collegian persistedCollegian = collegianService.saveCollegian(collegian);
            Account account1 = persistedCollegian.getAccount();
            account1.setStatus("waiting for confirmation");
            accountService.saveAccount(account1);
        }
        if (role.getId() == 2) {
            Instructor instructor = new Instructor();

            instructor.setAccount(account);
            instructor.setEmail(person.getEmail());
            instructor.setFirstName(person.getFirstName());
            instructor.setLastName(person.getLastName());
            instructor.setPhoneNumber(person.getPhoneNumber());

            Instructor persistedInstructor = instructorService.saveInstructor(instructor);
            Account account1 = persistedInstructor.getAccount();
            account1.setStatus("waiting for confirmation");
            accountService.saveAccount(account1);
        }


//        System.out.println(person.toString());
//        System.out.println(account.toString());
        return null;
    }
}
