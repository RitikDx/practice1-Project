package com.practice1;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Practice1Application {

	public static void main(String[] args) {

		SpringApplication.run(Practice1Application.class, args);
	}
		@Bean
		public ModelMapper modelMapper(){
			return new ModelMapper();
	}
}
