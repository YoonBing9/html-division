package com.wemakeprice.htmldivision.online.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.validator.constraints.URL;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

@ToString
@Getter
public class HtmlDivisionInDto {
    @URL
    private String url;

    @NotBlank
    private String type;

    @Positive
    private int divisor;

    @Builder
    public HtmlDivisionInDto(@URL String url, @NotBlank String type, @Positive int divisor) {
        this.url = url;
        this.type = type;
        this.divisor = divisor;
    }
}
