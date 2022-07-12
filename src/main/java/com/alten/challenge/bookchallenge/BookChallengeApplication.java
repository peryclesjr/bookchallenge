package com.alten.challenge.bookchallenge;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc
@EnableCaching
public class BookChallengeApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookChallengeApplication.class, args);
	}

}
