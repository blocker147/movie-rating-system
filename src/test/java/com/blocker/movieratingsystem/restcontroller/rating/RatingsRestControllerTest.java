package com.blocker.movieratingsystem.restcontroller.rating;

import com.blocker.movieratingsystem.model.RatingDTO;
import com.blocker.movieratingsystem.model.Models;
import com.blocker.movieratingsystem.service.MovieService;
import com.blocker.movieratingsystem.service.RatingService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {RatingsRestController.class})
class RatingsRestControllerTest {

  @Autowired private RatingsRestController ratingsRestController;
  @MockBean private RatingService ratingService;
  @MockBean private MovieService movieService;

  private static final RatingDTO RATING_DTO = Models.getRatingDTO();
  private static final Long ID = Models.LONG_VALUE;

  @Test
  void shouldReturnRatingDTOList_on_findAll() {
    when(ratingService.findAll()).thenReturn(Arrays.asList(RATING_DTO, RATING_DTO));

    List<RatingDTO> ratingDTO = ratingsRestController.findAll();

    assertNotNull(ratingDTO);
    assertEquals(2, ratingDTO.size());
  }

  @Test
  void shouldReturnRatingDTO_on_findById() {
    when(ratingService.findById(ID)).thenReturn(RATING_DTO);

    RatingDTO ratingDTO = ratingsRestController.findById(ID);

    assertNotNull(ratingDTO);
    assertEquals(RATING_DTO.getMark(), ratingDTO.getMark());
  }

  @Test
  void shouldAddRatingToMovie_on_addRatingToMovie() {
    when(ratingService.addRatingToMovie(ID, RATING_DTO)).thenReturn(RATING_DTO);

    RatingDTO ratingDTO = ratingsRestController.addRatingToMovie(ID, RATING_DTO);

    assertNotNull(ratingDTO);
    assertEquals(RATING_DTO.getMark(), ratingDTO.getMark());
  }

  @Test
  void shouldUpdateRating_on_updateById() {
    RatingDTO updatedRatingDTO = Models.getUpdatedRatingDTO();
    when(ratingService.updateById(ID, updatedRatingDTO)).thenReturn(updatedRatingDTO);

    RatingDTO ratingDTO = ratingsRestController.updateById(ID, updatedRatingDTO);

    assertNotNull(ratingDTO);
    assertEquals(updatedRatingDTO.getMark(), ratingDTO.getMark());
  }

  @Test
  void shouldDeleteFile_on_deleteById() {
    doNothing().when(ratingService).deleteById(ID);

    ratingsRestController.deleteById(ID);

    verify(ratingService, times(1)).deleteById(ID);
  }
}
