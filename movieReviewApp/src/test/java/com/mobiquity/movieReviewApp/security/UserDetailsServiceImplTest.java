package com.mobiquity.movieReviewApp.security;

import com.mobiquity.movieReviewApp.Entity.UserProfile;
import com.mobiquity.movieReviewApp.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.swing.text.html.Option;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

//2) create tests for an in memory db (requires set up of h2 and then running tests against that)
@ExtendWith(MockitoExtension.class)
class UserDetailsServiceImplTest {

    @Mock
    private UserRepository repository;

    @InjectMocks
    private UserDetailsServiceImpl service;

    @Test
    void whenUser_DoesNotExist_ExpectUsernameNotFoundException() {
        Optional<UserProfile> noUser = Optional.empty();
        Mockito.when(repository.findByEmailId(any())).thenReturn(noUser);

        assertThrows(UsernameNotFoundException.class,
                () -> service.loadUserByUsername("not a user"));
    }

    @Test
    void whenUser_Exists_ExpectUserSecurityReturned() {

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

}