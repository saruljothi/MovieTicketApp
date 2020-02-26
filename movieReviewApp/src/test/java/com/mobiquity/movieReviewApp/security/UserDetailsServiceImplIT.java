package com.mobiquity.movieReviewApp.security;

import com.mobiquity.movieReviewApp.security.config.JdbcConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

@ContextConfiguration(classes = {JdbcConfig.class})
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
@TestPropertySource(locations = "classpath:test.properties")
public class UserDetailsServiceImplIT {

    @Test
    void newTest() {
        System.out.println("Stop");

    }

}
