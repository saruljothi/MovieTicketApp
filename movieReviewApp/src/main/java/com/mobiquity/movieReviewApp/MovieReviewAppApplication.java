package com.mobiquity.movieReviewApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@EnableJpaRepositories(basePackages = {"com.mobiquity.movieReviewApp"})
public class MovieReviewAppApplication {

  public static void main(String[] args) {
    SpringApplication.run(MovieReviewAppApplication.class, args);
  }

}
