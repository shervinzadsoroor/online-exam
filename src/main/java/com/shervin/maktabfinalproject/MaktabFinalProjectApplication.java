package com.shervin.maktabfinalproject;

import com.shervin.maktabfinalproject.crudrepositories.accountrepository.AccountRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories(basePackageClasses = AccountRepository.class)
public class MaktabFinalProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(MaktabFinalProjectApplication.class, args);
    }

}
