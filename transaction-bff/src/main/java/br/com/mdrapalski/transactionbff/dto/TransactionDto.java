package br.com.mdrapalski.transactionbff.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@EqualsAndHashCode(of = "uuid")
@RedisHash(value = "TransactionDto", timeToLive = 300)
public class TransactionDto {

    @Schema(description = "Identifier")
    @Id
    private UUID uuid;

    @Schema(description = "Value")
    @NotNull(message = "Value is required")
    private BigDecimal value;

    @Schema(description = "Date")
    @JsonFormat(pattern = "yyyy/MM/dd'T'HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime date;

    @Schema(description = "Account")
    @NotNull(message = "Account is required")
    @Valid
    private AccountDto accountDto;

    @Schema(description = "Beneficiary")
    @NotNull(message = "Beneficiary is required")
    @Valid
    private BeneficiaryDto beneficiaryDto;

    @Schema(description = "Type")
    @NotNull(message = "Type is required")
    private TransactionType type;

    @Schema(description = "Status")
    private StatusType status;
}
