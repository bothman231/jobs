package com.botham;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(exclude = {SecurityAutoConfiguration.class })


//@SpringBootApplication 
@ComponentScan({"com.botham*", "com.botham.news.db.jobs*", "com.botham.cust.db.customer*",
	"com.botham.thing.db*" })
@EntityScan({"com.botham.news.domain.jobs", "com.botham.cust.domain.customer", "com.botham.news.domain.db",
	          "com.botham.thing.persist"})
//@EnableOAuth2Sso
@EnableAutoConfiguration
//@ComponentScan(basePackageClasses = {JobsController.class, JobsRepository.class})
public class JavaJobsApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaJobsApplication.class, args);
	}
}