package com.wemakeprice.htmldivision.online.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HtmlDivisionController {

    @GetMapping("html-division")
    public String createFormTemplate() {
        return "html-division";
    }
}
