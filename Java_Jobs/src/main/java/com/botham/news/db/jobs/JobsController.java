package com.botham.news.db.jobs;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.aspectj.lang.annotation.Before;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.botham.base.GlobalConstants;
import com.botham.news.db.jobs.JobsRepository;
import com.botham.news.domain.jobs.Jobs;
import com.botham.news.domain.jobs.JobsMirror;
import com.github.dozermapper.core.DozerBeanMapper;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;



//@ComponentScan({"com.botham.news.db.jobs"})
//@ComponentScan(basePackageClasses = {JobsController.class})
@RestController
@RequestMapping("/api")



public class JobsController {
	

	

	
	@Autowired
	public JobsRepository jobsRepository;
	
	

	
	
   @GetMapping("/hello") 
   public String hello() {
	   System.out.println("hello ran "+new Date());
	   
	   return "{\"message\": \"This is a https:// endpoint\"}";
   }
   
   
// Needs Pagination etc
   
   @GetMapping("/jobs") 
   public List<Jobs> getAllJobs() {	   
	   return jobsRepository.findAll();
   }
   
// This is done   
   @GetMapping("/job/{id}") 
   public Optional<Jobs> getJob(@PathVariable(value = "id", required = true) Integer id) {
	   
	   if (!jobsRepository.existsById(id)) {
	      throw new NotFoundException("This job does not exist, id="+id);  
	   }
	   
	   //System.out.println("here "+"id="+id);
	   return jobsRepository.findById(id);
   }
   
   @RequestMapping(value="/job", method=RequestMethod.POST) 
   public String createJob(@RequestBody Jobs job) {
	   String mName="createJob-POST"; 
	   
	   System.out.println(mName+" here "+"job to create="+job);
	   
	   
	   if (jobsRepository.existsById(job.getId())) {
		   return "This jobs already exists, use update";
	   }
	   
// Do we always accept what is sent on create ? or default some fields?
// Seq id
	   
	   jobsRepository.save(job);
	   
	   return "Created";
	}
   
   
   @RequestMapping(value="/job/{id}", method=RequestMethod.DELETE) 
   public String deleteJob(@PathVariable(value = "id", required = true) Integer id) {
	   String mName="deleteJob-DELETE"; 
	   System.out.println(mName+" here "+"job to delete="+id);
	   	   
	   if (!jobsRepository.existsById(id)) {
		   return "This jobs does not exist";
	   }
	   
	   jobsRepository.deleteById(id);
	   
	   return id+" deleted";
	}
   
   
   
// A PUT should take everything that is passed and update it to the DB.
  
   @RequestMapping(value="/job", method=RequestMethod.PUT) 
   public String updateJob(@RequestBody Jobs job) {
	   String mName="UpdateJob-PUT"; 
	   System.out.println(mName+" here "+"job to update="+job);
	   
	   
	   if (!jobsRepository.existsById(job.getId())) {
		   return "This job does not exist";
	   }
	   
	   jobsRepository.save(job);
	   
	   return "Updated";
	}
   
  
// A PATCH has to figure out what was sent and only update those fields.
   
   @RequestMapping(value="/job", method=RequestMethod.PATCH) 
   public Jobs updateJob1(@RequestBody Jobs job) {
	   String mName="UpdateJob1-PATCH"; 
	   System.out.println(mName+" here "+"job to update="+job);
	    
	   /*
	   if (!jobsRepository.existsById(job.getId())) {
		   return "This job does not exist";
	   }
	   */

// Get the entity as it is now on the DB
	   Optional<Jobs> preUpdateJob = jobsRepository.findById(job.getId());
	   
	   Jobs saveJob=preUpdateJob.get();
	   
	   System.out.println(preUpdateJob);
	   
	   if (job.getName()!=null) {
		   saveJob.setName(job.getName());
	   }
	   
	   if (job.getCurrentStatus()!=null) {
		   saveJob.setCurrentStatus(job.getCurrentStatus());
	   }
	   
	   if (job.getInfo()!=null) {
		   saveJob.setInfo(job.getInfo());
	   }
	   
	   if (job.getStart()!=null) {
		   saveJob.setStart(job.getStart());
	   }
	   
	   if (job.getEnd()!=null) {
		   saveJob.setEnd(job.getEnd());
	   }
	   
	   if (job.getDescription()!=null) {
		   saveJob.setDescription(job.getDescription());
	   }
	   
	   if (job.getLastRun()!=null) {
		   saveJob.setLastRun(job.getLastRun());
	   }
	   
	   if (job.getSchedule()!=null) {
		   saveJob.setSchedule(job.getSchedule());
	   }
	   
	   if (job.getNotificationsSent()!=null) {
		   saveJob.setNotificationsSent(job.getNotificationsSent());
	   }
	   
	   if (job.getRunOnNodes()!=null) {
		   saveJob.setRunOnNodes(job.getRunOnNodes());
	   }
	   
	   if (job.getRunOnInstances()!=null) {
		   saveJob.setRunOnInstances(job.getRunOnInstances());
	   }
	   
	   if (job.getStatusAcknowledged()!=null) {
		   saveJob.setStatusAcknowledged(job.getStatusAcknowledged());
	   }

	   
	   Mapper mapper = DozerBeanMapperBuilder.create()
		        .withMappingFiles("jobsDozer.xml")
		        .build();
	   
	   
	   System.out.println("before mapping job="+job.toString());
	   System.out.println("before mapping saveJob="+saveJob.toString());
       //DozerBeanMapper mapper = new DozerBeanMapper();
       
       
       //Mapper mapper = DozerBeanMapperBuilder.buildDefault();
       //saveJob = mapper.map(job, Jobs.class);
       
//     saveJob = mapper.map(job, Jobs.class);
	   System.out.println("after mapping="+saveJob.toString());
       
	   //if (preUpdateJob)
	   
	   jobsRepository.save(saveJob);
	   
	   return saveJob;
	   //return "Updated 1";
	}
   
   
   @PostConstruct
   public void afterPropsSet() {
	   System.out.println("jobsRepository="+jobsRepository);
	   /*
	   List<Jobs> jobs = jobsRepository.findAll();
	   for (Jobs j : jobs) {
		   System.out.println("j="+j.toString());
	   }
	   */
	   
   }
}
