package com.mobiquity.movieReviewApp.security.controllers;

public interface SecurityResults {

    //As soon as a code matches then the user is not authenticated
    boolean isAuthenticated(String url, int... codes) throws Exception;
    boolean isAuthenticated(String url) throws Exception;
}
