package com.blocker.movieratingsystem.model;

import com.blocker.movieratingsystem.entity.Genre;

import java.util.Collections;

public final class Models {

  public static final int INT_VALUE = 1;
  public static final int UPDATED_INT_VALUE = 5;
  public static final String STRING_VALUE = "string";
  public static final String UPDATED_STRING_VALUE = "new string";
  public static final Genre[] GENRE_VALUES = new Genre[] {Genre.COMEDY, Genre.HORROR};
  public static final Genre[] UPDATED_GENRE_VALUES = new Genre[] {Genre.COMEDY};

  public static FileDTO getFileDTO() {
    FileDTO fileDTO = new FileDTO();
    fileDTO.setSrc(STRING_VALUE);
    return fileDTO;
  }

  public static FileDTO getUpdatedFileDTO() {
    FileDTO fileDTO = new FileDTO();
    fileDTO.setSrc(UPDATED_STRING_VALUE);
    return fileDTO;
  }

  public static RatingDTO getRatingDTO() {
    RatingDTO ratingDTO = new RatingDTO();
    ratingDTO.setMark(INT_VALUE);
    return ratingDTO;
  }

  public static RatingDTO getUpdatedRatingDTO() {
    RatingDTO ratingDTO = new RatingDTO();
    ratingDTO.setMark(UPDATED_INT_VALUE);
    return ratingDTO;
  }

  public static MovieDTO getMovieDTO() {
    MovieDTO movieDTO = new MovieDTO();
    movieDTO.setTitle(STRING_VALUE);
    movieDTO.setDescription(STRING_VALUE);
    movieDTO.setGenres(GENRE_VALUES);
    movieDTO.setFile(getFileDTO());
    movieDTO.setRatings(Collections.singleton(getRatingDTO()));
    return movieDTO;
  }

  public static MovieDTO getUpdatedMovieDTO() {
    MovieDTO movieDTO = new MovieDTO();
    movieDTO.setTitle(UPDATED_STRING_VALUE);
    movieDTO.setDescription(UPDATED_STRING_VALUE);
    movieDTO.setGenres(UPDATED_GENRE_VALUES);
    movieDTO.setFile(getUpdatedFileDTO());
    movieDTO.setRatings(Collections.singleton(getUpdatedRatingDTO()));
    return movieDTO;
  }
}
