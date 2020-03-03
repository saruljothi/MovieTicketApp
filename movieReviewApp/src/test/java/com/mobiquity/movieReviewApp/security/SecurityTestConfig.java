package com.mobiquity.movieReviewApp.security;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories(basePackages = "com.mobiquity.movieReviewApp.notarealpackage.override")
@MockBeans(value = {@MockBean(CustomUserDetails.class),
        @MockBean(CustomUserDetailsService.class)})
public class SecurityTestConfig {

}
