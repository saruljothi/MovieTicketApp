package com.mobiquity.movieReviewApp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v2/")
public class TestController {

    @GetMapping("loggedintest/1")
    public String loggedInBehind() {
        return "Logged In behind";
    }

    @GetMapping("loggedintest/2")
    public String alsoLoggedIn() {return "also in";}

    @GetMapping("loggedin")
    public String loggedIn() {return "logged in front";}

    @GetMapping("hasRole")
    public String hasRole() {
        return "Has Role";
    }

    @GetMapping("")
    public String forAnyone() {
        return "anyone";
    }

    @GetMapping("alsoopen")
    public String alsoOpen() {
        return "open one";
    }
}
