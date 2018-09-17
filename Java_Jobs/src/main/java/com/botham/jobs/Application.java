package com.botham.jobs;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.botham.news.db.jobs.JobsRepository;



// Add this class if you are going to deploy this to an external TOMCAT Server,
// Remove / Comment it for embedded STS

@Configuration
//@ComponentScan
@ComponentScan(basePackages = {"com.botham.news.db.jobs"}, basePackageClasses = JobsRepository.class)
// package com.botham.news.db.jobs;

@EnableAutoConfiguration
public class Application extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(applicationClass, args);
    }

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(applicationClass);
    }

    private static Class<Application> applicationClass = Application.class;
}


