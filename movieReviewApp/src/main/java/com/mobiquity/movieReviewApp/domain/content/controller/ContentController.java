package com.mobiquity.movieReviewApp.domain.content.controller;

import com.mobiquity.movieReviewApp.domain.content.dto.MovieDTO;
import com.mobiquity.movieReviewApp.domain.content.dto.SeriesDTO;
import com.mobiquity.movieReviewApp.domain.content.entity.Movie;
import com.mobiquity.movieReviewApp.domain.content.entity.Series;
import com.mobiquity.movieReviewApp.domain.accountmanagement.model.ResponseMovieApp;
import com.mobiquity.movieReviewApp.domain.content.service.MovieService;
import com.mobiquity.movieReviewApp.domain.content.service.SeriesService;
import java.util.Collections;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/content")
public class ContentController {

  private MovieService movieService;
  private SeriesService seriesService;
  private ModelMapper modelMapper;

  public ContentController(MovieService movieService, SeriesService seriesService, ModelMapper modelMapper) {
    this.movieService = movieService;
    this.seriesService = seriesService;
    this.modelMapper = modelMapper;
  }

  @PostMapping("/addMovie")
  public ResponseEntity<Object> addMovie(@RequestBody MovieDTO movieDTO) {
    Movie movie = convertToMovieEntity(movieDTO);
    return new ResponseEntity<>(movieService.addMovie(movie), HttpStatus.OK);
  }

  @GetMapping("/movie")
  public ResponseEntity<Object> getMovie(@RequestParam(name = "name") String name) {
    Movie movie = movieService.getMovie(name);
    MovieDTO movieDTO = convertToMovieDTO(movie);
    if(movie != null){
      return new ResponseEntity<>(movieDTO, HttpStatus.OK);
    }
    return new ResponseEntity<>(new ResponseMovieApp(Collections.singletonList("Movie not found")), HttpStatus.NOT_FOUND);
  }


  @PostMapping("/addSeries")
  public ResponseEntity<Object> addSeries(@RequestBody SeriesDTO seriesDTO) {
    Series series = convertToSeriesEntity(seriesDTO);
    return new ResponseEntity<>(new ResponseMovieApp(
        Collections.singletonList(seriesService.addSeries(series))), HttpStatus.OK);
  }

  @GetMapping("/series")
  public ResponseEntity<Object> getSeries(@RequestParam(name = "name") String name) {
    Series series = seriesService.getSeries(name);
    SeriesDTO seriesDTO = convertToSeriesDTO(series);
    if(series != null){
      return new ResponseEntity<>(seriesDTO, HttpStatus.OK);
    }
    return new ResponseEntity<>(new ResponseMovieApp(Collections.singletonList("Series not found")), HttpStatus.NOT_FOUND);
  }

  private MovieDTO convertToMovieDTO(Movie movie) {
    return modelMapper.map(movie, MovieDTO.class);
  }

  private Movie convertToMovieEntity(MovieDTO movieDTO) {
    return modelMapper.map(movieDTO, Movie.class);
  }

  private SeriesDTO convertToSeriesDTO(Series series) {
    return modelMapper.map(series, SeriesDTO.class);
  }

  private Series convertToSeriesEntity(SeriesDTO seriesDTO) {
    return modelMapper.map(seriesDTO, Series.class);
  }

}
