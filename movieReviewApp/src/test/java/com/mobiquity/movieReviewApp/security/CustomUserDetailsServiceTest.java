package com.mobiquity.movieReviewApp.security;

import com.mobiquity.movieReviewApp.domain.accountmanagement.entity.UserProfile;
import com.mobiquity.movieReviewApp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private CustomUserDetailsService service;

    @Test
    void whenUser_DoesNotExist_ExpectUsernameNotFoundException() {
        Optional<UserProfile> noUser = Optional.empty();
        Mockito.when(repository.findByEmailId(any())).thenReturn(noUser);

        assertThrows(UsernameNotFoundException.class,
                () -> service.loadUserByUsername("not a user"));
    }

    @Test
    void whenUser_Exists_ExpectCustomUserDetailsReturned() {

        String matchingPassword = "matching";
        Mockito.when(repository.findByEmailId("temp")).thenReturn(dummyEntity(matchingPassword));
        Mockito.when(repository.findByEmailId("nomatch")).thenReturn(dummyEntity("will not match"));

        assertEquals(matchingPassword,
                service.loadUserByUsername("temp").getPassword());
        assertNotEquals(matchingPassword,
                service.loadUserByUsername("nomatch").getPassword());
    }

    private Optional<UserProfile> dummyEntity(String password) {
        UserProfile entity = new UserProfile();
        entity.setEmailId("temp");
        entity.setName("exists");
        entity.setPassword(password);
        return Optional.of(entity);
    }

    @Test
    void whenUserDetailsReturned_andPasswordMatches_thenUserAuthenticated() {
        UserProfile testUserPrep;
        testUserPrep = new UserProfile();
        testUserPrep.setEmailId("test@mail.com");
        testUserPrep.setName("test user");
        testUserPrep.setStatus(true);
        testUserPrep.setPassword(BCrypt.hashpw("password", BCrypt.gensalt()));
        CustomUserDetails testUser = new CustomUserDetails(testUserPrep);

        Mockito.when(repository.findByEmailId(any())).thenReturn(Optional.of(testUserPrep));

        CustomUserDetails test = (CustomUserDetails) service.loadUserByUsername("roc");

        assertEquals(testUser, test);
    }

}