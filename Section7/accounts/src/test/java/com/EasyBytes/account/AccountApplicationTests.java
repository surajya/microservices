package com.EasyBytes.account;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
//Removin unnessesary import package "ctrl+alt+o"
@Slf4j
@SpringBootTest
class AccountApplicationTests {
	public static void main(String[] args) {
        SpringApplication.run(AccountApplication.class, args);
		log.info("again staring...");
    }
	@Test
	void contextLoads() {
		System.out.println("Hello World");
	}

}
