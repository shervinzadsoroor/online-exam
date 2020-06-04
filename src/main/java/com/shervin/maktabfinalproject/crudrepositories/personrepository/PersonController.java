package com.shervin.maktabfinalproject.crudrepositories.personrepository;

import com.shervin.maktabfinalproject.crudrepositories.accountrepository.AccountService;
import com.shervin.maktabfinalproject.crudrepositories.rolerepository.RoleService;
import com.shervin.maktabfinalproject.models.Account;
import com.shervin.maktabfinalproject.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/person")
@Controller
public class PersonController {
    private final PersonService personService;
    private final RoleService roleService;
    private final AccountService accountService;

    @Autowired
    public PersonController(PersonService personService, RoleService roleService, AccountService accountService) {
        this.personService = personService;
        this.roleService = roleService;
        this.accountService = accountService;
    }

    @GetMapping("/search")
    public String sendSearchForm(Model model) {
        List<Role> roles = new ArrayList<>();
        roles.add(roleService.findRoleById(2L)); //retrieves ROLE_INSTRUCTOR
        roles.add(roleService.findRoleById(3L)); //retrieves ROLE_COLLEGIAN
        model.addAttribute("roles", roles);
        model.addAttribute("account", new Account());
        return "searchByManager";
    }

    @PostMapping("/search")
    public String searchAccounts(@ModelAttribute Account account, Model model) {
        List<Account> accountList = new ArrayList<>();

        //retrieves all accounts
        if (account.getRole().getId() == 1) {
            accountList.addAll(accountService.findAllAccountsByRoleId(2L));
            accountList.addAll(accountService.findAllAccountsByRoleId(3L));
        }
        //retrieves instructor accounts
        if (account.getRole().getId() == 2) {
            accountList.addAll(accountService.findAllAccountsByRoleId(2L));
        }

        //retrieves collegian accounts
        if (account.getRole().getId() == 3) {
            accountList.addAll(accountService.findAllAccountsByRoleId(3L));
        }

        if (account.getPerson().getFirstName().isBlank() && account.getPerson().getLastName().isBlank()) {
            model.addAttribute("accounts", accountList);
        }

        if (!account.getPerson().getFirstName().isBlank() && account.getPerson().getLastName().isBlank()) {
            List<Account> filteredAccounts = accountList.stream().filter(account1 -> account1.getPerson().getFirstName()
                    .equals(account.getPerson().getFirstName())).collect(Collectors.toList());
            model.addAttribute("accounts", filteredAccounts);
        }

        if (!account.getPerson().getLastName().isBlank() && account.getPerson().getFirstName().isBlank()) {
            List<Account> filteredAccounts = accountList.stream().filter(account1 -> account1.getPerson().getLastName()
                    .equals(account.getPerson().getLastName())).collect(Collectors.toList());
            model.addAttribute("accounts", filteredAccounts);
        }

        if (!account.getPerson().getLastName().isBlank() && !account.getPerson().getFirstName().isBlank()) {
            List<Account> filteredAccounts = accountList.stream().filter(account1 -> account1.getPerson().getLastName()
                    .equals(account.getPerson().getLastName()))
                    .filter(account1 -> account1.getPerson().getFirstName()
                            .equals(account.getPerson().getFirstName()))
                    .collect(Collectors.toList());

            model.addAttribute("accounts", filteredAccounts);
        }
        return "searchResult";
    }

}
