package org.example;

import org.example.dto.ProductEvent;
import org.example.service.ProducerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Main implements CommandLineRunner {

    @Autowired
    @Qualifier("pulsarProducer")
    private ProducerService producerService;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        final ProductEvent event = ProductEvent.builder()
            .productId(123L)
            .productName("Rugs")
            .build();

        producerService.forward(event);
    }
}
