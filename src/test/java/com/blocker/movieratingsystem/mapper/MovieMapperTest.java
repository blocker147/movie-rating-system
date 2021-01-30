package com.blocker.movieratingsystem.mapper;

import com.blocker.movieratingsystem.entity.Entities;
import com.blocker.movieratingsystem.entity.Movie;
import com.blocker.movieratingsystem.entity.Rating;
import com.blocker.movieratingsystem.model.Models;
import com.blocker.movieratingsystem.model.MovieDTO;
import com.blocker.movieratingsystem.model.RatingDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {FileMapper.class, MovieMapper.class, RatingMapper.class})
class MovieMapperTest {

  @Autowired private MovieMapper movieMapper;

  @Test
  void shouldReturnMovieEntity() {
    MovieDTO movieDTO = Models.getMovieDTO();

    Movie movie = movieMapper.toEntity(movieDTO);

    assertNull(movie.getId());
    assertEquals(movieDTO.getTitle(), movie.getTitle());
    assertEquals(movieDTO.getDescription(), movie.getDescription());
    assertEquals(movieDTO.getGenres(), movie.getGenres());
    assertNull(movie.getFile().getId());
    assertEquals(movieDTO.getFile().getSrc(), movie.getFile().getSrc());

    for (Rating rating : movie.getRatings()) {
      assertNull(rating.getId());
      assertEquals(Models.INT_VALUE, rating.getMark());
    }
  }

  @Test
  void shouldReturnMovieModel() {
    Movie movie = Entities.getMovie();

    MovieDTO movieDTO = movieMapper.toModel(movie);

    assertEquals(movie.getId(), movieDTO.getId());
    assertEquals(movie.getTitle(), movieDTO.getTitle());
    assertEquals(movie.getDescription(), movieDTO.getDescription());
    assertEquals(movie.getGenres(), movieDTO.getGenres());
    assertEquals(movie.getFile().getId(), movieDTO.getFile().getId());
    assertEquals(movie.getFile().getSrc(), movieDTO.getFile().getSrc());

    for (RatingDTO ratingDTO : movieDTO.getRatings()) {
      assertEquals(Entities.LONG_VALUE, ratingDTO.getId());
      assertEquals(Entities.INT_VALUE, ratingDTO.getMark());
    }
  }
}
