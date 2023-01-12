package com.MongoDb.Joc;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class JocDausMongodbApplication {

	public static void main(String[] args) {
		SpringApplication.run(JocDausMongodbApplication.class, args);
	}

	//Utilitzem modelmapper per poder mapejar per convertir d'identitat a dto, o al contrari
	@Bean
	public ModelMapper modelMapper(){
		return new ModelMapper();
	}
}
