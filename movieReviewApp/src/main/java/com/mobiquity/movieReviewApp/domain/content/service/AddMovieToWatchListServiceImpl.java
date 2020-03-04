package com.mobiquity.movieReviewApp.domain.content.service;

import com.mobiquity.movieReviewApp.client.OmdbFeignClient;
import com.mobiquity.movieReviewApp.client.omdb.Search;
import com.mobiquity.movieReviewApp.domain.accountmanagement.exception.UserException;
import com.mobiquity.movieReviewApp.domain.content.entity.Movie;
import com.mobiquity.movieReviewApp.repository.MovieRepository;
import com.mobiquity.movieReviewApp.repository.SeriesRepository;
import com.mobiquity.movieReviewApp.repository.UserRepository;
import io.github.cdimascio.dotenv.Dotenv;
import java.util.List;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AddMovieToWatchListServiceImpl implements AddMovieToWatchListService {

  private UserRepository userRepository;
  private MovieRepository movieRepository;
  private SeriesRepository seriesRepository;
  private OmdbFeignClient omdbFeignClient;
  private Dotenv dotenv;

  public AddMovieToWatchListServiceImpl(
      UserRepository userRepository,
      MovieRepository movieRepository,
      SeriesRepository seriesRepository,
      OmdbFeignClient omdbFeignClient,
      Dotenv dotenv) {
    this.userRepository = userRepository;
    this.movieRepository = movieRepository;
    this.seriesRepository = seriesRepository;
    this.omdbFeignClient = omdbFeignClient;
    this.dotenv = dotenv;
  }

  @Override
  public String addMovieToWatchList(String movieName) {
    userRepository.findByEmailId(getPrincipal());
    List<Movie> movies = movieRepository.findByName(movieName);
    if (movies.isEmpty()) {
      getMoviesFromOmdb(movieName);

    }
    return null;
  }

  private String getMoviesFromOmdb(String movieName) {
    try {
      Search search = omdbFeignClient.getMovies(movieName, dotenv.get("omdbKey"));
    } catch (NullPointerException e) {
      throw new UserException(ErrorMessages.NO_MOVIE_PRESENT.toString());
    }
    return null;
  }

  private String getPrincipal() {

    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    return authentication.getName();
  }
}
