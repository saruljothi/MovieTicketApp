package com.mobiquity.movieReviewApp.client;

import com.mobiquity.movieReviewApp.model.omdb.Search;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;


@FeignClient(name="movies" , url="http://www.omdbapi.com")
public interface OmdbFeignClient {

  @RequestMapping(method = RequestMethod.GET,consumes = MediaType.APPLICATION_JSON_VALUE)
 Search getMovies(@RequestParam(name="s") String Name, @RequestParam(name="apikey") String key);


}
