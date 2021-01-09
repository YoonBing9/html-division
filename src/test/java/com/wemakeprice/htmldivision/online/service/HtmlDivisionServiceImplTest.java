package com.wemakeprice.htmldivision.online.service;

import com.wemakeprice.htmldivision.online.domain.dto.HtmlDivisionInDto;
import org.assertj.core.api.Assertions;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class HtmlDivisionServiceImplTest {
    HtmlDivisionServiceImpl htmlDivisionService = new HtmlDivisionServiceImpl();
    private static final String testUrl = "https://www.naver.com";
    private static final Pattern onlyEnglishPattern = Pattern.compile("^[a-zA-Z0-9]*$");
    private static final Pattern onlyNumberPattern = Pattern.compile("^[0-9]*$");

    @Test
    void calculate() {
        System.out.println(htmlDivisionService.calculate(HtmlDivisionInDto.builder()
                .divisor("10")
                .type("tagExclusion")
                .url(testUrl)
                .build()
                )
        );
    }

    @Test
    void getHtml() {
        Assertions.assertThat(htmlDivisionService.getHtml(testUrl)).contains("html");
    }

    @Test
    void removeTags() {
        String html = htmlDivisionService.getHtml(testUrl);

        String removedTagStr = htmlDivisionService.removeTags(html);

        Assertions.assertThat(removedTagStr).doesNotContain("</");
    }

    @Test
    void OnlyEnglishFilter() {
        String html = htmlDivisionService.getHtml(testUrl);

        String onlyEng = htmlDivisionService.OnlyEnglishFilter(html);

        Assertions.assertThat(Pattern.matches(onlyEnglishPattern.toString(), onlyEng)).isTrue();
    }

    @Test
    void OnlyNumberFilter() {
        String html = htmlDivisionService.getHtml(testUrl);

        String onlyNum = htmlDivisionService.OnlyNumberFilter(html);

        Assertions.assertThat(Pattern.matches(onlyNumberPattern.toString(), onlyNum)).isTrue();
    }

    @Test
    void sortEnglish() {
        System.out.println(htmlDivisionService.sortEnglish(htmlDivisionService.OnlyEnglishFilter(htmlDivisionService.getHtml("https://www.naver.com"))));
    }

    @Test
    void sortNumber() {
        String html = htmlDivisionService.getHtml(testUrl);
        String onlyNum = htmlDivisionService.OnlyNumberFilter(html);

        List list = htmlDivisionService.sortNumber(onlyNum);


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