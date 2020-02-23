package com.shervin.maktabfinalproject.crudrepositories.instructorrepository;

import com.shervin.maktabfinalproject.models.Instructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/instructor")
@Controller
public class InstructorController {
    @Autowired
    private InstructorService instructorService;

//    public InstructorController(InstructorService instructorService) {
//        this.instructorService = instructorService;
//    }

    @GetMapping("/form")
    public String sendInstructorForm(Model model){
        model.addAttribute("instructor",new Instructor());
        return "instructorForm";
    }

    @PostMapping("/save")
    public String saveInstructor(@ModelAttribute Instructor instructor){
        instructorService.saveInstructor(instructor);
        return "instructorSaved";
    }
}
