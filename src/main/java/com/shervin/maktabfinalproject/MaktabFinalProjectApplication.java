package com.shervin.maktabfinalproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
//@EnableJpaRepositories(basePackageClasses = AccountRepository.class)
public class MaktabFinalProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(MaktabFinalProjectApplication.class, args);
    }

}
