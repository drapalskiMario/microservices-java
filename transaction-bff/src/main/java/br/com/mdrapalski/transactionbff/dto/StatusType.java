package br.com.mdrapalski.transactionbff.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema
public enum StatusType {
    ANALYZED,
    UNANALYZED,
    UNDER_HUMAN_ANALYSIS,
    SUSPECTED_FRAUD,
    CONFIRMED_FRAUD,
    APPROVED;
}
