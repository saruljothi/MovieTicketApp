package com.mobiquity.movieReviewApp.security.controllers;

public interface PrivateEndpointRule {

    void privateEndpoint_unauthorizedUser_isUnauthorizedResponse(String url) throws Exception;
    void privateEndpoint_authorizedUser_hasAccess(String url) throws Exception;
}
