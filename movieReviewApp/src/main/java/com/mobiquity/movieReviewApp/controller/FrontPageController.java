package com.mobiquity.movieReviewApp.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/welcome/")
public class FrontPageController {

    @GetMapping
    public String welcomePage(Principal principal) {
        return principal.toString();
//        return SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
//        return "Welcome funny user person {enter user here}";
    }


}
