package com.datascope;

import com.vaadin.spring.annotation.EnableVaadin;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableVaadin
public class DatatouchApplication {

	public static void main(String[] args) {
		SpringApplication.run(DatatouchApplication.class, args);
	}
}
