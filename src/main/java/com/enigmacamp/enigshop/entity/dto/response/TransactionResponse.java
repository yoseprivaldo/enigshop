package com.enigmacamp.enigshop.entity.dto.response;

import com.enigmacamp.enigshop.constant.TransactionStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransactionResponse {
    private String id;
    private CustomerResponse customer;
    private Date date;
    private List<TransactionDetailResponse>  transactionDetails;
    private Long totalPayment;
    private TransactionStatus status;
}
