package com.shervin.maktabfinalproject.crudrepositories.accountrepository;

import com.shervin.maktabfinalproject.crudrepositories.collegianrepository.CollegianService;
import com.shervin.maktabfinalproject.crudrepositories.courserepository.CourseService;
import com.shervin.maktabfinalproject.crudrepositories.instructorrepository.InstructorService;
import com.shervin.maktabfinalproject.crudrepositories.personrepository.PersonService;
import com.shervin.maktabfinalproject.crudrepositories.rolerepository.RoleService;
import com.shervin.maktabfinalproject.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    @Autowired
    private CourseService courseService;

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
                model.addAttribute("person", person);

                List<Role> roles = new ArrayList<>();
                roles.add(roleService.findRoleById(2L)); //  retrieves ROLE_INSTRUCTOR
                roles.add(roleService.findRoleById(3L)); //  retrieves ROLE_COLLEGIAN
                model.addAttribute("roles", roles);
                return "registrationForm";
            }
            if (loggedInAccount.getStatus().equals("waiting")) {
                model.addAttribute("account", loggedInAccount);
                return "registrationWaitingDetails";
            }
            if (loggedInAccount.getStatus().equals("registered")
                    && loggedInAccount.getRole().getTitle().equals("ROLE_INSTRUCTOR")) {

                // TODO: 2/24/20 show dashboard to the instructor
            }

            if (loggedInAccount.getStatus().equals("registered")
                    && loggedInAccount.getRole().getTitle().equals("ROLE_COLLEGIAN")) {

                // TODO: 2/24/20 show dashboard to the collegian
            }

            if (loggedInAccount.getStatus().equals("registered")
                    && loggedInAccount.getRole().getTitle().equals("ROLE_MANAGER")) {

                // TODO: 2/24/20 show dashboard to the manager
                return "managerWelcomePage";
            }
            return "loginSuccessful";
        }
        return "errorInLogin";
    }

    @GetMapping("/signUp")
    public String sendSignUpForm(Model model) {
        model.addAttribute("account", new Account());
        List<Role> roles = new ArrayList<>();
        roles.add(roleService.findRoleById(2L)); //  retrieves ROLE_INSTRUCTOR
        roles.add(roleService.findRoleById(3L)); //  retrieves ROLE_COLLEGIAN
        model.addAttribute("roles", roles);
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
    public String register(@ModelAttribute Person person, Model model) {
        Account account = accountService.findAccountById(person.getAccount().getId()).get();
        Role role = roleService.findRoleById(person.getAccount().getRole().getId());
        account.setRole(role);
        accountService.saveAccount(account);

        if (role.getId() == 2) {
            Instructor instructor = new Instructor();
            instructor.setAccount(account);
            instructor.setEmail(person.getEmail());
            instructor.setFirstName(person.getFirstName());
            instructor.setLastName(person.getLastName());
            instructor.setPhoneNumber(person.getPhoneNumber());

            Instructor persistedInstructor = instructorService.saveInstructor(instructor);
            Account account1 = persistedInstructor.getAccount();
            account1.setStatus("waiting");
            Account account2 = accountService.saveAccount(account1);
//            model.addAttribute("account",account2);
        }

        if (role.getId() == 3) {
            Collegian collegian = new Collegian();

            collegian.setAccount(account);
            collegian.setEmail(person.getEmail());
            collegian.setFirstName(person.getFirstName());
            collegian.setLastName(person.getLastName());
            collegian.setPhoneNumber(person.getPhoneNumber());

            Collegian persistedCollegian = collegianService.saveCollegian(collegian);
            Account account1 = persistedCollegian.getAccount();
            account1.setStatus("waiting");
            Account account2 = accountService.saveAccount(account1);
//            model.addAttribute("account",account2);
        }

        return "registrationSuccessful";
    }


    @PostMapping("/confirm")
    public String confirmAccounts() {
        // TODO: 2/24/20
        return null;
    }


    @GetMapping("/waiting/all")
    public String showAllWaitingAccounts(Model model) {

        Role roleCollegian = roleService.findRoleById(3L);
        Role roleInstructor = roleService.findRoleById(2L);
        model.addAttribute("roleCollegian", roleCollegian);
        model.addAttribute("roleInstructor", roleInstructor);
        return "allWaitingAccounts";
    }

    @PostMapping("/waiting/all")
    public String saveAllWaitingAccountsChanges(@ModelAttribute("roleCollegian") Role roleCollegian,
                                                @ModelAttribute("roleInstructor") Role roleInstructor) {

        for (Account account : roleCollegian.getAccounts()) {
            if (account.isConfirmed()) {
                account.setStatus("registered");
            } else if (!account.isConfirmed()) {
                account.setStatus("waiting");
                System.out.println("1");
            }
            accountService.saveAccount(account);
        }
        for (Account account : roleInstructor.getAccounts()) {
            if (account.isConfirmed()) {
                account.setStatus("registered");
            } else if (!account.isConfirmed()) {
                account.setStatus("waiting");
                System.out.println("2");
            }
            accountService.saveAccount(account);
        }
        return "managerDashboard";
    }


    @GetMapping("/collegian/all")
    public String showAllCollegianAccounts(Model model) {

        Role role = roleService.findRoleById(3L); // ROLE_COLLEGIAN
        model.addAttribute("role", role);
        return "allCollegianAccounts";
    }

    @PostMapping("/collegian/all")
    public String saveAllCollegianChanges(@ModelAttribute Role role) {

        for (Account account : role.getAccounts()) {
            if (account.isConfirmed()) {
                account.setStatus("registered");
            } else if (!account.isConfirmed()) {
                account.setStatus("waiting");
            }
            accountService.saveAccount(account);
        }
        return "managerDashboard";
    }

    @GetMapping("/instructor/all")
    public String showAllInstructorAccounts(Model model) {

        Role role = roleService.findRoleById(2L); // ROLE_INSTRUCTOR
        model.addAttribute("role", role);
        return "allInstructorAccounts";
    }

    @PostMapping("/instructor/all")
    public String saveAllInstructorChanges(@ModelAttribute Role role) {

        for (Account account : role.getAccounts()) {
            if (account.isConfirmed()) {
                account.setStatus("registered");
            } else if (!account.isConfirmed()) {
                account.setStatus("waiting");
            }
            accountService.saveAccount(account);
        }
        return "managerDashboard";
    }

    @GetMapping("/test")
    public String test(){
        return "test";
    }

}
