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
    private String errorMsg;

    @Builder
    public HtmlDivisionOutDto(String quotient, String remainder, String errorMsg) {
        this.quotient = quotient;
        this.remainder = remainder;
        this.errorMsg = errorMsg;
    }
}
