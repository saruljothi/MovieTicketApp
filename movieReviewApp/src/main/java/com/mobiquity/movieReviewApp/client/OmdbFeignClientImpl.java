package com.mobiquity.movieReviewApp.client;

import com.mobiquity.movieReviewApp.model.omdb.Search;
import org.springframework.stereotype.Component;

@Component
public class OmdbFeignClientImpl implements OmdbFeignClient {

  @Override
  public Search getMovies(String name, String apiKey) {
    return null;
  }
}
