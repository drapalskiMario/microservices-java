package br.com.mdrapalski.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

public class RequestTransactionDto extends TransactionDto {

    @JsonIgnore
    private StatusType status;
    @JsonIgnore
    private LocalDateTime date;
}
