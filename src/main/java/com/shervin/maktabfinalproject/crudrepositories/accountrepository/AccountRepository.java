package com.shervin.maktabfinalproject.crudrepositories.accountrepository;

import com.shervin.maktabfinalproject.models.Account;
import com.shervin.maktabfinalproject.models.Person;
import com.shervin.maktabfinalproject.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    List<Account> findAllById(Long id);

    List<Account> findAllByPerson(Person person);

    List<Account> findAllByUsername(String username);

    Optional<Account> findByUsername(String username);

    Optional<Account> findByUsernameAndPassword(String username, String password);

    List<Account> findAllByPassword(String password);

    List<Account> findAllByStatus(String status);

    List<Account> findAllByRole_IdOrderByIdDesc(Long roleId);
}
