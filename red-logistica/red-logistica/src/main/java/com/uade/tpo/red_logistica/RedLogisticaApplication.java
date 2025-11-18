package com.uade.tpo.red_logistica;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.uade.tpo.red_logistica")

public class RedLogisticaApplication {

    public static void main(String[] args) {
        SpringApplication.run(RedLogisticaApplication.class, args);
    }

}
