package com.mobiquity.movieReviewApp.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@EnableWebSecurity(debug = false)
@EnableGlobalMethodSecurity(securedEnabled = true)
public class AuthenticationConfig extends WebSecurityConfigurerAdapter {

    private static final String[] AUTH_WHITELIST = {
            //public endpoints
            "/v1/signUp/**",
            "/v1/forgotPassword/**",
            //swagger endpoints
            "/v2/api-docs",
            "/configuration/ui",
            "/swagger-resources/**",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            //h2
            "/h2/**"
    };

    private final String port;
    private UserDetailsService userDetailsService;

    public AuthenticationConfig(CustomUserDetailsService userDetailsService,
                                @Value("${server.port}") String port) {

        this.userDetailsService = userDetailsService;
        this.port = port;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers("/**").authenticated()
                .and()
                .formLogin()
                .successHandler(new SimpleUrlAuthenticationSuccessHandler(
                        "http://localhost:" + this.port + "/welcome/"))
                .permitAll()
                .and()
                .logout()
                .permitAll()
                .and().csrf().disable()
                .headers().frameOptions().disable();
    }

}
