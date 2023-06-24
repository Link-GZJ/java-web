package com.guo;

import com.guo.learn.service.User;
import com.guo.learn.service.UserService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
//        BeanFactory factory = new XmlBeanFactory(new ClassPathResource("application.xml"));
//        MailService mailService = factory.getBean(MailService.class);
        ApplicationContext context = new ClassPathXmlApplicationContext("application.xml");
        UserService userService = context.getBean(UserService.class);
        User user = userService.login("bob@example.com", "password");
        System.out.println(user.getName());
    }
}
