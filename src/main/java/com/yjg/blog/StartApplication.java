package com.yjg.blog;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.yjg.blog.mybatis.dao")
public class StartApplication {
	public static void main(String[] args) {
		SpringApplication.run(StartApplication.class);
			//new SpringApplicationBuilder(StartApplication.class).run(args);
	}
}
