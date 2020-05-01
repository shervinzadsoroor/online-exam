package com.shervin.maktabfinalproject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
/**
 * @author shervin zadsoroor
 * @version 1.0.0
 * <h2>online exam application</h2>
 */
@SpringBootApplication
@EnableScheduling
//@EnableJpaRepositories(basePackageClasses = AccountRepository.class)
public class MaktabFinalProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(MaktabFinalProjectApplication.class, args);
    }

}
