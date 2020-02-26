package com.mobiquity.movieReviewApp.domain.content.service;


import com.mobiquity.movieReviewApp.domain.content.entity.Movie;

public interface MovieService {

  String addMovie(Movie movie);

  Movie getMovie(String name);

}
