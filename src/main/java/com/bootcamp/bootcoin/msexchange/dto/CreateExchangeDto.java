package com.bootcamp.bootcoin.msexchange.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateExchangeDto {

    @Schema(description = "Input currency", example = "PEN")
    private String inputCurrency;
    @Schema(description = "Output currency", example = "BOOTCOIN")
    private String outputCurrency;
    @Schema(description = "Exchange rate", example = "0.35")
    private BigDecimal exchange;

    @Schema(description = "Insertion user agent", example = "LIM3J9")
    private String fk_insertionUser;
    @Schema(description = "Insertion terminal or platform", example = "EDGE-WEB")
    private String insertionTerminal;

}