package com.mobiquity.movieReviewApp.domain.accountmanagement.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("")
public class GreetingsController {

    @GetMapping("/welcome")
    public String welcomePage(Principal principal, Authentication auth) {

        return String.format("%s%n\t%s", principal.toString(), auth.getName());
    }

}
