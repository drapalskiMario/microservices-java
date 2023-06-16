package br.com.mdrapalski.transactionbff.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public enum TransactionType {
    PAYMENT_OF_TAXES,
    PAYMENT_OF_BANK_SECURITIES,
    TED,
    DOC;
}
