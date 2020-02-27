//package com.mobiquity.movieReviewApp.security.tryingout;
//
//import com.mobiquity.movieReviewApp.security.AuthenticationConfig;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig  {
//    private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
//
//    private Environment env;
//
//    private WebSecurityConfigurerAdapter securityConfig;
//
//    public SecurityConfig(Environment env, AuthenticationConfig securityConfig) {
//
////        this.env = env;
//    //        this.securityConfig = securityConfig;
//    //
//    //        Principal testPrinc = new PrinceTest("Richard");
//    //        Authentication testAuth = new AuthTest(testPrinc);
//    //
//    //        try {
//    //            securityConfig.authenticationManagerBean().authenticate(testAuth);
//    //        } catch (Exception e) {
//    //            logger.info(e.getMessage());
//    //        }
//    }
//
////    @PostConstruct
////    public void updateClass() {
////        try {
////            securityConfig.configure(getInMemoryConfig());
////        } catch (Exception e) {
////            logger.info(e.getMessage());
////        }
////    }
//
////    private AuthenticationManagerBuilder getInMemoryConfig() {
////
////        Principal user = new UserSecurity(new AuthenticationProvider());
////        securityConfig.authenticationManagerBean().authenticate(new TestingAuthenticationToken())
////        return null;
////    }
//}
