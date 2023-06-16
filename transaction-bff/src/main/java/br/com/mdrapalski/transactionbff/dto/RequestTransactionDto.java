package br.com.mdrapalski.transactionbff.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@Schema
public class RequestTransactionDto extends TransactionDto {
    @JsonIgnore
    private StatusType status;
    @JsonIgnore
    private LocalDateTime date;
}
