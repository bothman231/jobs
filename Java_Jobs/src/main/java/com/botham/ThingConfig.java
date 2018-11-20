


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
@PropertySource({ "classpath:thing.properties" })
@EnableJpaRepositories(basePackages = "com.botham.thing.db", 
                       entityManagerFactoryRef = "thingEntityManager", 
                       transactionManagerRef = "thingTransactionManager"
)


public class ThingConfig {
	
	
	Logger log = LoggerFactory.getLogger(ThingConfig.class);
	
	
    @Autowired
    private Environment env;
     
    @Bean
    public LocalContainerEntityManagerFactoryBean thingEntityManager() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(thingDataSource());
        em.setPackagesToScan(new String[] { "com.botham.thing.persist" });
 
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        
        log.error("hibernate.hbm2ddl.auto="+env.getProperty("hibernate.hbm2ddl.auto"));
        
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        
        log.error("hibernate.dialect="+env.getProperty("hibernate.dialect"));
        
        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        

        em.setJpaPropertyMap(properties);
 
        return em;
    }
 
    @Bean
    public DataSource thingDataSource() {
  
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("thing.driverClassName"));
        dataSource.setUrl(env.getProperty("thing.jdbc.url"));
        dataSource.setUsername(env.getProperty("thing.user"));
        dataSource.setPassword(env.getProperty("thing.pass"));
        
 
        return dataSource;
    }
 
    @Bean
    public PlatformTransactionManager thingTransactionManager() {
  
        JpaTransactionManager transactionManager= new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(thingEntityManager().getObject());
        return transactionManager;
    }
}