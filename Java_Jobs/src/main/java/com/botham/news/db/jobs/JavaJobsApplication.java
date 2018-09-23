package com.botham.news.db.jobs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })

//@SpringBootApplication 
@ComponentScan({"com.botham.news.db.jobs*"})
@EntityScan({"com.botham.news.domain.jobs"})
//@EnableOAuth2Sso
@EnableAutoConfiguration
//@ComponentScan(basePackageClasses = {JobsController.class, JobsRepository.class})
public class JavaJobsApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaJobsApplication.class, args);
	}
}