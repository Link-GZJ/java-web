package com.guo.learn.bean;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

//factorybean 可用来注入第三方bean ,@autowired 可以直接注入
@Component
public class DataSourceFactoryBean implements FactoryBean<HikariDataSource> {
    @Override
    public HikariDataSource getObject() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://192.168.189.128:3306/fruitdb?useUnicode=true&characterEncoding=utf-8&useSSL=false");
        config.setUsername("root");
        config.setPassword("123456");
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "100");
        config.addDataSourceProperty("maximumPoolSize", "10");
        return new HikariDataSource(config);
    }

    @Override
    public Class<?> getObjectType() {
        return HikariDataSource.class;
    }
}
