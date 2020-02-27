package com.shervin.maktabfinalproject.crudrepositories.personrepository;

import com.shervin.maktabfinalproject.crudrepositories.accountrepository.AccountService;
import com.shervin.maktabfinalproject.crudrepositories.rolerepository.RoleService;
import com.shervin.maktabfinalproject.models.Account;
import com.shervin.maktabfinalproject.models.Person;
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

@RequestMapping("/person")
@Controller
public class PersonController {
    @Autowired
    private PersonService personService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private AccountService accountService;

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
        if (account.getRole().getId() == 1) {
            accountList.addAll(accountService.findAllAccountsByRoleId(2L));
            accountList.addAll(accountService.findAllAccountsByRoleId(3L));
        }
        if (account.getRole().getId() == 2) {
            accountList.addAll(accountService.findAllAccountsByRoleId(2L));
        }
        if (account.getRole().getId() == 3) {
            accountList.addAll(accountService.findAllAccountsByRoleId(3L));
        }
        model.addAttribute("accounts", accountList);
        return "searchResult";
    }

}
