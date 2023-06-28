package br.com.mdrapalski.transactionbff.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class AccountDto implements Serializable {

    private static final long serialVersionUID = 2806412403585360625L;

    @Schema(description = "Bank branch code")
    @NotNull(message = "Bank branch code is required")
    private Long bankBranchCode;

    @Schema(description = "Account code")
    @NotNull(message = "Account code is required")
    private Long accountCode;

    @Override
    public String toString() {
        return "AccountDto{" +
                "bankBranchCode=" + bankBranchCode +
                ", accountCode=" + accountCode +
                '}';
    }


    public Long getBankBranchCode() {
        return bankBranchCode;
    }

    public void setBankBranchCode(Long bankBranchCode) {
        this.bankBranchCode = bankBranchCode;
    }

    public Long getAccountCode() {
        return accountCode;
    }

    public void setAccountCode(Long accountCode) {
        this.accountCode = accountCode;
    }
}
