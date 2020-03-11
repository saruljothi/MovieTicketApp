package com.mobiquity.movieReviewApp.security.controllers;

public interface PublicEndpointRule {

    void publicEndpoint_authorizedUser_hasAccess(String url) throws Exception;
    void publicEndpoint_unAuthorizedUser_hasAccess(String url) throws Exception;
}
