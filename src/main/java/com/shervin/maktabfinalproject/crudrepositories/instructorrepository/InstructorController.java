package com.shervin.maktabfinalproject.crudrepositories.instructorrepository;

import com.shervin.maktabfinalproject.crudrepositories.accountrepository.AccountService;
import com.shervin.maktabfinalproject.models.Account;
import com.shervin.maktabfinalproject.models.Collegian;
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
    public String showInstructorDashboard(@PathVariable("userName") String username, Model model) {

        //if instructor account's status is not registered redirect to registrationForm.html
        //else if status is waiting redirect to registrationWaitingDetails.html
        //and else if status is registered show the dashboard to the instructor
        Account account = accountService.findAccountByUsername(username);
        if (account.getStatus().equalsIgnoreCase("not registered")){

            Instructor instructor = new Instructor();
            instructor.setAccount(account);

            model.addAttribute("instructor", instructor);

            return "registrationFormForInstructor";

        }else if (account.getStatus().equalsIgnoreCase("waiting")){
            model.addAttribute("account", account);
            return "registrationWaitingDetails";

        }else if (account.getStatus().equalsIgnoreCase("registered")){

        }
        return "instructorDashboard";
    }

    @PostMapping("/register")
    public String registerCollegian(@ModelAttribute Instructor instructor){
        instructor.getAccount().setStatus("waiting");
        instructorService.saveInstructor(instructor);
        return "registrationSuccessful";
    }


//    @GetMapping("/form")
//    public String sendInstructorForm(Model model) {
//        model.addAttribute("instructor", new Instructor());
//        return "instructorForm";
//    }
//
//    @PostMapping("/save")
//    public String saveInstructor(@ModelAttribute Instructor instructor) {
//        instructorService.saveInstructor(instructor);
//        return "instructorSaved";
//    }
}
