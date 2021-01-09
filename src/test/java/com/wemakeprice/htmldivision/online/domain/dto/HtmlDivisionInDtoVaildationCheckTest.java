package com.wemakeprice.htmldivision.online.domain.dto;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Collection;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HtmlDivisionInDtoVaildationCheckTest {
    final String testUrl = "https://www.naver.com";
    final Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

    @Test
    @DisplayName("입력값 NOT NULL 체크")
    void notNull() {
        HtmlDivisionInDto htmlDivisionInDto = new HtmlDivisionInDto(null, null, "1");

        final Collection<ConstraintViolation<HtmlDivisionInDto>> constraintViolations = validator.validate(htmlDivisionInDto);

        assertEquals(2, constraintViolations.size());
        assertEquals("공백일 수 없습니다", constraintViolations.iterator().next().getMessage());

        for (ConstraintViolation<HtmlDivisionInDto> constraintViolation : constraintViolations) {
            System.out.println(constraintViolation.getMessage());
        }
    }

    @Test
    @DisplayName("입력값 빈문자열 체크, ")
    void notBlank() {
        HtmlDivisionInDto htmlDivisionInDto = new HtmlDivisionInDto("", "", "1");

        final Collection<ConstraintViolation<HtmlDivisionInDto>> constraintViolations = validator.validate(htmlDivisionInDto);

        assertEquals(2, constraintViolations.size());
        assertEquals("공백일 수 없습니다", constraintViolations.iterator().next().getMessage());

        for (ConstraintViolation<HtmlDivisionInDto> constraintViolation : constraintViolations) {
            System.out.println(constraintViolation.getMessage());
        }
    }

    @Test
    @DisplayName("입력값 공백 문자열 체크, ")
    void notSpace() {
        HtmlDivisionInDto htmlDivisionInDto = new HtmlDivisionInDto(testUrl, "   ", "1");

        final Collection<ConstraintViolation<HtmlDivisionInDto>> constraintViolations = validator.validate(htmlDivisionInDto);

        assertEquals(1, constraintViolations.size());
        assertEquals("공백일 수 없습니다", constraintViolations.iterator().next().getMessage());

        for (ConstraintViolation<HtmlDivisionInDto> constraintViolation : constraintViolations) {
            System.out.println(constraintViolation.getMessage());
        }
    }

    @Test
    @DisplayName("입력값 divisior 음수 체크, ")
    void notMinus() {
        HtmlDivisionInDto htmlDivisionInDto = new HtmlDivisionInDto(testUrl, "test", "-1");

        final Collection<ConstraintViolation<HtmlDivisionInDto>> constraintViolations = validator.validate(htmlDivisionInDto);

        assertEquals(1, constraintViolations.size());
        assertEquals("0보다 커야 합니다", constraintViolations.iterator().next().getMessage());

        for (ConstraintViolation<HtmlDivisionInDto> constraintViolation : constraintViolations) {
            System.out.println(constraintViolation.getMessage());
        }
    }

    @Test
    @DisplayName("입력값 divisior 0 체크, ")
    void notZero() {
        HtmlDivisionInDto htmlDivisionInDto = new HtmlDivisionInDto(testUrl, "test", "0");

        final Collection<ConstraintViolation<HtmlDivisionInDto>> constraintViolations = validator.validate(htmlDivisionInDto);

        assertEquals(1, constraintViolations.size());
        assertEquals("0보다 커야 합니다", constraintViolations.iterator().next().getMessage());

        for (ConstraintViolation<HtmlDivisionInDto> constraintViolation : constraintViolations) {
            System.out.println(constraintViolation.getMessage());
        }
    }

    @Test
    @DisplayName("입력값 URL 형식 체크, ")
    void urlFormCheck() {
        HtmlDivisionInDto htmlDivisionInDto = new HtmlDivisionInDto("abc.asd", "test", "1");

        final Collection<ConstraintViolation<HtmlDivisionInDto>> constraintViolations = validator.validate(htmlDivisionInDto);

        assertEquals(1, constraintViolations.size());
        assertEquals("올바른 URL이어야 합니다", constraintViolations.iterator().next().getMessage());

        for (ConstraintViolation<HtmlDivisionInDto> constraintViolation : constraintViolations) {
            System.out.println(constraintViolation.getMessage());
        }
    }
}