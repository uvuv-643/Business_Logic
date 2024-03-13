package ru.uvuv643.business_logic;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@EntityScan(basePackages = {"ru.uvuv643.business_logic.models"})
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class BusinessLogicApplication {

    /*

        POST    /login              +
        POST    /register           +

        POST    /articles           +
        GET     /articles
        GET     /articles/my
        GET     /articles/{id}
        PUT     /articles/{id}
        DELETE  /articles/{id}

        // article_id in first two
        POST    /attachments        +
        GET     /attachments
        GET     /attachments/{id}
        DELETE  /attachments/{id}

     */
    public static void main(String[] args) {
        SpringApplication.run(BusinessLogicApplication.class, args);
    }
}
