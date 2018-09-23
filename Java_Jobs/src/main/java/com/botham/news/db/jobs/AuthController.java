package com.botham.news.db.jobs;

import java.util.Date;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@ComponentScan({"com.botham.news.db.jobs"})
//@ComponentScan(basePackageClasses = {JobsController.class})
@RestController
@RequestMapping("/api")
@EnableAutoConfiguration
//@EnableOAuth2Sso

public class AuthController {

	Logger log = LoggerFactory.getLogger(AuthController.class);
	
	
	
   @GetMapping("/auth") 
   public String hello() {
	   System.out.println("hello ran "+new Date());
	   
	   return "{\"message\": \"This is a https:// endpoint\"}";
   }
   
   
   
   
   @PostConstruct
   public void afterPropsSet() {
	   System.out.println("Starts");
	   /*
	   List<Jobs> jobs = jobsRepository.findAll();
	   for (Jobs j : jobs) {
		   System.out.println("j="+j.toString());
	   }
	   */
	   
   }
}
