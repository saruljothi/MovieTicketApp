package com.mobiquity.movieReviewApp.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mobiquity.movieReviewApp.MovieReviewApp;
import com.mobiquity.movieReviewApp.domain.accountmanagement.entity.UserProfile;
import com.mobiquity.movieReviewApp.domain.accountmanagement.model.UserInformation;
import com.mobiquity.movieReviewApp.repository.UserRepository;
import com.mobiquity.movieReviewApp.security.resources.CustomRestTemplate;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional
public class AuthenticationIT {

    private MockMvc mockMvc;

    private UserRepository userRepository;

    @LocalServerPort
    int randomServerPort;
    private RestTemplate template;
    private String urlPrefix;
    private ResponseEntity<MovieReviewApp> response;
    private UserProfile userProfile;

    @Autowired
    public AuthenticationIT(MockMvc mockMvc, UserRepository userRepository) {
        userProfile = new UserProfile();
        this.mockMvc = mockMvc;
        this.userRepository = userRepository;
        template = new CustomRestTemplate().getRestTemplate();

        setProfile("email@email.com", "name", "pass");
    }



    @BeforeEach
    public void setUp() {
        this.urlPrefix = "http://localhost:" + randomServerPort;
        addUserToUserRepository();
    }

    private void addUserToUserRepository() {

        userRepository.save(userProfile);
    }

    void setProfile(String email, String name, String pass) {
//        UserProfile userEntity = new UserProfile();
        userProfile.setStatus(true);
        userProfile.setEmailId(email);
        userProfile.setName(name);
        userProfile.setPassword(BCrypt.hashpw(pass,BCrypt.gensalt()));
    }



    @Test
    public void checkIfUserIsAdded()
    {
        Optional<UserProfile> user = userRepository.findByEmailId("email@email.com");
        assertEquals("name",user.get().getName());
    }





    //hit public end point and get 200  YY

    //hit private end point and get redirect  YY
    //add user to db
    //hit private end point after user is authenticated and get 200

    @Test
    void publicEndpoint_unAuthorizedUser_hasAccess() throws JsonProcessingException {
        String url = urlPrefix + "/v1/signUp/";

        UserInformation info = new UserInformation();
        info.setEmailId("email1@email.com");
        info.setName("name");
        info.setPassword("pass");
        info.setPasswordConfirmation("pass");

        ObjectMapper mapper = new ObjectMapper();
        String body = mapper.writeValueAsString(info);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(new MediaType("application", "json"));
        HttpEntity<String> entity = new HttpEntity<>(body, headers);

        response = template.exchange(url, HttpMethod.POST, entity, MovieReviewApp.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void privateEndpoint_unAuthorizedUser_getsRedirected() {
        String url =  urlPrefix + "/welcome";
        response = template.exchange(
                url, HttpMethod.GET,null, MovieReviewApp.class);

        assertEquals(HttpStatus.FOUND, response.getStatusCode());
    }

    @Test
    void privateEndpoint_authorizedUser_getsRedirected() {
        String url =  urlPrefix + "/welcome";

        response = template.exchange(
                url, HttpMethod.GET,null, MovieReviewApp.class);

        assertEquals(HttpStatus.FOUND, response.getStatusCode());
    }


    @AfterEach
    void removeUserProfile() {

        userRepository.delete(userProfile);
    }

}
