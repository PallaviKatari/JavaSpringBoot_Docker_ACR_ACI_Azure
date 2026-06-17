package com.example.demo;

import org.springframework.http.MediaType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class DemoApplication {

	@RequestMapping(value = "/", produces = MediaType.TEXT_HTML_VALUE)
	public String home()
	{
		return "<h1>Hello World</h1>";
	} 

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);

	}

}
