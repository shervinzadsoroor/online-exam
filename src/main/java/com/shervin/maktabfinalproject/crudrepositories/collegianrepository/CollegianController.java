package com.shervin.maktabfinalproject.crudrepositories.collegianrepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/collegian")
@Controller
public class CollegianController {
    @Autowired
    private CollegianService collegianService;


}
