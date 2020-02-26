package com.mobiquity.movieReviewApp.domain.content.service;

import com.mobiquity.movieReviewApp.domain.content.entity.Movie;
import com.mobiquity.movieReviewApp.repository.MovieRepository;
import org.springframework.stereotype.Service;

@Service
public class MovieServiceImpl implements MovieService {

  private MovieRepository movieRepository;

  public MovieServiceImpl(MovieRepository movieRepository) {
    this.movieRepository = movieRepository;
  }

  @Override
  public String addMovie(Movie movie) {
    Movie addedMovie = movieRepository.save(movie);

    return addedMovie.getName() + " has been added";
  }

  @Override
  public Movie getMovie(String name) {
    return movieRepository.findMovieByName(name).orElse(null);
  }

}
