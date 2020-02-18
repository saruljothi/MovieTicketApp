package com.mobiquity.movieReviewApp.service;

import com.mobiquity.movieReviewApp.Entity.Movie;
import java.util.Set;

public interface WatchlistService {

  String addMovieToUsersWatchlist(String userEmailId, Movie movie);

  String removeMovieFromAUsersWatchlist(String userEmailId, Movie movie);

  Set<Movie> getUserWatchlist(String userEmailId);

}
