package com.enigmacamp.enigshop.entity;
import com.enigmacamp.enigshop.constant.ROLES;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_accounts")
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column()
    private ROLES role;

    @OneToOne(mappedBy =  "userAccount", cascade = CascadeType.ALL, optional = false)
    @ToString.Exclude
    private Customer customer;

    public UserAccount (String username, String password){
        this.role = ROLES.ROLES_CUSTOMER;
        this.username = username;
        this.password = password;
    }


}
