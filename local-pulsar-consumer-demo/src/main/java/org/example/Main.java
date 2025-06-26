package org.example;

import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.PulsarClientException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@Slf4j
public class Main implements CommandLineRunner {

    @Autowired
    private Consumer<String> foo;

    @Autowired
    private Consumer<String> bar;

    public static void main(String[] args) {
        log.info("Starting Application...");
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String[] args) throws Exception {
    }

    @PreDestroy
    public void cleanUp() {
        try {
            log.info("Closing foo consumer...");
            foo.close();
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }

        try {
            log.info("Closing bar consumer...");
            bar.close();
        } catch (PulsarClientException e) {
            e.printStackTrace();
        }
    }
}
