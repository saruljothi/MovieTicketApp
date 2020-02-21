package com.mobiquity.movieReviewApp.service;

import com.mobiquity.movieReviewApp.Entity.Series;
import com.mobiquity.movieReviewApp.repository.SeriesRepository;
import org.springframework.stereotype.Service;

@Service
public class SeriesServiceImpl implements SeriesService {

  private SeriesRepository seriesRepository;

  public SeriesServiceImpl(SeriesRepository seriesRepository) {
    this.seriesRepository = seriesRepository;
  }

  @Override
  public String addSeries(Series series) {
    Series addedSeries = seriesRepository.save(series);

    return addedSeries.getName() + "  has been added";
  }

  @Override
  public Series getSeries(String name) {
    return seriesRepository.findSeriesByName(name).orElse(null);
  }

}
