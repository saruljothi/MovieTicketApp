package com.mobiquity.movieReviewApp.security;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;

@EnableWebSecurity(debug = false)
public class AuthenticationConfig extends WebSecurityConfigurerAdapter {
    private UserDetailsServiceImpl userDetailsService;

    public AuthenticationConfig(UserDetailsServiceImpl userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }


    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/v1/signUp", "/v1/activationLink",
                        "/v1/forgotPassword", "/v1/setNewPassword",
                        "/v2/", "/v2/loggedintest/").permitAll()
                .antMatchers("/v1/resetPassword", "/v2/hasRole",
                        "/v2/loggedintest/%d").hasRole("USER")
                .antMatchers("/**").authenticated()
                .and()
                .formLogin()
                .successHandler(new SimpleUrlAuthenticationSuccessHandler("http://localhost:8086/welcome/"))
                .permitAll();
    }
}
