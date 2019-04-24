package com.gyportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
//@ServletComponentScan
public class GYSpringBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(GYSpringBootApplication.class, args);
    }
}

