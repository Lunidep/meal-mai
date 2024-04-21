package ru.lunidep.micro.meal.users;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@EnableEurekaClient
@ComponentScan(basePackages = {"ru.lunidep.micro.meal"})
@RefreshScope
public class mealUsersApplication {

    public static void main(String[] args) {
        SpringApplication.run(mealUsersApplication.class, args);
    }

}
