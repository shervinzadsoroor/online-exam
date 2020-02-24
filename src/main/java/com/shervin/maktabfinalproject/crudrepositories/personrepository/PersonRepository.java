package com.shervin.maktabfinalproject.crudrepositories.personrepository;

import com.shervin.maktabfinalproject.models.Account;
import com.shervin.maktabfinalproject.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    List<Person> findByAccount(Account account);

    //Person findByFirstNameAndPhoneNumberAndLastNameAndEmail(String firstName, Long phoneNumber, String lastName, String email);
}
