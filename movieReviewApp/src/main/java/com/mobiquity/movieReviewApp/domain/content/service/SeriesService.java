package com.mobiquity.movieReviewApp.domain.content.service;

import com.mobiquity.movieReviewApp.domain.content.entity.Series;

public interface SeriesService {

  String addSeries(Series series);

  Series getSeries(String name);

}
