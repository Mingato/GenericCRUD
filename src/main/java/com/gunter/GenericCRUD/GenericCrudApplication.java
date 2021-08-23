package com.gunter.GenericCRUD;

import com.gunter.GenericCRUD.repository.MyClassRepository;
import com.gunter.GenericCRUD.service.MyClassInstanced;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GenericCrudApplication {


	public static void main(String[] args) {
		SpringApplication.run(GenericCrudApplication.class, args);
	}

}
