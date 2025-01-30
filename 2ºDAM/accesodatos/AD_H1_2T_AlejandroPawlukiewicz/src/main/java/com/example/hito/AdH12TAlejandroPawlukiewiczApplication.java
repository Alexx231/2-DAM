package com.example.hito;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication(scanBasePackages = "com.example")
@EnableJpaRepositories(basePackages = "com.example.repositorios")
@EntityScan(basePackages = "com.example.modulos")
public class AdH12TAlejandroPawlukiewiczApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdH12TAlejandroPawlukiewiczApplication.class, args);
    }
}