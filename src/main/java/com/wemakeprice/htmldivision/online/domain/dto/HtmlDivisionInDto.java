package com.wemakeprice.htmldivision.online.domain.dto;

import lombok.*;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@ToString
@Getter
public class HtmlDivisionInDto {
    @URL
    @NotBlank
    private String url;

    @NotBlank
    private String type;

    @Positive
    @NotBlank
    private String divisor;

    @Builder
    public HtmlDivisionInDto(String url, String type, String divisor) {
        this.url = url;
        this.type = type;
        this.divisor = divisor;
    }
}
