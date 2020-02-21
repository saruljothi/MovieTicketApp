package com.mobiquity.movieReviewApp.service;

import com.mobiquity.movieReviewApp.Entity.Series;

public interface SeriesService {

  String addSeries(Series series);

  Series getSeries(String name);

}
