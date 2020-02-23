package com.shervin.maktabfinalproject.models;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private boolean isActive;
    private Date lastLoginDate;
    private String status;

    @ManyToOne
    private Role role;

    @OneToOne(mappedBy = "account")
    private Person person;

}
