package com.mobiquity.movieReviewApp;

import io.github.cdimascio.dotenv.Dotenv;
import java.util.Properties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableJpaRepositories(basePackages = {"com.mobiquity.movieReviewApp"})
public class MovieReviewApp {


	public static void main(String[] args) {
		SpringApplication.run(MovieReviewApp.class, args);
	}

@Bean
	public JavaMailSender javaMailSender(){

	Dotenv dotenv = Dotenv.load();
	JavaMailSenderImpl mailSender =new JavaMailSenderImpl();
	mailSender.setHost("smtp.gmail.com");
	mailSender.setPort(587);

	mailSender.setUsername("MovieReviewApplicationMob@gmail.com");
	mailSender.setPassword(dotenv.get("password"));

	Properties props = mailSender.getJavaMailProperties();
	props.put("mail.transport.protocol", "smtp");
	props.put("mail.smtp.auth", "true");
	props.put("mail.smtp.starttls.enable", "true");
	props.put("mail.debug", "true");
		return mailSender ;
	}
	@Bean
	public Dotenv dotenv()
	{
		return Dotenv.load();
	}

}
