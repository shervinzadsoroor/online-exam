package com.shervin.maktabfinalproject.crudrepositories.instructorrepository;

import com.shervin.maktabfinalproject.crudrepositories.accountrepository.AccountService;
import com.shervin.maktabfinalproject.models.Account;
import com.shervin.maktabfinalproject.models.Instructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/instructor")
@Controller
public class InstructorController {
    @Autowired
    private InstructorService instructorService;
    @Autowired
    private AccountService accountService;

//    public InstructorController(InstructorService instructorService) {
//        this.instructorService = instructorService;
//    }

    @GetMapping("/dashboard/{userName}")
    public String showCollegianDashboard(@PathVariable("userName") String username) {

        //if instructor account's status is not registered redirect to registrationForm.html
        //else if status is waiting redirect to registrationWaitingDetails.html
        //and else if status is registered show the dashboard to the instructor
        Account account = accountService.findAccountByUsername(username);
        System.out.println("username = " + username);
        return "instructorDashboard";
    }


    @GetMapping("/form")
    public String sendInstructorForm(Model model) {
        model.addAttribute("instructor", new Instructor());
        return "instructorForm";
    }

    @PostMapping("/save")
    public String saveInstructor(@ModelAttribute Instructor instructor) {
        instructorService.saveInstructor(instructor);
        return "instructorSaved";
    }
}
