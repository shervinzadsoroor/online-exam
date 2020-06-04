package com.shervin.maktabfinalproject.crudrepositories.instructorrepository;

import com.shervin.maktabfinalproject.crudrepositories.accountrepository.AccountService;
import com.shervin.maktabfinalproject.crudrepositories.courserepository.CourseService;
import com.shervin.maktabfinalproject.models.Account;
import com.shervin.maktabfinalproject.models.Course;
import com.shervin.maktabfinalproject.models.Instructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RequestMapping("/instructor")
@Controller
public class InstructorController {
    private final InstructorService instructorService;
    private final AccountService accountService;
    private final CourseService courseService;

    @Autowired
    public InstructorController(InstructorService instructorService, AccountService accountService, CourseService courseService) {
        this.instructorService = instructorService;
        this.accountService = accountService;
        this.courseService = courseService;
    }

//    public InstructorController(InstructorService instructorService) {
//        this.instructorService = instructorService;
//    }

    @GetMapping("/dashboard/{userName}")
    public String showInstructorDashboard(@PathVariable("userName") String username, Model model) {

        //if instructor account's status is not registered redirect to registrationForm.html
        //else if status is waiting redirect to registrationWaitingDetails.html
        //and else if status is registered show the dashboard to the instructor
        Account account = accountService.findAccountByUsername(username);
        account.setLastLoginDate(new Date());
        account = accountService.saveAccount(account);
        if (account.getStatus().equalsIgnoreCase("not registered")) {

            Instructor instructor = new Instructor();
            instructor.setAccount(account);

            model.addAttribute("instructor", instructor);

            return "registrationFormForInstructor";

        } else if (account.getStatus().equalsIgnoreCase("waiting")) {
            model.addAttribute("account", account);
            return "registrationWaitingDetails";

        } else if (account.getStatus().equalsIgnoreCase("registered")) {
            List<Course> courseList = courseService
                    .findAllCoursesOfTheInstructor(account.getPerson().getId());
            model.addAttribute("courses", courseList);
        }
        return "instructorDashboard";
    }

    @PostMapping("/register")
    public String registerCollegian(@ModelAttribute Instructor instructor) {
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
