package com.guo.learn;

import com.guo.learn.bean.DataSourceFactoryBean;
import com.guo.learn.bean.Mail;
import com.guo.learn.bean.Validators;
import com.guo.learn.resource.AppService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;

@Configuration
@ComponentScan
public class AppConfig {
    public static void main(String[] args) {
        /*
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
//        Validators bean = context.getBean(Validators.class);
//        bean.validate("123","123","tom");
        Mail bean = context.getBean(Mail.class);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //这里要主动关闭容器，才会出发bean的destroy方法
        context.close();

       */

        /*
        // factorybean 的应用
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        DataSourceFactoryBean bean = context.getBean(DataSourceFactoryBean.class);
        try (Connection con  = bean.getObject().getConnection()) {
            try (PreparedStatement ps = con.prepareStatement("select * from user");
                 ResultSet rs = ps.executeQuery()){
                while (rs.next()) {
                    System.out.println(rs.getInt("id"));
                    System.out.println(rs.getString("name"));
                    System.out.println(rs.getString("email"));
                    System.out.println(rs.getString("password"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }*/

        //@resource 应用
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        AppService bean = context.getBean(AppService.class);
        System.out.println(bean.getLogo());
    }

    /*
    //创建第三方bean
    @Bean("z")
    @Primary// 指定为主要Bean
    ZoneId createZoneId() {
        return ZoneId.of("Z");
    }
    //使用别名
    @Bean
    @Qualifier("utc8")
    ZoneId createZoneOfUTC8() {
        return ZoneId.of("UTC+08:00");
    }

     */
}
