package br.com.mdrapalski.dto;

import java.io.Serializable;

public class AccountDto implements Serializable {

    private static final long serialVersionUID = 2806412403585360625L;

    private Long bankBranchCode;

    private Long accountCode;

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
