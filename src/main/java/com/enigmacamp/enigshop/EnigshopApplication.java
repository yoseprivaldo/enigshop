package com.enigmacamp.enigshop;

import com.enigmacamp.enigshop.controller.ProductController;
import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EnigshopApplication {

	public static void main(String[] args) {

		SpringApplication springApplication = new SpringApplication(EnigshopApplication.class);
		springApplication.setBannerMode(Banner.Mode.OFF);
		springApplication.run(args);

	}

}
