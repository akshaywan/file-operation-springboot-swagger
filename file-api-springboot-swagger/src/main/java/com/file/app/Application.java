package com.file.app;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.file.app.service.FileService;

@SpringBootApplication
public class Application  implements CommandLineRunner {
	@Autowired
	FileService fileService;
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);

	}
	@Override
	  public void run(String... arg) throws Exception {
		fileService.deleteFile();
		fileService.initDir();
	  }
	
}
