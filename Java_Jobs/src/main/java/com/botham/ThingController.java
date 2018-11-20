package com.botham;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.annotation.PostConstruct;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.botham.cust.db.customer.CustomerRepository;
import com.botham.cust.domain.customer.Customer;
import com.botham.news.db.jobs.ErrorMessage;
import com.botham.news.db.jobs.JobsRepository;
import com.botham.news.domain.jobs.Jobs;
import com.botham.thing.db.ThingFieldRepository;
import com.botham.thing.db.ThingRepository;
import com.botham.thing.db.ThingViewRepository;
import com.botham.thing.persist.Thing;
import com.botham.thing.persist.ThingField;
import com.botham.thing.persist.ThingView;
import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import lombok.extern.java.Log;


@Log
@RestController
@RequestMapping("/")

// CRUD Services

// GetMultiple
// Get1
// Create
// Update
// Delete
// Change




public class ThingController {

	Logger log = LoggerFactory.getLogger(ThingController.class);
	
	@Autowired
	public ThingRepository thingRepository;
	
	@Autowired
	public ThingViewRepository thingViewRepository;
	
	@Autowired
	public ThingFieldRepository thingFieldRepository;
	
   @GetMapping("/hello") 
   public String hello() {
	   System.out.println("hello ran "+new Date());
	   
	   return "{\"message\": \"This is a https:// endpoint\"}";
   }
   
   
// Needs Pagination/Fields/Filters/Sort etc
// https://localhost:8445/api/jobs?fields=name,description&filter=name like 'fred'
// https://localhost:8445/api/jobs?fields=name,description&filter=name like 'fred'&page=1,2,20&sort=name,-description
   
   @GetMapping("/things") 
   public List<Thing> getAllThings(@RequestParam(value="fields", required=false) String fields,
		                        @RequestParam(value="filter", required=false) String filter,
		                        @RequestParam(value="sort", required=false) String sort,
		                        @RequestParam(value="page", required=false) String page) {	
	   String mName="getAllThings";
	   if (log.isDebugEnabled()) {
		   log.debug(mName+" Starts");
		   log.debug(mName+" fields="+fields+"*");
		   log.debug(mName+" filter="+filter+"*");
		   log.debug(mName+" sort="+sort+"*");
		   log.debug(mName+" page="+page+"*");
	   }
	   
	   return thingRepository.findAll();
   }
   
   
   public void searchForThing(String searchTerm) {
	   String mName="searchForThing";
	   boolean searchFields=true;
	   
	   //List<Thing> entities = thingRepository.findByThingDescriptionLike(searchTerm);
	   
	   
	   List<ThingView> entities1 = thingViewRepository.findByThingFieldDescriptionLike(searchTerm);
	   
	   for (ThingView tv : entities1) {
		   
		  if (log.isDebugEnabled()) {
	         log.debug(mName+" by thing field = thingId="+tv.getThingId()+" "+
	        		     " thing="+tv.getThingDescription()+" "+
	        		       tv.getThingFieldDescription());
		  }
	   }
	   
	   List<ThingView> entities2 = thingViewRepository.findByThingDescriptionLike(searchTerm);
	   
	   for (ThingView tv : entities2) {
		   
		  if (log.isDebugEnabled()) {
	         log.debug(mName+" by thing = thingId="+tv.getThingId()+" "+
	        		     " thing="+tv.getThingDescription()+" "+
	        		       tv.getThingFieldDescription());
		  }
	   }
	   
   }
   
   @PostConstruct


   public void afterPropsSet() {
	   System.out.println("thingRepository="+thingRepository);
	   
	   //List<Thing> entities = thingRepository.findAll();
	   //String searchTerm="%1103022288%";
	   String searchTerm="%030%";
	   
	   
	   searchForThing(searchTerm);
		   
		   
		   
	   /*
	   List<Thing> entities = thingRepository.findByThingDescriptionLike(searchTerm);
	   
	   if (entities.isEmpty()) {
		   log.error("NON FOUND *****************************");
	   }
	   
	   for (Thing entity : entities) {
		   
		   //Hibernate.initialize(entity.getThingFields());
		   
		   System.out.println("entity="+entity.getThingId()+" "+entity.getThingDescription()+" "+entity.getThingFields().size());
	   }
	   */
	
	   
   }
   
   
}

	
