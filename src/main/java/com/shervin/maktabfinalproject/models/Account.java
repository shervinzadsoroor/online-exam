package com.shervin.maktabfinalproject.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

/**
 * @author shervin zadsoroor
 * <h2>this the Account model</h2>
 * this entity is using for working with accounts
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Account implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Size(min = 3)
    private String username;
    @Size(min = 4)
    private String password;
    private boolean isActive;
    private Date lastLoginDate;
    private String status;
    private boolean isConfirmed;

    @ManyToOne
    private Role role;

    @OneToOne(mappedBy = "account")
    private Person person;

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", isActive=" + isActive +
                ", lastLoginDate=" + lastLoginDate +
                ", status='" + status + '\'' +
                ", role=" + role.getTitle() +
                ", isConfirmed=" + isConfirmed +
//                ", person id=" + person.getId() +
                '}';
    }

}
