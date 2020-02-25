package com.mobiquity.movieReviewApp.client;

import com.mobiquity.movieReviewApp.Entity.Movie;
import com.mobiquity.movieReviewApp.model.omdb.Search;
import io.github.cdimascio.dotenv.Dotenv;
import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandler;
import java.net.http.HttpResponse.BodyHandlers;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class OmdbHttpClient {

  private final HttpClient httpClient = HttpClient.newBuilder().build();
  @Value("${urls.base.omdb}")
  private String baseUrl;
  @Value("${urls.key.name.omdb}")
  private String keyName;
  private Dotenv dotenv;

  public OmdbHttpClient(Dotenv dotenv) {
    this.dotenv = dotenv;
  }

  public HttpResponse<String> getMovieInformation(String movieName) throws IOException, InterruptedException {
    UriComponents uri = UriComponentsBuilder.newInstance().scheme("http").host(baseUrl)
        .queryParam("s", movieName).queryParam(keyName, dotenv.get("omdbKey")).build();
    HttpRequest request = HttpRequest.newBuilder().uri(uri.toUri()).build();
    return httpClient.newHttpClient().send(request, BodyHandlers.ofString());

  }

}
