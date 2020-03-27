package com.shervin.maktabfinalproject.crudrepositories.instructorrepository;

import com.shervin.maktabfinalproject.models.Collegian;
import com.shervin.maktabfinalproject.models.Instructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InstructorRepository extends JpaRepository<Instructor,Long> {
    List<Instructor> findAllByOrderByIdDesc();
}
