package com.mobiquity.movieReviewApp.controller;

import com.mobiquity.movieReviewApp.Entity.Movie;
import com.mobiquity.movieReviewApp.model.ResponseMovieApp;
import com.mobiquity.movieReviewApp.service.WatchlistService;
import java.util.Arrays;
import java.util.Set;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class WatchlistController {

  private WatchlistService watchlistService;

  public WatchlistController(WatchlistService watchlistService) {
    this.watchlistService = watchlistService;
  }

  @PostMapping("/addMovieToWatchlist")
  public ResponseEntity<ResponseMovieApp> addMovieToUserWatchList(
      @RequestParam(name = "userEmail") String userEmailId,
      @RequestParam(name = "movieName") String movie) {
    return new ResponseEntity<>(
        new ResponseMovieApp(
            Arrays.asList(watchlistService.addMovieToUsersWatchlist(userEmailId, movie))),
        HttpStatus.OK);
  }

  @PostMapping("/removeMovieFromWatchlist")
  public ResponseEntity<ResponseMovieApp> removeMovieToUserWatchList(
      @RequestParam(name = "userEmail") String userEmailId,
      @RequestParam(name = "movieName") String movie) {
    return new ResponseEntity<>(
        new ResponseMovieApp(
            Arrays.asList(watchlistService.removeMovieFromAUsersWatchlist(userEmailId, movie))),
        HttpStatus.OK);
  }

}
