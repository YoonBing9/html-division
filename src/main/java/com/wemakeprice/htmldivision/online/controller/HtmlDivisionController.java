package com.wemakeprice.htmldivision.online.controller;

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
    public ResponseEntity calculate(HttpServletRequest request, @Valid HtmlDivisionInDto htmlDivisionInDto) {
        logger.setRequestURL(request.getRequestURI());
        logger.info(htmlDivisionInDto.toString());

        HtmlDivisionOutDto htmlDivisionOutDto = htmlDivisionService.calculate(htmlDivisionInDto);

        return ResponseEntity.ok(htmlDivisionOutDto);
    }
}
