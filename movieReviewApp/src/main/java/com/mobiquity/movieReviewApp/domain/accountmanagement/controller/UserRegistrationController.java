package com.mobiquity.movieReviewApp.domain.accountmanagement.controller;

import com.mobiquity.movieReviewApp.domain.accountmanagement.model.ResponseMovieApp;
import com.mobiquity.movieReviewApp.domain.accountmanagement.model.UserInformation;
import com.mobiquity.movieReviewApp.domain.accountmanagement.service.SignUpService;
import com.mobiquity.movieReviewApp.domain.accountmanagement.validation.UserValidator;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@RestController
@RequestMapping("/v1/signUp")
public class UserRegistrationController {

    private SignUpService signUpService;
    private UserValidator userValidator;

    public UserRegistrationController(SignUpService signUpService,
                                      UserValidator userValidator) {
        this.signUpService = signUpService;
        this.userValidator = userValidator;
    }

    @PostMapping("/")
    public ResponseEntity<ResponseMovieApp> signUp(@RequestBody UserInformation userInformation,
                                                   BindingResult bindingResult) {
        userValidator.validate(userInformation, bindingResult);

        if (bindingResult.hasErrors()) {
            List<String> issues = new ArrayList<>();
            List<ObjectError> errors = bindingResult.getAllErrors();
            for (ObjectError error : errors) {
                issues.add(error.getCode());
            }

            return new ResponseEntity<>(new ResponseMovieApp(issues),
                    HttpStatus.FORBIDDEN);
        }

        return new ResponseEntity<>(new ResponseMovieApp(Collections.singletonList(signUpService.saveUser(userInformation))),
                HttpStatus.OK);
    }

    @GetMapping("/activationLink")
    public ResponseEntity<ResponseMovieApp> activateAccountRegistration(@RequestParam String token) {
        return new ResponseEntity<>(new ResponseMovieApp(
                Collections.singletonList(signUpService.registerAccount(token))
        ), HttpStatus.OK);
    }




}
