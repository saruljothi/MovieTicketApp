package com.mobiquity.movieReviewApp.client;

import com.mobiquity.movieReviewApp.model.omdb.Search;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name="movies")
public interface OmdbFeignClient {

  @RequestMapping(method = RequestMethod.GET ,value = "www.omdbapi.com/s={movieName}&apikey={apiKey}" )
 Search getMovies(@RequestParam("movieName") String name,@RequestParam("apiKey") String apiKey);


}
