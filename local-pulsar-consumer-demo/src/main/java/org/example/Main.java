package org.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class Main {

    public static void main(String[] args) {
        log.info("Starting Application...");

        // inject Vault secrets

        final SpringApplication application = new SpringApplication(Main.class);

        // add profile from env

        application.run(args);
    }
}
