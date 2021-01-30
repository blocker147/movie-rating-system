package com.blocker.movieratingsystem.entity;

import java.util.Collections;

public final class Entities {

  public static final Long LONG_VALUE = 1L;
  public static final int INT_VALUE = 1;
  public static final String STRING_VALUE = "string";
  public static final Genre[] GENRE_VALUES = new Genre[] {Genre.COMEDY, Genre.HORROR};

  public static File getFile() {
    File file = new File();
    file.setId(LONG_VALUE);
    file.setSrc(STRING_VALUE);
    return file;
  }

  public static File getFileWithoutId() {
    File file = new File();
    file.setSrc(STRING_VALUE);
    return file;
  }

  public static Rating getRating() {
    Rating rating = new Rating();
    rating.setId(LONG_VALUE);
    rating.setMark(INT_VALUE);
    return rating;
  }

  public static Rating getRatingWithoutId() {
    Rating rating = new Rating();
    rating.setMark(INT_VALUE);
    return rating;
  }

  public static Movie getMovie() {
    Movie movie = new Movie();
    movie.setId(LONG_VALUE);
    movie.setTitle(STRING_VALUE);
    movie.setDescription(STRING_VALUE);
    movie.setGenres(GENRE_VALUES);
    movie.setFile(getFile());
    movie.setRatings(Collections.singleton(getRating()));
    return movie;
  }

  public static Movie getMovieWithoutId() {
    Movie movie = new Movie();
    movie.setTitle(STRING_VALUE);
    movie.setDescription(STRING_VALUE);
    movie.setGenres(GENRE_VALUES);
    movie.setFile(getFile());
    movie.setRatings(Collections.singleton(getRating()));
    return movie;
  }
}
