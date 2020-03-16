package com.mobiquity.movieReviewApp.domain.content.service;

import com.mobiquity.movieReviewApp.domain.content.entity.Movie;
import com.mobiquity.movieReviewApp.repository.MovieRepository;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class MovieServiceImpl implements MovieService {

  private MovieRepository movieRepository;
  private MessageSource messageSource;

  public MovieServiceImpl(MovieRepository movieRepository,
      MessageSource messageSource) {
    this.movieRepository = movieRepository;
    this.messageSource = messageSource;
  }

  @Override
  public String addMovie(Movie movie) {
    Movie addedMovie = movieRepository.save(movie);

    return addedMovie.getName()+" "+messageSource.getMessage("add.success",null,
        LocaleContextHolder.getLocale());
  }

  @Override
  public Movie getMovie(String name) {
    return movieRepository.findMovieByName(name).orElse(null);
  }

}
