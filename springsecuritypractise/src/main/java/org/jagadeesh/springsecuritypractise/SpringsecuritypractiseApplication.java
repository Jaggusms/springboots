package org.jagadeesh.springsecuritypractise;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableKafka
public class SpringsecuritypractiseApplication {

    public static void main ( String[] args ) {
        SpringApplication.run ( SpringsecuritypractiseApplication.class , args );
    }

}
