package com.wemakeprice.htmldivision.online.service;

import com.wemakeprice.htmldivision.online.domain.dto.HtmlDivisionInDto;
import com.wemakeprice.htmldivision.online.domain.dto.HtmlDivisionOutDto;

import java.util.List;

public interface HtmlDivisionService {
    HtmlDivisionOutDto calculate(HtmlDivisionInDto htmlDivisionInDto);
}
