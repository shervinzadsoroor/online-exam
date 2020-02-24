package com.shervin.maktabfinalproject.crudrepositories.collegianrepository;

import com.shervin.maktabfinalproject.models.Collegian;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CollegianService {
    @Autowired
    private CollegianRepository collegianRepository;
    
    public Collegian saveCollegian(Collegian collegian) {
        return collegianRepository.save(collegian);
    }
}
