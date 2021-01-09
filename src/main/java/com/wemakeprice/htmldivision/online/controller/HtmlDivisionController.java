package com.wemakeprice.htmldivision.online.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wemakeprice.htmldivision.online.WebLogger;
import com.wemakeprice.htmldivision.online.domain.dto.HtmlDivisionInDto;
import com.wemakeprice.htmldivision.online.domain.dto.HtmlDivisionOutDto;
import com.wemakeprice.htmldivision.online.service.HtmlDivisionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RequiredArgsConstructor
@Controller
public class HtmlDivisionController {
    private final HtmlDivisionService htmlDivisionService;
    private final WebLogger logger;

    @GetMapping("html-division")
    public String createFormTemplate() {
        return "html-division";
    }

    @ResponseBody
    @GetMapping("html-division/calculate")
    public ResponseEntity calculate(HttpServletRequest request, @Valid HtmlDivisionInDto htmlDivisionInDto, BindingResult result) throws JsonProcessingException {
        logger.setRequestURL(request.getRequestURI());
        logger.info(htmlDivisionInDto.toString());
        HtmlDivisionOutDto htmlDivisionOutDto;

        if(result.hasErrors()) {
            return ResponseEntity.badRequest().build();
        }
        /*else {
            htmlDivisionOutDto = htmlDivisionService.calculate(htmlDivisionInDto);
        }*/
        htmlDivisionOutDto = htmlDivisionService.calculate(htmlDivisionInDto);
        System.out.println(htmlDivisionOutDto.toString());
        return ResponseEntity.ok(htmlDivisionOutDto);
    }
}
