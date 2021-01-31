package com.blocker.movieratingsystem.controller;

import com.blocker.movieratingsystem.entity.Genre;
import com.blocker.movieratingsystem.model.Models;
import com.blocker.movieratingsystem.model.MovieDTO;
import com.blocker.movieratingsystem.service.FileService;
import com.blocker.movieratingsystem.service.MovieService;
import com.blocker.movieratingsystem.service.RatingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {MoviesController.class})
class MoviesControllerTest {

  @Autowired private MoviesController moviesController;
  @MockBean private FileService fileService;
  @MockBean private MovieService movieService;
  @MockBean private RatingService ratingService;
  @MockBean private Model model;
  @MockBean private MultipartFile file;

  private static final MovieDTO MOVIE_DTO = Models.getMovieDTO();
  private static final Long ID = Models.LONG_VALUE;

  @Test
  void shouldReturnString_on_findAll() {
    when(movieService.findAll()).thenReturn(Arrays.asList(MOVIE_DTO, MOVIE_DTO));
    when(model.addAttribute("movies", movieService.findAll())).thenReturn(model);

    String view = moviesController.findAll(model);

    assertEquals("movies", view);
  }

  @Test
  void shouldReturnString_on_findById() {
    when(movieService.findById(ID)).thenReturn(MOVIE_DTO);
    when(model.addAttribute("movie", movieService.findById(ID))).thenReturn(model);
    when(model.addAttribute("newRatingDTO", Models.getRatingDTO())).thenReturn(model);
    when(model.addAttribute("avg", movieService.findById(ID).getAverage().doubleValue()))
        .thenReturn(model);

    String view = moviesController.findById(ID, model);

    assertEquals("movie", view);
  }

  @Test
  void shouldReturnString_on_createMovie() {
    when(model.addAttribute("newMovie", MOVIE_DTO)).thenReturn(model);
    when(model.addAttribute("genres", Genre.values())).thenReturn(model);

    String view = moviesController.createMovie(model);

    assertEquals("create-movie", view);
  }

  @Test
  void shouldReturnString_on_saveMovie() {
    MovieDTO movieDTO = new MovieDTO();
    movieDTO.setId(ID);
    try {
      when(movieService.save(MOVIE_DTO)).thenReturn(movieDTO);
      when(file.getBytes()).thenThrow(new IOException("null"));
      String view = moviesController.saveMovie(MOVIE_DTO, file);
      assertEquals("redirect:/movies/" + movieDTO.getId(), view);
    } catch (IOException exception) {
      assertEquals("null", exception.getMessage());
    }
  }

  @Test
  void shouldReturnString_on_editMovie() {
    when(movieService.findById(ID)).thenReturn(MOVIE_DTO);
    when(model.addAttribute("movie", movieService.findById(ID))).thenReturn(model);
    when(model.addAttribute("checkedGenres", new ArrayList<>())).thenReturn(model);
    when(model.addAttribute("genres", Genre.values())).thenReturn(model);

    String view = moviesController.editMovie(ID, model);

    assertEquals("edit-movie", view);
  }

  @Test
  void shouldReturnString_on_updateMovie() {
    MovieDTO updatedMovieDTO = Models.getUpdatedMovieDTO();
    updatedMovieDTO.setId(ID);
    when(movieService.updateById(ID, updatedMovieDTO)).thenReturn(updatedMovieDTO);

    String view = moviesController.updateMovie(ID, updatedMovieDTO);

    assertEquals("redirect:/movies/" + updatedMovieDTO.getId(), view);
  }

  @Test
  void shouldReturnString_on_voteForMovie() {
    when(ratingService.addRatingToMovie(ID, Models.getRatingDTO()))
        .thenReturn(Models.getRatingDTO());

    String view = moviesController.voteForMovie(ID, Models.INT_VALUE);

    assertEquals("redirect:/movies/" + ID, view);
  }

  @Test
  void shouldReturnString_on_deleteMovie() {
    doNothing().when(movieService).deleteById(ID);

    String view = moviesController.deleteMovie(ID);

    assertEquals("redirect:/movies", view);
    verify(movieService, times(1)).deleteById(ID);
  }

  @Test
  void shouldReturnString_on_editFile() {
    when(movieService.findById(ID)).thenReturn(MOVIE_DTO);
    when(model.addAttribute("movie", movieService.findById(ID))).thenReturn(model);

    String view = moviesController.editFile(ID, model);

    assertEquals("edit-file", view);
  }

  @Test
  void shouldReturnString_on_updateFile() {
    try {
      when(fileService.addFileToMovie(ID, Models.getFileDTO())).thenReturn(Models.getFileDTO());
      when(file.getBytes()).thenThrow(new IOException("null"));
      String view = moviesController.updateFile(ID, file);

      assertEquals("redirect:/movies/" + ID, view);
    } catch (IOException exception) {
      assertEquals("null", exception.getMessage());
    }
  }
}
