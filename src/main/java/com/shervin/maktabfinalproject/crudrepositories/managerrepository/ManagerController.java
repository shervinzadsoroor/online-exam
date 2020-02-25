package com.shervin.maktabfinalproject.crudrepositories.managerrepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/manager")
@Controller
public class ManagerController {
    @Autowired
    private ManagerService managerService;


    @GetMapping("/home")
    public String showWelcomePage() {
        return "managerWelcomePage";
    }


    @GetMapping("/dashboard")
    public String showDashboard() {
        return "managerDashboard";
    }

    @GetMapping("/setting")
    public String showSettingPage() {
        // TODO: 2/24/20
        return null;
    }

}
