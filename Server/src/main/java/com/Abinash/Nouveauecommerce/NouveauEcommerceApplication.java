package com.Abinash.Nouveauecommerce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import com.Abinash.Nouveauecommerce.Controller.AuthController;

@SpringBootApplication
@ComponentScan(basePackages = {"com.Abinash.Nouveauecommerce"})
public class NouveauEcommerceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NouveauEcommerceApplication.class, args);
	}

}
