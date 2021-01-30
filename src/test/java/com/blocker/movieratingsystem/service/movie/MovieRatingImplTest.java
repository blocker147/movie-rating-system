package com.blocker.movieratingsystem.service.movie;

import com.blocker.movieratingsystem.entity.Entities;
import com.blocker.movieratingsystem.entity.Movie;
import com.blocker.movieratingsystem.exception.MovieNotFoundException;
import com.blocker.movieratingsystem.mapper.FileMapper;
import com.blocker.movieratingsystem.mapper.MovieMapper;
import com.blocker.movieratingsystem.mapper.RatingMapper;
import com.blocker.movieratingsystem.model.Models;
import com.blocker.movieratingsystem.model.MovieDTO;
import com.blocker.movieratingsystem.model.RatingDTO;
import com.blocker.movieratingsystem.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(
    classes = {FileMapper.class, RatingMapper.class, MovieMapper.class, MovieServiceImpl.class})
class MovieRatingImplTest {

  @Autowired private MovieServiceImpl movieService;
  @MockBean private MovieRepository movieRepository;

  private static Movie MOVIE_WITHOUT_ID;
  private static Movie MOVIE_WITH_ID;
  private static final Long ID = Entities.LONG_VALUE;

  @BeforeEach
  void setUp() {
    MOVIE_WITHOUT_ID = Entities.getMovieWithoutId();
    MOVIE_WITH_ID = Entities.getMovie();
  }

  @Test
  void shouldReturnListOfMovieDTOS() {
    Movie movie = MOVIE_WITH_ID;

    when(movieRepository.findAll()).thenReturn(Arrays.asList(movie, movie, movie));

    List<MovieDTO> movieDTOS = movieService.findAll();

    assertNotNull(movieDTOS);
    assertEquals(3, movieDTOS.size());
    for (MovieDTO movieDTO : movieDTOS) {
      assertNotNull(movieDTO.getId());
      assertEquals(movie.getTitle(), movieDTO.getTitle());
      assertEquals(movie.getDescription(), movieDTO.getDescription());
      assertEquals(movie.getGenres(), movieDTO.getGenres());
    }
  }

  @Test
  void shouldSaveMovie_and_ReturnSavedMovieDTO() {
    Movie movie = MOVIE_WITH_ID;
    when(movieRepository.save(MOVIE_WITHOUT_ID)).thenReturn(movie);

    MovieDTO movieDTO = movieService.save(Models.getMovieDTO());

    assertNotNull(movieDTO);
    assertEquals(movie.getId(), movieDTO.getId());
    assertEquals(movie.getTitle(), movieDTO.getTitle());
    assertEquals(movie.getDescription(), movieDTO.getDescription());
    assertEquals(movie.getGenres(), movieDTO.getGenres());

    assertNotNull(movieDTO.getFile());
    assertNotNull(movieDTO.getFile().getId());
    assertEquals(movie.getFile().getId(), movieDTO.getFile().getId());
    assertEquals(movie.getFile().getSrc(), movieDTO.getFile().getSrc());

    assertNotNull(movieDTO.getRatings());
    assertEquals(movie.getRatings().size(), movieDTO.getRatings().size());
    for (RatingDTO ratingDTO : movieDTO.getRatings()) {
      assertNotNull(ratingDTO.getId());
      assertEquals(Models.INT_VALUE, ratingDTO.getMark());
    }
  }

  @Test
  void shouldFindMovieById_and_ReturnMovieDTO() {
    Movie movie = MOVIE_WITH_ID;
    when(movieRepository.findById(ID)).thenReturn(Optional.of(movie));

    MovieDTO movieDTO = movieService.findById(ID);

    assertNotNull(movieDTO);
    assertEquals(movie.getId(), movieDTO.getId());
    assertEquals(movie.getTitle(), movieDTO.getTitle());
    assertEquals(movie.getDescription(), movieDTO.getDescription());
    assertEquals(movie.getGenres(), movieDTO.getGenres());

    assertNotNull(movieDTO.getFile());
    assertNotNull(movieDTO.getFile().getId());
    assertEquals(movie.getFile().getId(), movieDTO.getFile().getId());
    assertEquals(movie.getFile().getSrc(), movieDTO.getFile().getSrc());

    assertNotNull(movieDTO.getRatings());
    assertEquals(movie.getRatings().size(), movieDTO.getRatings().size());
    for (RatingDTO ratingDTO : movieDTO.getRatings()) {
      assertNotNull(ratingDTO.getId());
      assertEquals(Models.INT_VALUE, ratingDTO.getMark());
    }
  }

  @Test
  void shouldFindMovieById_and_UpdateMovie_and_SaveMovie_and_ReturnUpdatedMovieDTO() {
    Movie movie = MOVIE_WITH_ID;
    when(movieRepository.findById(ID)).thenReturn(Optional.of(movie));
    when(movieRepository.save(movie)).thenReturn(movie);

    MovieDTO newMovieDTO = Models.getUpdatedMovieDTO();
    MovieDTO movieDTO = movieService.updateById(ID, newMovieDTO);

    assertNotNull(movieDTO);
    assertEquals(movie.getId(), movieDTO.getId());
    assertEquals(newMovieDTO.getTitle(), movieDTO.getTitle());
    assertEquals(newMovieDTO.getDescription(), movieDTO.getDescription());
    assertEquals(newMovieDTO.getGenres(), movieDTO.getGenres());

    assertNotNull(movieDTO.getFile());
    assertNotNull(movieDTO.getFile().getId());

    assertNotNull(movieDTO.getRatings());
    assertEquals(movie.getRatings().size(), movieDTO.getRatings().size());
    for (RatingDTO ratingDTO : movieDTO.getRatings()) {
      assertNotNull(ratingDTO.getId());
    }
  }

  @Test
  void shouldFindMovieById_and_DeleteMovieById_and_ReturnVoid() {
    Movie movie = MOVIE_WITH_ID;
    when(movieRepository.findById(ID)).thenReturn(Optional.of(movie));
    doNothing().when(movieRepository).deleteById(ID);

    movieService.deleteById(ID);

    verify(movieRepository, times(1)).deleteById(ID);
  }

  @Test
  void shouldThrowMovieNotFoundException_onFindById() {
    String exceptionMessage = String.format("Movie with '%d' not found.", ID);
    when(movieRepository.findById(ID)).thenThrow(new MovieNotFoundException(exceptionMessage));

    try {
      assertThrows(MovieNotFoundException.class, () -> movieService.checkIfExistAndFindById(ID));
      movieService.checkIfExistAndFindById(ID);
    } catch (MovieNotFoundException e) {
      assertEquals(exceptionMessage, e.getMessage());
    }
  }
}
