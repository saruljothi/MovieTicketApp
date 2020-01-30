package com.mobiquity.movieReviewApp;

import java.util.Properties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

@SpringBootApplication
public class MovieReviewAppApplication {


	public static void main(String[] args) {
		SpringApplication.run(MovieReviewAppApplication.class, args);
	}
@Bean
	public JavaMailSender javaMailSender(){

	JavaMailSenderImpl mailSender =new JavaMailSenderImpl();
	mailSender.setHost("smtp.gmail.com");
	mailSender.setPort(587);

	mailSender.setUsername("MovieReviewApplicationMob@gmail.com");
	mailSender.setPassword(System.getenv("password"));

	Properties props = mailSender.getJavaMailProperties();
	props.put("mail.transport.protocol", "smtp");
	props.put("mail.smtp.auth", "true");
	props.put("mail.smtp.starttls.enable", "true");
	props.put("mail.debug", "true");
		return mailSender ;
	}

}
