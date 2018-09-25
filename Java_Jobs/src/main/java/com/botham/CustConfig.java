


package com.botham;


import java.util.HashMap;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@PropertySource({ "classpath:cust.properties" })
@EnableJpaRepositories(basePackages = "com.botham.cust.db", 
                       entityManagerFactoryRef = "custEntityManager", 
                       transactionManagerRef = "custTransactionManager"
)


public class CustConfig {
	
	
	Logger log = LoggerFactory.getLogger(CustConfig.class);
	
	
    @Autowired
    private Environment env;
     
    @Bean
    public LocalContainerEntityManagerFactoryBean custEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(custDataSource());
        em.setPackagesToScan(new String[] { "com.botham.cust.domain" });
 
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        
        log.error("hibernate.hbm2ddl.auto="+env.getProperty("hibernate.hbm2ddl.auto"));
        
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        
        log.error("hibernate.dialect="+env.getProperty("hibernate.dialect"));
        
        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        
        properties.put("hibernate.jdbc.lob.non_contextual_creation", env.getProperty("spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation"));
        
        em.setJpaPropertyMap(properties);
 
        return em;
    }
 
    @Bean
    public DataSource custDataSource() {
  
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("cust.driverClassName"));
        dataSource.setUrl(env.getProperty("cust.jdbc.url"));
        dataSource.setUsername(env.getProperty("cust.user"));
        dataSource.setPassword(env.getProperty("cust.pass"));
        
 
        return dataSource;
    }
 
    @Bean
    public PlatformTransactionManager custTransactionManager() {
  
        JpaTransactionManager transactionManager= new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(custEntityManager().getObject());
        return transactionManager;
    }
}