package com.shervin.maktabfinalproject.crudrepositories.accountrepository;

import com.shervin.maktabfinalproject.models.Account;
import com.shervin.maktabfinalproject.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAllById(Long id);

    List<Account> findAllByPerson(Person person);

    List<Account> findAllByUsername(String username);

    Account findByUsername(String username);

    List<Account> findAllByPassword(String password);
}
