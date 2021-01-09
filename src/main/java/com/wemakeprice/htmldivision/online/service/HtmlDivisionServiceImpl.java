package com.wemakeprice.htmldivision.online.service;

import com.wemakeprice.htmldivision.online.WebLogger;
import com.wemakeprice.htmldivision.online.domain.dto.HtmlDivisionInDto;
import com.wemakeprice.htmldivision.online.domain.dto.HtmlDivisionOutDto;
import org.jsoup.Jsoup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;



@Service
public class HtmlDivisionServiceImpl implements HtmlDivisionService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private HtmlDivisionInDto inDto;
    private static final Pattern onlyEnglishPattern = Pattern.compile("[a-zA-Z]");
    private static final Pattern onlyNumberPattern = Pattern.compile("[0-9]");

    @Override
    public HtmlDivisionOutDto calculate(HtmlDivisionInDto htmlDivisionInDto) {
        inDto = htmlDivisionInDto;
        String contents = "";
        try {
            contents = getHtml(inDto.getUrl());
        }catch (UnknownHostException e) {
            return HtmlDivisionOutDto.builder()
                    .errorMsg("사이트를 찾을 수 없습니다.")
                    .build();
        }catch (Exception e) {
            return HtmlDivisionOutDto.builder()
                    .errorMsg("ERROR")
                    .build();
        }

        System.out.println("123");
        if (htmlDivisionInDto.getType().equals("tagExclusion")) {
            contents = removeTags(contents);
        }
        List engList = sortEnglish(OnlyEnglishFilter(contents));
        List numList = sortNumber(OnlyNumberFilter(contents));
        return division(Integer.valueOf(htmlDivisionInDto.getDivisor()), mixList(engList, numList));
    }

    /*
     * HTML 문자열 가져오기
     * */
    public String getHtml(String urlPath) throws UnknownHostException, Exception{
        String pageContents = "";
        StringBuilder contents = new StringBuilder();

        try {
            URL url = new URL(urlPath);
            URLConnection con = (URLConnection) url.openConnection();
            InputStreamReader reader = new InputStreamReader(con.getInputStream(), "utf-8");

            BufferedReader buff = new BufferedReader(reader);

            while ((pageContents = buff.readLine()) != null) {
                contents.append(pageContents);
            }

            buff.close();
        } catch (UnknownHostException e) {
            logger.error("Exception", e);
            throw e;
        } catch (Exception e) {
            logger.error("Exception", e);
            throw e;
        }

        return contents.toString();
    }

    /*
     * 태그 제거
     * */
    public String removeTags(String contents) {
        return Jsoup.parse(contents).text();
    }

    /*
     * 영어만 필터링
     * */
    public String OnlyEnglishFilter(String contents) {
        Matcher matcher = onlyEnglishPattern.matcher(contents);
        StringBuilder sb = new StringBuilder();

        while (matcher.find()) {
            sb.append(matcher.group());
        }
        return sb.toString();
    }

    /*
     * 숫자만 필터링
     * */
    public String OnlyNumberFilter(String contents) {
        Matcher matcher = onlyNumberPattern.matcher(contents);
        StringBuilder sb = new StringBuilder();

        while (matcher.find()) {
            sb.append(matcher.group());
        }
        return sb.toString();
    }

    /*
     * 영어 정렬 AaBbCc..순
     * 우선순위1: 알파벳순
     * 우선순위2: 알파벳이 같다면 대문자, 소문자 순
     * */
    public List sortEnglish(String contents) {
        List list = new ArrayList(Arrays.asList(contents.split("")));
        Collections.sort(list, new Comparator<String>() {

            @Override
            public int compare(String str1, String str2) {
                int val = str1.toUpperCase().compareTo(str2.toUpperCase());

                if (val == 0) {
                    val = str1.compareTo(str2);
                }
                return val;
            }
        });

        return list;
    }

    /*
    * 숫자 오름차순 정렬*/
    public List sortNumber(String contents) {
        List list = new ArrayList(Arrays.asList(contents.split("")));
        Collections.sort(list);
        return list;
    }

    /*
    * 영어리스트, 숫자리스트 요소 하나씩 교대로 섞기*/
    public List mixList(List engList, List numList) {
        List mixedList = new ArrayList();
        int index = 0;

        while (index < engList.size() && index < numList.size()) {
            mixedList.add(engList.get(index));
            mixedList.add(numList.get(index));
            index++;
        }

        if (index < engList.size()) {
            mixedList.addAll(engList.subList(index, engList.size()));
        }
        if (index < numList.size()) {
            mixedList.addAll(numList.subList(index, numList.size()));
        }

        return mixedList;
    }

    /*
    * 처리 문자열을 divisor로 나눠 몫과 나머지 계산.
    * */
    public HtmlDivisionOutDto division(int divisor, List list) {
        String contents = String.join("", list);
        int remainderNum = contents.length() % divisor;
        String quotient = contents.substring(0, contents.length() - remainderNum);
        String remainder = contents.substring(contents.length() - remainderNum);

        return HtmlDivisionOutDto.builder()
                .quotient(quotient)
                .remainder(remainder)
                .build();
    }
}
