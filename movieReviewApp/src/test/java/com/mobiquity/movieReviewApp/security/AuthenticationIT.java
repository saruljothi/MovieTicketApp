package com.mobiquity.movieReviewApp.security;


import com.mobiquity.movieReviewApp.domain.accountmanagement.controller.GreetingsController;
import com.mobiquity.movieReviewApp.domain.accountmanagement.controller.PasswordController;
import com.mobiquity.movieReviewApp.domain.accountmanagement.controller.UserRegistrationController;
import com.mobiquity.movieReviewApp.domain.accountmanagement.entity.UserProfile;
import com.mobiquity.movieReviewApp.domain.accountmanagement.service.PasswordManagementService;
import com.mobiquity.movieReviewApp.domain.accountmanagement.service.SignUpService;
import com.mobiquity.movieReviewApp.domain.accountmanagement.validation.UserValidator;
import com.mobiquity.movieReviewApp.domain.content.controller.ContentController;
import com.mobiquity.movieReviewApp.domain.content.controller.WatchlistController;
import com.mobiquity.movieReviewApp.repository.UserRepository;
import io.swagger.annotations.Authorization;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockBeans;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.persistence.EntityManagerFactory;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
//@SpringBootTest
@EnableWebSecurity
@WebMvcTest(controllers = {
        GreetingsController.class,
        PasswordController.class,
        UserRegistrationController.class,
})
@MockBeans({
        @MockBean(PasswordManagementService.class),
        @MockBean(SignUpService.class),
        @MockBean(UserValidator.class),
        @MockBean(CustomUserDetailsService.class)
})
public class AuthenticationIT {

    private MockMvc mockMvc;
    private PasswordManagementService passwordService;
    private SignUpService signUpService;
    private UserValidator userValidator;

    @Autowired
    public AuthenticationIT(MockMvc mockMvc,
                            PasswordManagementService passwordService,
                            SignUpService signUpService,
                            UserValidator userValidator) {
        this.mockMvc = mockMvc;
        this.passwordService = passwordService;
        this.signUpService = signUpService;
        this.userValidator = userValidator;
    }

    @Test
    void unauthorisedUser_publicDomain_success() throws Exception {
        System.out.println("Stop");
//        mockMvc.perform(get("/welcome"))
//                .andExpect(status().isForbidden());
    }

}
