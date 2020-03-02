package com.mobiquity.movieReviewApp.security.resources;

import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.HttpURLConnection;

public class CustomRestTemplate extends SimpleClientHttpRequestFactory {

    public RestTemplate getRestTemplate() {
        return new RestTemplate(this);
    }

    @Override
    protected void prepareConnection(HttpURLConnection connection, String httpMethod) throws IOException {
        super.prepareConnection(connection, httpMethod);
        connection.setInstanceFollowRedirects(false);
    }
}
