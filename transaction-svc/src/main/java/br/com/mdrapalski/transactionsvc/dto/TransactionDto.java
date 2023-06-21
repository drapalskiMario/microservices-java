package br.com.mdrapalski.transactionsvc.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class TransactionDto {

    private UUID id;

    @NotNull(message = "Value is required")
    private BigDecimal value;

    @JsonFormat(pattern = "yyyy/MM/dd'T'HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime date;

    @NotNull(message = "Account is required")
    @Valid
    private AccountDto accountDto;

    @NotNull(message = "Beneficiary is required")
    @Valid
    private BeneficiaryDto beneficiaryDto;

    @NotNull(message = "Type is required")
    private TransactionType type;

    private StatusType status;
}
