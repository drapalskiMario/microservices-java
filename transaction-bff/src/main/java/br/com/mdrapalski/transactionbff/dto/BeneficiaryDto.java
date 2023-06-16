package br.com.mdrapalski.transactionbff.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Getter
@Setter
@ToString
public class BeneficiaryDto implements Serializable {

    private static final long serialVersionUID = 2806421543985360625L;

    @Schema(description = "CPF")
    @NotNull(message = "CPF is required")
    private Long CPF;

    @Schema(description = "Bank code")
    @NotNull(message = "Bank code is required")
    private Long bankCode;

    @Schema(description = "Bank branch")
    @NotNull(message = "Bank branch is required")
    private String bankBranch;

    @Schema(description = "Account")
    @NotNull(message = "Account is required")
    private String account;

    @Schema(description = "Name")
    @NotNull(message = "Name is required")
    private String name;
}