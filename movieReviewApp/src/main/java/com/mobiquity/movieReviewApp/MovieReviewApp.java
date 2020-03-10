package com.mobiquity.movieReviewApp;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringBootConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Properties;

@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories(basePackages = {"com.mobiquity.movieReviewApp"})
@SpringBootConfiguration
@EnableCaching
public class MovieReviewApp {

    public static void main(String[] args) {
        SpringApplication.run(MovieReviewApp.class, args);
    }

    @Bean
    public JavaMailSender javaMailSender() {

        Dotenv dotenv = Dotenv.load();
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.gmail.com");
        mailSender.setPort(587);

        mailSender.setUsername("MovieReviewApplicationMob@gmail.com");
        mailSender.setPassword(dotenv.get("PASSWORD"));

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.debug", "true");
        return mailSender;
    }

    @Bean
    public Dotenv dotenv() {
        return Dotenv.load();
    }

}
