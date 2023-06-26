package com.guo.learn.resource;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;

@Component
public class PropertiesUtil {
    @Value("db.properties")
    Resource resource;
    Properties properties = new Properties();

    public Properties getProperties() {
        return properties;
    }

    @PostConstruct
    public void init() {
        try {
            properties.load(resource.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
