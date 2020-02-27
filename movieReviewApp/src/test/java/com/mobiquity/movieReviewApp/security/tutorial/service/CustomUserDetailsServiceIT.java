//package com.mobiquity.movieReviewApp.security.tutorial.service;
//
//import com.mobiquity.movieReviewApp.security.tutorial.service.ResponseMessageService;
//import com.mobiquity.movieReviewApp.security.tutorial.service.TestMessageService;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
//import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
//import org.springframework.security.test.context.support.WithMockUser;
//import org.springframework.security.test.context.support.WithSecurityContextTestExecutionListener;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.TestExecutionListeners;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
//import org.springframework.test.context.transaction.TransactionalTestExecutionListener;
//import org.springframework.test.context.web.ServletTestExecutionListener;
//
//import static org.junit.jupiter.api.Assertions.assertThrows;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//
//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//@ExtendWith(SpringExtension.class)
//@ContextConfiguration
//@TestExecutionListeners(listeners = {
//        ServletTestExecutionListener.class,
//        DependencyInjectionTestExecutionListener.class,
//        TransactionalTestExecutionListener.class,
//        WithSecurityContextTestExecutionListener.class
//})
//public class CustomUserDetailsServiceIT {
//
//    private TestMessageService messageService;
//
//    @Autowired
//    public CustomUserDetailsServiceIT(ResponseMessageService messageService) {
//        this.messageService = messageService;
//    }
//
//    @Test
//    void messageGetsUnauthenticated() {
//        System.out.println(messageService.getMessage());
//        assertThrows(AuthenticationCredentialsNotFoundException.class,
//            () -> messageService.getMessage());
//    }
//
//    @Test
//    @WithMockUser
//    public void getMessageWithMockUser() {
//        String message = messageService.getMessage();
//        System.out.println(message);
//        assertTrue(message.contains("user"));
//    }
//}
