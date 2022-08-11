package com.catalogo;

import com.catalogo.services.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CatalogoApplication implements CommandLineRunner {

	@Autowired
	private S3Service s3Service;
	public static void main(String[] args) {
		SpringApplication.run(CatalogoApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception{
		s3Service.uploadFile("/home/almeida/Downloads/img-profile.svg");
	}

}
