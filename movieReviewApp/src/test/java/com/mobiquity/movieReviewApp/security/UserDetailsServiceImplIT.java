package com.mobiquity.movieReviewApp.security;

import com.mobiquity.movieReviewApp.domain.accountmanagement.entity.UserProfile;
import com.mobiquity.movieReviewApp.domain.accountmanagement.model.UserInformation;
import com.mobiquity.movieReviewApp.repository.UserRepository;
import com.mobiquity.movieReviewApp.domain.accountmanagement.service.SignUpServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.transaction.Transactional;

@ContextConfiguration(classes = {AuthenticationConfig.class,
        SecurityConfig.class})
@WebAppConfiguration
//@ContextConfiguration(classes = AuthenticationConfig.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
//@DirtiesContext(classMode = ClassMode.BEFORE_EACH_TEST_METHOD)
//@Transactional
//@TestPropertySource(locations = "classpath:test.properties")
public class UserDetailsServiceImplIT {

//    private UserRepository db;
//    private SignUpServiceImpl signUpService;
    private UserProfile testUser;

//    @Autowired
//    public UserDetailsServiceImplIT(AuthenticationConfig config, UserRepository db, SignUpServiceImpl signUpService) {
//        this.db = db;
//        this.signUpService = signUpService;
//        testUser = setUpUser();
//
//    }

//    private UserProfile setUpUser() {
//        UserInformation user = new UserInformation();
//        user.setName("user1");
//        user.setEmailId("user1@email.com");
//        user.setPassword(BCrypt.hashpw("password", BCrypt.gensalt()));
//        return signUpService.setUserProfile(user);
//    }


//    @BeforeEach
//    void setUp() {
//        db.save(testUser);
//    }

//    @Test
//    void userExistsInDb() {
//        db.findByEmailId("user1@email.com");
//    }

    @Test
    void userAuthenticated() {
        System.out.println("Stop");
    }

    @Test
    void unauthorisedUser_publicDomain_success() {
        System.out.println("Stop");

    }

    @Test
    void unauthorisedUser_privateDomain_redirectedToLogin() {

    }

    @Test
    void unauthorisedUser_signIn_incorrectDetails_throwsError() {

    }

    @Test
    void unauthorisedUser_signIn_correctDetails_success() {

    }

    @Test
    void unauthorisedUser_securedMethod_failure_exceptionThrown() {

    }

    @Test
    void unauthorisedUser_logout_failure() {

    }

    @Test
    void authorisedUser_publicDomain_success() {

    }

    @Test
    void authorisedUser_privateDomain_success() {

    }

    @Test
    void authorisedUser_privateMethod_success() {

    }

    @Test
    void authorisedUser_logOut_success_and_redirected() {

    }

    @Test
    void loggedOutUser_accessPublic_success() {

    }

    @Test
    void loggedOutUser_accessPrivate_redirectToLogin() {

    }

    @AfterEach
    void tearDown() {

    }

}
