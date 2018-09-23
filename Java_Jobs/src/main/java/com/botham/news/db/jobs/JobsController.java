package com.botham.news.db.jobs;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.botham.news.domain.jobs.Jobs;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

//@ComponentScan({"com.botham.news.db.jobs"})
//@ComponentScan(basePackageClasses = {JobsController.class})
@RestController
@RequestMapping("/api")

public class JobsController {

	Logger log = LoggerFactory.getLogger(JobsController.class);
	
	@Autowired
	public JobsRepository jobsRepository;
	
   @GetMapping("/hello") 
   public String hello() {
	   System.out.println("hello ran "+new Date());
	   
	   return "{\"message\": \"This is a https:// endpoint\"}";
   }
   
   
// Needs Pagination/Fields/Filters/Sort etc
// https://localhost:8445/api/jobs?fields=name,description&filter=name like 'fred'
// https://localhost:8445/api/jobs?fields=name,description&filter=name like 'fred'&page=1,2,20&sort=name,-description
   
   @GetMapping("/jobs") 
   public List<Jobs> getAllJobs(@RequestParam(value="fields", required=false) String fields,
		                        @RequestParam(value="filter", required=false) String filter,
		                        @RequestParam(value="sort", required=false) String sort,
		                        @RequestParam(value="page", required=false) String page) {	
	   String mName="getAllJobs";
	   if (log.isDebugEnabled()) {
		   log.debug(mName+" Starts");
		   log.debug(mName+" fields="+fields+"*");
		   log.debug(mName+" filter="+filter+"*");
		   log.debug(mName+" sort="+sort+"*");
		   log.debug(mName+" page="+page+"*");
	   }
	   return jobsRepository.findAll();
   }
   
// This is done   
   @GetMapping("/jobs/{id}") 
   public ResponseEntity<?> getJob(@PathVariable(value = "id", required = true) Integer id) {
	   
	   if (!jobsRepository.existsById(id)) {

	      return new ResponseEntity<ErrorMessage>(new ErrorMessage("This job does not exist, id="+id, "0001"), HttpStatus.OK);
	   }

	   Jobs job = null;
	   Optional<Jobs> jobsOpt= jobsRepository.findById(id);
	   if (jobsOpt.isPresent()) {
		   job=jobsOpt.get();
	   }

   
	   return new ResponseEntity<Jobs>(job, HttpStatus.OK);
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
