package com.shervin.maktabfinalproject.crudrepositories.personrepository;

import com.shervin.maktabfinalproject.models.Account;
import com.shervin.maktabfinalproject.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {

    private PersonRepository personRepository;

    @Autowired
    public void setPersonRepository(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public boolean isPersonHasAccount(Account account) {
        List<Person> list = personRepository.findByAccount(account);
        return list.size() > 0;
    }

    public Person savePerson(Person person) {
        return personRepository.save(person);
    }
}
