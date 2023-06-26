package com.guo.learn;

import com.guo.learn.bean.MailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.*;

import java.time.ZoneId;


@ComponentScan
@Configuration
@PropertySource("app.properties") //@PropertySource读取的配置是针对IoC容器全局的，其他任何bean都可以直接引用已读取到的配置
public class AppConfig {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        ZoneId zone = context.getBean(ZoneId.class);
        System.out.println(zone);
        MailService mail = context.getBean(MailService.class);
        System.out.println(mail.getSmtpHost());
        System.out.println(mail.getSmtpPort());
    }

    @Value("${app.zone:Z}")
    String zoneId;
    @Bean
    ZoneId createZoneId() {
        return ZoneId.of(zoneId);
    }
}
