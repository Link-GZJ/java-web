package com.guo.learn.bean;

import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

import java.time.ZoneId;

@Component
public class ZoneIdFactoryBean implements FactoryBean<ZoneId> {
    @Override
    public ZoneId getObject() throws Exception {
        return ZoneId.of("Z");
    }

    @Override
    public Class<?> getObjectType() {
        return ZoneId.class;
    }
}
