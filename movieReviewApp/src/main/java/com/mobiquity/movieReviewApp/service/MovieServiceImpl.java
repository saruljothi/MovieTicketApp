package com.mobiquity.movieReviewApp.service;

import com.mobiquity.movieReviewApp.Entity.Movie;
import com.mobiquity.movieReviewApp.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MovieServiceImpl implements MovieService{

  @Autowired
  MovieRepository movieRepository;

  @Override
  public String addMovie(Movie movie){

    Movie addedMovie = movieRepository.save(movie);

    return addedMovie.getName() + " has been added";
  }

}
