package com.bootcamp.bootcoin.msexchange.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
public class GetExchangeDto {
    @Schema(description = "Input currency", example = "PEN")
    private String inputCurrency;
    @Schema(description = "Output currency", example = "BOOTCOIN")
    private String outputCurrency;
}
