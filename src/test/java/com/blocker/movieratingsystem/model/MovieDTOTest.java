package com.blocker.movieratingsystem.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;

class MovieDTOTest {

  @Test
  void shouldReturnMovieDTO() {
    MovieDTO movieDTO = movieDTO();
    MovieDTO otherMovieDTO = movieDTO();

    assertTrue(movieDTO.canEqual(otherMovieDTO));
    assertTrue(movieDTO.equals(otherMovieDTO));
    assertEquals(movieDTO, otherMovieDTO);
    assertEquals(movieDTO.hashCode(), otherMovieDTO.hashCode());
  }

  private MovieDTO movieDTO() {
    return new MovieDTO(
        Models.LONG_VALUE,
        Models.STRING_VALUE,
        Models.STRING_VALUE,
        Models.GENRE_VALUES,
        Models.getFileDTO(),
        new HashSet<>(),
        new BigDecimal("0"));
  }
}
