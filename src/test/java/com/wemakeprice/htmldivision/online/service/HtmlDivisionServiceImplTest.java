package com.wemakeprice.htmldivision.online.service;

import com.wemakeprice.htmldivision.online.domain.dto.HtmlDivisionInDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class HtmlDivisionServiceImplTest {
    HtmlDivisionServiceImpl htmlDivisionService = new HtmlDivisionServiceImpl();

    @Test
    void calculate() {
        System.out.println(htmlDivisionService.calculate(HtmlDivisionInDto.builder()
                .divisor(10)
                .type("tagExclusion")
                .url("https://www.naver.com")
                .build()
                )
        );
    }

    @Test
    void getHtml() {
        System.out.println(htmlDivisionService.getHtml("https://www.naver.com"));
    }

    @Test
    void removeTags() {
        System.out.println(htmlDivisionService.removeTags(htmlDivisionService.getHtml("https://www.naver.com")));
    }

    @Test
    void OnlyEnglishFilter() {
        System.out.println(htmlDivisionService.OnlyEnglishFilter(htmlDivisionService.getHtml("https://www.naver.com")));
    }

    @Test
    void OnlyNumberFilter() {
        System.out.println(htmlDivisionService.OnlyNumberFilter(htmlDivisionService.getHtml("https://www.naver.com")));
    }

    @Test
    void sortEnglish() {
        System.out.println(htmlDivisionService.sortEnglish(htmlDivisionService.OnlyEnglishFilter(htmlDivisionService.getHtml("https://www.naver.com"))));
    }

    @Test
    void sortNumber() {
        System.out.println(htmlDivisionService.sortNumber(htmlDivisionService.OnlyNumberFilter(htmlDivisionService.getHtml("https://www.naver.com"))));
    }

    @Test
    void mixList() {
        System.out.println(htmlDivisionService.mixList(Arrays.asList(new String[]{"A","a","B","b","C","c"}),Arrays.asList(new String[]{"0","1","2","3","4","5"})));
    }

    @Test
    void division() {
        System.out.println(htmlDivisionService.division(10,htmlDivisionService.mixList(Arrays.asList(new String[]{"A","a","B","b","C","c"}),Arrays.asList(new String[]{"0","1","2","3","4","5"}))));
    }
}