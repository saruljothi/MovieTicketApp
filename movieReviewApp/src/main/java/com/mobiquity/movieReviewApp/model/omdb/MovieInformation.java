package com.mobiquity.movieReviewApp.model.omdb;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

@Value
public class MovieInformation {

  @JsonProperty("Title")
  private String title;
  @JsonProperty("Year")
  private String year;
  @JsonProperty("Type")
  private String type;
  @JsonProperty("Poster")
  private String poster;
  @JsonProperty("imdbID")
  private String imdbId;
}
