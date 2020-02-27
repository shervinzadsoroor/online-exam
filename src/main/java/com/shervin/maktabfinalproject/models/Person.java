package com.shervin.maktabfinalproject.models;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;
    private String lastName;
    private Long phoneNumber;
    private String email;

    @OneToOne
    private Account account;

    @Override
    public String toString() {
        return "id=" + id +
                ", name='" + firstName + " " + lastName + '\'' +
                ", phoneNumber=" + phoneNumber +
                ", email='" + email + '\'' +
                ", account=" + account.getUsername() +
                '}';
    }
}