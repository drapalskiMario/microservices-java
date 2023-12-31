package br.com.mdrapalski.transactionsvc.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

public class RequestTransactionDto extends TransactionDto {

    @JsonIgnore
    private StatusType requestStatus;

    @JsonIgnore
    private LocalDateTime requestDate;
}
