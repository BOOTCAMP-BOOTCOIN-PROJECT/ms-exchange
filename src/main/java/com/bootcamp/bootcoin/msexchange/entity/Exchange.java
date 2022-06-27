package com.bootcamp.bootcoin.msexchange.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document
public class Exchange implements Serializable {

    private static final long serialVersionUID = 7156526077883281623L;

    @Id
    private String id;

    @Schema(description = "Input currency", example = "PEN")
    private String inputCurrency;
    @Schema(description = "Output currency", example = "BOOTCOIN")
    private String outputCurrency;
    @Schema(description = "Exchange rate", example = "0.31")
    private BigDecimal exchange;
    @Schema(description = "Exchange tag [INPUTCURRENCY.OUTPUTCURRENCY]", example = "PEN.BOOTCOIN")
    private String tag;

    @Schema(description = "Emision Date (automatic)", example = "2022-05-05T16:56:26.670Z")
    private Date emisionDate;
    @Schema(description = "Insertion user agent", example = "LIM3J9")
    private String fk_insertionUser;
    @Schema(description = "Insertion terminal or platform", example = "EDGE-WEB")
    private String insertionTerminal;

}
