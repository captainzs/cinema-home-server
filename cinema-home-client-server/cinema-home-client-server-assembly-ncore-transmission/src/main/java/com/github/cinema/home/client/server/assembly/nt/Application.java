package com.github.cinema.home.client.server.assembly.nt;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableRetry
@EnableSwagger2
@EnableScheduling
@SpringBootApplication(scanBasePackages = "com.github.cinema.home.client.server")
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
