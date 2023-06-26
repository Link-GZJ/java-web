package com.guo.learn.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailService {
    @Value("#{smtpConfig.host}")
    private String smtpHost;
    @Value("#{smtpConfig.port}")
    private String smtpPort;

    public String getSmtpHost() {
        return smtpHost;
    }

    public String getSmtpPort() {
        return smtpPort;
    }
}
