package br.com.mdrapalski.transactionsvc.dto;

import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

public class BeneficiaryDto implements Serializable {

    private static final long serialVersionUID = 2806421543985360625L;

    @NotNull(message = "CPF is required")
    private Long CPF;

    @NotNull(message = "Bank code is required")
    private Long bankCode;

    @NotNull(message = "Bank branch is required")
    private String bankBranch;

    @NotNull(message = "Account is required")
    private String account;

    @NotNull(message = "Name is required")
    private String name;

    public Long getCPF() {
        return CPF;
    }

    public void setCPF(Long CPF) {
        this.CPF = CPF;
    }

    public Long getBankCode() {
        return bankCode;
    }

    public void setBankCode(Long bankCode) {
        this.bankCode = bankCode;
    }

    public String getBankBranch() {
        return bankBranch;
    }

    public void setBankBranch(String bankBranch) {
        this.bankBranch = bankBranch;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}