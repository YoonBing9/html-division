package com.wemakeprice.htmldivision.online.service;

import com.wemakeprice.htmldivision.online.WebLogger;
import com.wemakeprice.htmldivision.online.domain.dto.HtmlDivisionInDto;
import com.wemakeprice.htmldivision.online.domain.dto.HtmlDivisionOutDto;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*String getHtml(String url);
    String removeTags(String contents);
    String filter(String contents);
    List sortEnglish(String contents);
    List sortNumber(String contents);
    List mixList(List list1, List list2);
    void division(int divisor, List list);*/

@Service
public class HtmlDivisionServiceImpl implements HtmlDivisionService{
    private HtmlDivisionInDto inDto;
    private static final Pattern onlyEnglishPattern = Pattern.compile("[a-zA-Z]");
    private static final Pattern onlyNumberPattern = Pattern.compile("[0-9]");

    @Override
    public HtmlDivisionOutDto calculate(HtmlDivisionInDto htmlDivisionInDto) {
        inDto = htmlDivisionInDto;
        String contents = "";
        contents = getHtml(inDto.getUrl());
        if(htmlDivisionInDto.getType().equals("tagExclusion")) {
            contents = removeTags(contents);
        }
        List engList = sortEnglish(OnlyEnglishFilter(contents));
        List numList = sortNumber(OnlyNumberFilter(contents));
        return division(Integer.valueOf(htmlDivisionInDto.getDivisor()), mixList(engList, numList));
    }

    /*
    * HTML 문자열 가져오기
    * */
    public String getHtml(String urlPath) {
        String pageContents = "";
        StringBuilder contents = new StringBuilder();

        try{
            URL url = new URL(urlPath);
            URLConnection con = (URLConnection)url.openConnection();
            InputStreamReader reader = new InputStreamReader (con.getInputStream(), "utf-8");

            BufferedReader buff = new BufferedReader(reader);

            while((pageContents = buff.readLine())!=null){
                contents.append(pageContents);
            }

            buff.close();
        }catch(Exception e){
            e.printStackTrace();
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
    * */
    public List sortEnglish(String contents) {
        List list = new ArrayList(Arrays.asList(contents.split("")));
        Collections.sort(list, new Comparator<String>() {

            @Override
            public int compare(String str1, String str2) {
                int val = str1.toUpperCase().compareTo(str2.toUpperCase());

                if(val == 0) {
                    val = str1.compareTo(str2);
                }
                return val;
            }
        });

        return list;
    }

    public List sortNumber(String contents) {
        List list = new ArrayList(Arrays.asList(contents.split("")));
        Collections.sort(list);
        return list;
    }

    public List mixList(List engList, List numList) {
        List mixedList = new ArrayList();
        int index = 0;

        while (index < engList.size() && index < numList.size()) {
            mixedList.add(engList.get(index));
            mixedList.add(numList.get(index));
            index++;
        }

        if(index < engList.size()) {
            mixedList.addAll(engList.subList(index, engList.size()));
        }
        if(index < numList.size()) {
            mixedList.addAll(numList.subList(index, numList.size()));
        }

        return mixedList;
    }

    public HtmlDivisionOutDto division(int divisor, List list) {
        String contents = String.join("",list);
        int remainderNum = contents.length()%divisor;
        String quotient = contents.substring(0, contents.length()-remainderNum);
        String remainder = contents.substring(contents.length()-remainderNum);

        return HtmlDivisionOutDto.builder()
                .quotient(quotient)
                .remainder(remainder)
                .build();
    }
}
