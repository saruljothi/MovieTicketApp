package com.mobiquity.movieReviewApp.controller;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/welcome/")
public class FrontPageController {

    @GetMapping
    public String welcomePage(Principal principal, Authentication auth) {

        return String.format("%s%n\t%s", principal.toString(), auth.getName());
    }


}
