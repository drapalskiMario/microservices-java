package br.com.mdrapalski.transactionsvc.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Document(collection = "transactions")
public class TransactionDto {

    @Id
    private String id;

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

    @Override
    public String toString() {
        return "TransactionDto{" +
                "id=" + id +
                ", status=" + status +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public AccountDto getAccountDto() {
        return accountDto;
    }

    public void setAccountDto(AccountDto accountDto) {
        this.accountDto = accountDto;
    }

    public BeneficiaryDto getBeneficiaryDto() {
        return beneficiaryDto;
    }

    public void setBeneficiaryDto(BeneficiaryDto beneficiaryDto) {
        this.beneficiaryDto = beneficiaryDto;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public StatusType getStatus() {
        return status;
    }

    public void setStatus(StatusType status) {
        this.status = status;
    }
}
