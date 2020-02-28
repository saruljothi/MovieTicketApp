package com.mobiquity.movieReviewApp.security.controllers;

public interface PrivateEndpointRule {

//    boolean privateEndpoint_unauthorizedUser_is302(String url) throws Exception;
    void privateEndpoint_unauthorizedUser_isUnauthorizedResponse(String url) throws Exception;

//    boolean privateEndpoint_authorizedUser_isNot401_or302(String url) throws Exception;
    void privateEndpoint_authorizedUser_hasAccess(String url) throws Exception;
}
