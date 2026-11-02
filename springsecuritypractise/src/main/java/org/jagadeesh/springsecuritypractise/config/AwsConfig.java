package org.jagadeesh.springsecuritypractise.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.ec2.Ec2Client;

@Configuration
public class AwsConfig {

    @Bean
    public Ec2Client ec2Client(
            @Value("${aws.region}") String region) {

        return Ec2Client.builder()
                .region(Region.of(region))
                .build();
    }
}
