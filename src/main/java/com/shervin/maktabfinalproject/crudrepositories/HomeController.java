package com.shervin.maktabfinalproject.crudrepositories;

import com.shervin.maktabfinalproject.models.Collegian;
import com.shervin.maktabfinalproject.models.Instructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/register")
@Controller
public class HomeController {


    @GetMapping("/form")
    public String sendForm(Model model) {
        model.addAttribute("instructor", new Instructor());
        model.addAttribute("collegian", new Collegian());
        return "registrationForm";
    }
}
