package br.com.mdrapalski.transactionbff.dto;

import java.math.BigDecimal;

public class DailyLimitDto {

    private Long id;
    private Long bankBranch;
    private Long account;
    private BigDecimal value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getBankBranch() {
        return bankBranch;
    }

    public void setBankBranch(Long bankBranch) {
        this.bankBranch = bankBranch;
    }

    public Long getAccount() {
        return account;
    }

    public void setAccount(Long account) {
        this.account = account;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }
}
