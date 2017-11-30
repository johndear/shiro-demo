package com.ls;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication 
@EnableCaching
//@ImportResource("classpath:dubbo.xml")
public class Startup {
	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(Startup.class);
		//app.setBannerMode(Banner.Mode.OFF); 1
		app.run(args);
	}
}
 