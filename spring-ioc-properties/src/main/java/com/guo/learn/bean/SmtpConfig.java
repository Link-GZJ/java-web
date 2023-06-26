package com.guo.learn.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//通过javaBean注入
@Component
public class SmtpConfig {
    @Value("${app.host}")
    private String host;
    @Value("${app.port:25}")
    private String port;

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }
}
