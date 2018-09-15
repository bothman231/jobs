package com.botham.jobs;

import java.util.Date;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/hello")
public class JobsController {
   @GetMapping 
   public String hello() {
	   System.out.println("hello ran "+new Date());
	   
	   return "{\"message\": \"This is a https:// endpoint\"}";
   }
}
