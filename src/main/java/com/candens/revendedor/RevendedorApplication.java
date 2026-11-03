package com.candens.revendedor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:applicationContext.xml")
public class RevendedorApplication {

	public static void main(String[] args) {

		SpringApplication.run(RevendedorApplication.class, args);
	}

}
