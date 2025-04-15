package com.example.loans;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LoansApplication {
    private static final Logger log = LoggerFactory.getLogger(LoansApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(LoansApplication.class, args);
        log.info("Application started");
    }

}
