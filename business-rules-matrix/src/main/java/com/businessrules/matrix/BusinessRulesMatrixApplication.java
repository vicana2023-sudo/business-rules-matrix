package com.businessrules.matrix;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
@SpringBootApplication
@EnableConfigurationProperties
public class BusinessRulesMatrixApplication {
    public static void main(String[] args) {
        SpringApplication.run(BusinessRulesMatrixApplication.class, args);
    }
}
