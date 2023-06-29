package com.guo.learn.resource;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Component
public class AppService {
    @Value("logo.txt")
    Resource resource;
    String logo;

    @PostConstruct
    public void init() {
        try (var reader = new BufferedReader(new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8))) {
            logo = reader.lines().collect(Collectors.joining("\t"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getLogo() {
        return logo;
    }
}
