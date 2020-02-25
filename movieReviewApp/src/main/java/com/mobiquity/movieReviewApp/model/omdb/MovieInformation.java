package com.mobiquity.movieReviewApp.model.omdb;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

@Getter
@Setter
public class MovieInformation {
private String title;
private String year;
private String type;
private String poster;
}
