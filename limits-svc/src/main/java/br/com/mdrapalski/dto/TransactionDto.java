package br.com.mdrapalski.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class TransactionDto {

    private UUID id;

    private BigDecimal value;

    @JsonFormat(pattern = "yyyy/MM/dd'T'HH:mm:ss")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime date;

    private AccountDto accountDto;

    private BeneficiaryDto beneficiaryDto;

    private TransactionType type;

    private StatusType status;

    @Override
    public String toString() {
        return "TransactionDto{" +
                "id=" + id +
                ", status=" + status +
                '}';
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
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
