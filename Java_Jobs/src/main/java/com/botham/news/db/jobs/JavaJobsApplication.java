package com.botham.news.db.jobs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

import com.botham.news.db.jobs.JobsRepository;

@SpringBootApplication
@ComponentScan({"com.botham.news.db.jobs*"})
@EntityScan({"com.botham.news.domain.jobs"})
//@ComponentScan(basePackageClasses = {JobsController.class, JobsRepository.class})
public class JavaJobsApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaJobsApplication.class, args);
	}
}
