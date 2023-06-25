package com.guo.learn.bean;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.time.ZoneId;

@Component
//原型，每次调用getBean()都返回一个新的实例
//@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
public class Mail {
    //可选注入
    @Autowired(required = false)
    //@Qualifier("z")// 指定注入名称为"z"的ZoneId
    ZoneId zoneId = ZoneId.systemDefault();

    //一个Bean在注入必要的依赖后，需要进行初始化（监听消息等）。在容器关闭时，有时候还需要清理资源（关闭连接池等）。我们通常会定义一个init()方法进行初始化，定义一个shutdown()方法进行清理，然后，引入JSR-250定义的Annotation：
    @PostConstruct
    public void init() {
        System.out.println("Init mail service with zoneId = " + this.zoneId);
    }
    @PreDestroy
    public void shutdown() {
        System.out.println("Shutdown mail service");
    }
}
