package ru.lunidep.micro.meal.mealconfig;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;
import org.springframework.boot.SpringApplication;

@SpringBootApplication
@EnableConfigServer
public class mealConfigApplication {

    public static void main(String[] args) {
        SpringApplication.run(mealConfigApplication.class, args
        );
    }

}
