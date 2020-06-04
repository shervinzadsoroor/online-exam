package com.shervin.maktabfinalproject.crudrepositories.collegianrepository;

import com.shervin.maktabfinalproject.models.Collegian;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CollegianService {
    private final CollegianRepository collegianRepository;

    @Autowired
    public CollegianService(CollegianRepository collegianRepository) {
        this.collegianRepository = collegianRepository;
    }

    public Collegian saveCollegian(Collegian collegian) {
        return collegianRepository.save(collegian);
    }

    public List<Collegian> finaAll() {
        return collegianRepository.findAllByOrderByIdDesc();
    }

    public Collegian findById(Long id) {
        return collegianRepository.findById(id).get();
    }

    public Collegian findCollegianByAccountUsername(String username) {
        return collegianRepository.findByAccount_Username(username);
    }
}
