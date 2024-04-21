package ru.lunidep.micro.meal.delivery;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan(basePackages = {"ru.lunidep.micro.meal"})
@EnableJpaRepositories(basePackages = {"ru.lunidep.micro.meal.menu"})
@EnableFeignClients
@RefreshScope
public class mealmenuApplication {

    public static void main(String[] args) {
        SpringApplication.run(mealmenuApplication.class, args);
    }


}
