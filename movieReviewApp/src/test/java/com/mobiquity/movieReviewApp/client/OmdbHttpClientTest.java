package com.mobiquity.movieReviewApp.client;

import static org.junit.jupiter.api.Assertions.*;

import com.mobiquity.movieReviewApp.Entity.Movie;
import com.mobiquity.movieReviewApp.model.omdb.Search;
import java.io.IOException;
import java.net.http.HttpResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.http.HttpStatus;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class OmdbHttpClientTest {

  @Autowired
  OmdbHttpClient omdbHttpClient;

  @Test
  public  void checkIfResponseisOk() throws IOException, InterruptedException {
    HttpResponse<String> result = omdbHttpClient.getMovieInformation("The Social Network");
    assertEquals(200,result.statusCode());
  }
}