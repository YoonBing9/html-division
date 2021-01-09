package com.wemakeprice.htmldivision.online.domain.dto;

import com.wemakeprice.htmldivision.AppConfig;
import com.wemakeprice.htmldivision.online.controller.HtmlDivisionController;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Locale;

class HtmlDivisionInDtoVaildationCheckTest {

    ApplicationContext applicationContext = new AnnotationConfigApplicationContext(AppConfig.class);
    MessageSource messageSource = applicationContext.getBean("messageSource", MessageSource.class);

    @Autowired
    HtmlDivisionController htmlDivisionController;

    @Test
    @DisplayName("입력값은 Null, 공백, 빈문자열을 허용하지 않는다.")
    public void inputDtoNullCheck() {
        HtmlDivisionInDto htmlDivisionInDto = new HtmlDivisionInDto(null,null,0);



        //System.out.println(messageSource.getMessage("ERRORMSG.VAILDATION.URL", new String[]{"Leica"}, Locale.getDefault()));
    }
}