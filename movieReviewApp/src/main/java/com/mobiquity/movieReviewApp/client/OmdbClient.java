package com.mobiquity.movieReviewApp.client;

import io.github.cdimascio.dotenv.Dotenv;
import java.net.URI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class OmdbClient {

  @Autowired
  RestTemplate restTemplate;
  @Value("${urls.base.omdb}")
  private String uriBase;
  @Value("${urls.key.name.omdb}")
  private String keyName;
  @Autowired
  private Dotenv dotenv;

  private HttpEntity httpEntity;

  //final String uri = "http://www.omdbapi.com/?t=The Social Network&apikey=53c922c5";


  ResponseEntity<Object> getOmdbResponse(String keyName) {

    String keyValue = dotenv.get("omdbKey");
    System.out.println("**************" + keyValue);

    ResponseEntity<Object> response = restTemplate
        .exchange(buildUri(keyValue, keyName), HttpMethod.GET, httpEntity, Object.class);

    System.out.println(response);
    return response;
  }

  URI buildUri(String keyValue, String keyName) {
    UriComponents uri = UriComponentsBuilder.newInstance()
        .scheme("http").host(uriBase)
        .queryParam("t", "The Social Network")
        .queryParam(keyName, keyValue)
        .build();
    return uri.toUri();
  }
}
