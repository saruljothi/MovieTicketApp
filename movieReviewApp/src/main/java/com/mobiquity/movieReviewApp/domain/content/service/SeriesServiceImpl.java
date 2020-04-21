package com.mobiquity.movieReviewApp.domain.content.service;

import com.mobiquity.movieReviewApp.domain.content.entity.Series;
import com.mobiquity.movieReviewApp.repository.SeriesRepository;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Service
public class SeriesServiceImpl implements SeriesService {

  private SeriesRepository seriesRepository;
  private MessageSource messageSource;

  public SeriesServiceImpl(SeriesRepository seriesRepository,
      MessageSource messageSource) {
    this.seriesRepository = seriesRepository;
    this.messageSource = messageSource;
  }

  @Override
  public String addSeries(Series series) {
    Series addedSeries = seriesRepository.save(series);

    return addedSeries.getName()+" "+  messageSource.getMessage("add.success",null,
        LocaleContextHolder.getLocale());
  }

  @Override
  public Series getSeries(String name) {
    return seriesRepository.findSeriesByName(name).orElse(null);
  }

}
