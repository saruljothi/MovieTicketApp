package com.mobiquity.movieReviewApp.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class SecurityConfig {

    private Environment env;

    public SecurityConfig(Environment env) {
        this.env = env;
    }
}
