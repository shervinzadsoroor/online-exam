package com.shervin.maktabfinalproject.crudrepositories.instructorrepository;

import com.shervin.maktabfinalproject.models.Instructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class InstructorService  {
    @Autowired
    private InstructorRepository instructorRepository;

//    public InstructorService(InstructorRepository instructorRepository) {
//        this.instructorRepository = instructorRepository;
//    }

    public Instructor saveInstructor(Instructor instructor){
        return instructorRepository.save(instructor);
    }
}
