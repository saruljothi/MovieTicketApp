package com.mobiquity.movieReviewApp.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.servlet.Filter;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@ContextConfiguration
@ExtendWith(SpringExtension.class)
@WebAppConfiguration
public class CustomConfigTests {

    private WebApplicationContext context;
    private Filter springSecurityFilterChain;
    private SecurityContextRepository securityContextRepository;

    private MockMvc mvc;

    @Autowired
    public CustomConfigTests(WebApplicationContext context, Filter springSecurityFilterChain,
                             SecurityContextRepository securityContextRepository) {
        this.context = context;
        this.springSecurityFilterChain = springSecurityFilterChain;
        this.securityContextRepository = securityContextRepository;
    }

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders
                .webAppContextSetup(context)
                .defaultRequest(get("/"))
                .addFilters(springSecurityFilterChain)
                .build();
    }

    @Test
    void requiresAuthentication() throws Exception {
        mvc
                .perform(get("/"))
                .andExpect(status().isFound());
    }

    @Test
    public void authenticationSuccess() throws Exception {
        mvc
                .perform(formLogin("/authenticate").user("user", "ric")
                .password("pass", "password"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/"))
                .andExpect(authenticated().withUsername("ric"));
    }

    @Test
    void withUserSuccess() throws Exception{
        mvc
                .perform(get("/").with(user("ric")))
                .andExpect(status().isNotFound())
                .andExpect(authenticated().withUsername("ric"));
    }

    @Test
    public void authenticationFailed() throws Exception {
        mvc
                .perform(formLogin("/authenticate").user("user", "notfound")
                        .password("pass", "password"))
                .andExpect(status().isFound())
                .andExpect(redirectedUrl("/authenticate?error"))
                .andExpect(unauthenticated());
    }


    @Configuration
    @EnableWebSecurity
    @EnableWebMvc
    static class Config extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(HttpSecurity http) throws Exception {
            http
                .authorizeRequests()
                .anyRequest().authenticated()
                    .and()
                .securityContext()
                .securityContextRepository(securityContextRepository())
                    .and()
                .formLogin()
                    .usernameParameter("user")
                    .passwordParameter("pass")
                    .loginPage("/authenticate");
        }

        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
            auth
                .inMemoryAuthentication()
                    .withUser("ric")
                    .password(encoder.encode("password"))
                    .roles("USER");

        }

        @Bean
        public SecurityContextRepository securityContextRepository() {
            HttpSessionSecurityContextRepository repo = new HttpSessionSecurityContextRepository();
            repo.setSpringSecurityContextKey("CUSTOM");
            return repo;
        }
    }
}
