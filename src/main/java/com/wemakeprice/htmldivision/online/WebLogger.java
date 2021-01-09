package com.wemakeprice.htmldivision.online;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.UUID;

@Scope(value = "request", proxyMode = ScopedProxyMode.TARGET_CLASS)
@Component
public class WebLogger {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private String uuid;
    private String requestURL;

    public void setRequestURL(String requestURL) {
        this.requestURL = requestURL;
    }

    public void info(String message) {
        logger.info("[" + uuid + "]" + "[" + requestURL + "] " + message);
    }

    @PostConstruct
    public void init() {
        uuid = UUID.randomUUID().toString();
        logger.info("[" + uuid + "]" + "[" + requestURL + "] " + "request start.");
    }

    @PreDestroy
    public void close() {
        System.out.println("[" + uuid + "]" + "request end.");
    }
}
