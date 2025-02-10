package com.enigmacamp.enigshop.entity;

import com.enigmacamp.enigshop.constant.ConstantTable;
import com.enigmacamp.enigshop.constant.UserRole;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Setter
@Entity
@Table(name = ConstantTable.ROLE)
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "name", nullable = false)
    @Enumerated(EnumType.STRING)
    private UserRole name;

}
