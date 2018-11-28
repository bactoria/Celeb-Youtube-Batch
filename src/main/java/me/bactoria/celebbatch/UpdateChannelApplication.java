package me.bactoria.celebbatch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableBatchProcessing
@SpringBootApplication
public class UpdateChannelApplication {
    public static void main(String[] args) {
        SpringApplication.run(UpdateChannelApplication.class, args);
    }
}
