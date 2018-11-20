package com.botham;

import java.util.HashMap;

import javax.sql.DataSource;

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
@PropertySource({ "classpath:mongo.properties" })
@EnableJpaRepositories(basePackages = "com.botham.mongo.db", 
                       entityManagerFactoryRef = "mongoEntityManager", 
                       transactionManagerRef = "mongoTransactionManager"
)
public class MongoConfig {
    @Autowired
    private Environment env;
     
    @Bean
    public LocalContainerEntityManagerFactoryBean mongoEntityManager() {
    	
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(mongoDataSource());
        em.setPackagesToScan(new String[] { "com.botham.mongo.domain" });
 
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        HashMap<String, Object> properties = new HashMap<>();
        properties.put("hibernate.hbm2ddl.auto", env.getProperty("hibernate.hbm2ddl.auto"));
        properties.put("hibernate.dialect", env.getProperty("hibernate.dialect"));
        em.setJpaPropertyMap(properties);
 
        return em;
    }
 
    @Bean
    public DataSource mongoDataSource() {
  
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("mongo.driverClassName"));
        dataSource.setUrl(env.getProperty("mongo.jdbc.url"));
        dataSource.setUsername(env.getProperty("mongo.user"));
        dataSource.setPassword(env.getProperty("mongo.pass"));
 
        return dataSource;
    }
 
    @Bean
    public PlatformTransactionManager mongoTransactionManager() {
  
        JpaTransactionManager transactionManager= new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(mongoEntityManager().getObject());
        return transactionManager;
    }
}