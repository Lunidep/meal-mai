package ru.lunidep.micro.meal.mealserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@SpringBootApplication
@EnableEurekaServer
public class mealServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(mealServerApplication.class, args);
    }

}
