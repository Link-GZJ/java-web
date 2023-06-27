package com.guo.learn;

import com.guo.learn.service.MailService;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan
@EnableAspectJAutoProxy
public class AppConfig {
    /*
    因此，正确使用AOP，我们需要一个避坑指南：
    访问被注入的Bean时，总是调用方法而非直接访问字段；
    编写Bean时，如果可能会被代理，就不要编写public final方法。
     */
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        MailService bean = context.getBean(MailService.class);
        System.out.println(bean.sendMail());

    }
}
