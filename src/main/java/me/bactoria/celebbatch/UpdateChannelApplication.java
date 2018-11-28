package me.bactoria.celebbatch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@EnableBatchProcessing
@SpringBootApplication
public class UpdateChannelApplication {
    public static void main(String[] args) {
        new SpringApplicationBuilder(UpdateChannelApplication.class)
                .properties(APPLICATION_LOCATIONS)
                .run(args);
    }

    public static final String APPLICATION_LOCATIONS = "spring.config.location="
            + "/home/ec2-user/batch/config/real-application.properties";

}
