package com.enigmacamp.enigshop.entity;

import com.enigmacamp.enigshop.constant.ConstantTable;
import com.enigmacamp.enigshop.constant.TransactionStatus;
import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name= ConstantTable.PAYMENT)
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false)
    private String token;

    @Column(nullable = false, name = "redirect_url")
    private String redirectUri;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, name = "transaction_status")
    private TransactionStatus transactionStatus;

    @OneToOne(mappedBy = "payment")
    private Transaction transaction;
}
