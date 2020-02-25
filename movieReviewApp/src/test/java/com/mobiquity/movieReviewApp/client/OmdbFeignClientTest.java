package com.mobiquity.movieReviewApp.client;

import com.mobiquity.movieReviewApp.model.omdb.Search;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class OmdbFeignClientTest {

  @Autowired
  OmdbFeignClient omdbFeignClient;
  @Test
  public void checkResponseFromOmdb()
  {
   Search search= omdbFeignClient.getMovies("The Social Network","53c922c5");
   // Search search= omdbFeignClient.getMovies();
    System.out.println(search.getTotalResults());
  }
}
