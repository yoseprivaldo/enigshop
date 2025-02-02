package com.enigmacamp.enigshop.entity;
import com.enigmacamp.enigshop.constant.ROLE;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;


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

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ROLE role;

    @OneToOne(mappedBy =  "userAccount", cascade = CascadeType.ALL)
    @ToString.Exclude
    @JsonIgnore
    private Customer customer;

    public UserAccount (String username, String password){
        this.role = ROLE.ROLES_CUSTOMER;
        this.username = username;
        this.password = password;
    }

    public UserAccount (String username, String password, ROLE role){
        this.role = role;
        this.username = username;
        this.password = password;
    }

}
