package br.com.mdrapalski.dto;


import java.io.Serializable;

public class BeneficiaryDto implements Serializable {

    private static final long serialVersionUID = 2806421543985360625L;

    private Long CPF;

    private Long bankCode;

    private String bankBranch;

    private String account;

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