package com.mobiquity.movieReviewApp.model.omdb;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
//@JsonIgnoreProperties(ignoreUnknown=true)
public class Search {

  @JsonAlias("search")
private List<MovieInformation> movieInformationList;
  private String totalResults;
  private String response;
}
