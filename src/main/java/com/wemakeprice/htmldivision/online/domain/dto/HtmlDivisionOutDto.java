package com.wemakeprice.htmldivision.online.domain.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
@ToString
@Getter
public class HtmlDivisionOutDto {
    private String quotient;
    private String remainder;

    @Builder
    public HtmlDivisionOutDto(String quotient, String remainder) {
        this.quotient = quotient;
        this.remainder = remainder;
    }
}
