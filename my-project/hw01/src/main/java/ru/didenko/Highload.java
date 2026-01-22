package ru.didenko;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Highload {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Highload.class);
    }
}
