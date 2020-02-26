package com.mobiquity.movieReviewApp.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class JdbcConfig {

    private Environment env;

    public JdbcConfig(Environment env) {
        this.env = env;
    }

//    @Bean
//    public DriverManagerDataSource dataSource() {
//
//        System.out.println("Stop here");
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName("org.h2.Driver");
//        dataSource.setUrl("jdbc:h2:~/movieReview;MV_STORE=false");
//        dataSource.setUsername("sa");
//        dataSource.setPassword("");
//
////        Resource initSchema = new ClassPathResource("/security/schema-h2.sql");
////        Resource initData = new ClassPathResource("/security/data-h2.sql");
//
//        return dataSource;
//    }
}
