package com.wemakeprice.htmldivision.online.service;

import com.wemakeprice.htmldivision.online.domain.dto.HtmlDivisionInDto;
import com.wemakeprice.htmldivision.online.domain.dto.HtmlDivisionOutDto;
import org.assertj.core.api.Assertions;
import org.jsoup.Jsoup;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.DisplayName;
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
    @DisplayName("URL로 HTML 가져오기")
    void getHtml() {
        Assertions.assertThat(htmlDivisionService.getHtml(testUrl)).contains("html");
    }

    @Test
    @DisplayName("문자열에서 HTML 요소만 삭제")
    void removeTags() {
        String html = htmlDivisionService.getHtml(testUrl);

        String removedTagStr = htmlDivisionService.removeTags(html);

        Assertions.assertThat(removedTagStr).doesNotContain("</");
    }

    @Test
    @DisplayName("영어만 필터링")
    void OnlyEnglishFilter() {
        String html = htmlDivisionService.getHtml(testUrl);

        String onlyEng = htmlDivisionService.OnlyEnglishFilter(html);

        Assertions.assertThat(Pattern.matches(onlyEnglishPattern.toString(), onlyEng)).isTrue();
    }

    @Test
    @DisplayName("숫자만 필터링")
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
    @DisplayName("오름차순으로 정렬된 숫자는 무조건 앞의 요소보다 크거나 같다.")
    void sortNumber() {
        boolean isSorted = true;
        String html = htmlDivisionService.getHtml(testUrl);
        String onlyNum = htmlDivisionService.OnlyNumberFilter(html);

        List<String> list = htmlDivisionService.sortNumber(onlyNum);

        for(int i=1; i<list.size(); i++) {
            if(list.get(i).compareTo(list.get(i-1)) < 0) {
                isSorted = false;
            }
        }

        Assertions.assertThat(isSorted).isTrue();
    }

    @Test
    @DisplayName("두 리스트의 길이의 합은 섞은 리스트의 길이와 같다.")
    void mixList() {
        List list1 = new ArrayList(Arrays.asList(new String[]{"A","a","B","b","C","c"}));
        List list2 = new ArrayList(Arrays.asList(new String[]{"0","1","2","3","4","5","6","7","8"}));

        List mixedList = htmlDivisionService.mixList(list1, list2);

        Assertions.assertThat(list1.size() + list2.size()).isSameAs(mixedList.size());
    }

    @Test
    @DisplayName("요소가 0인 리스트 섞기")
    void mixListNoElement() {
        List list1 = new ArrayList(Arrays.asList(new String[]{}));
        List list2 = new ArrayList(Arrays.asList(new String[]{}));

        List mixedList = htmlDivisionService.mixList(list1, list2);

        Assertions.assertThat(list1.size() + list2.size()).isSameAs(mixedList.size());
    }

    @Test
    @DisplayName("문자열의 길이를 숫자로 나눈 나머지의 길이 체크")
    void division() {
        int divisor = 10;
        String[] temp = new String[]{"1","1","1","1","1","1","1","1","1","1","1","1","1","1","1"};

        HtmlDivisionOutDto htmlDivisionOutDto = htmlDivisionService.division(divisor, new ArrayList<String>(Arrays.asList(temp)));
        int mod = temp.length % divisor;

        Assertions.assertThat(mod).isSameAs(htmlDivisionOutDto.getRemainder().length());
    }
}