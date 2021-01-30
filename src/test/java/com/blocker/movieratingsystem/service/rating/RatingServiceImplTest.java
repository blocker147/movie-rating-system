package com.blocker.movieratingsystem.service.rating;

import com.blocker.movieratingsystem.entity.Entities;
import com.blocker.movieratingsystem.entity.Rating;
import com.blocker.movieratingsystem.exception.RatingNotFoundException;
import com.blocker.movieratingsystem.mapper.RatingMapper;
import com.blocker.movieratingsystem.model.Models;
import com.blocker.movieratingsystem.model.RatingDTO;
import com.blocker.movieratingsystem.repository.MovieRepository;
import com.blocker.movieratingsystem.repository.RatingRepository;
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
@ContextConfiguration(classes = {RatingMapper.class, RatingServiceImpl.class})
class RatingServiceImplTest {

  @Autowired private RatingServiceImpl ratingService;
  @MockBean private RatingRepository ratingRepository;
  @MockBean private MovieRepository movieRepository;

  private static Rating RATING_WITHOUT_ID;
  private static Rating RATING_WITH_ID;
  private static final Long ID = Entities.LONG_VALUE;

  @BeforeEach
  void setUp() {
    RATING_WITHOUT_ID = Entities.getRatingWithoutId();
    RATING_WITH_ID = Entities.getRating();
  }

  @Test
  void shouldReturnListOfRatingDTOS() {
    Rating rating = RATING_WITH_ID;

    when(ratingRepository.findAll()).thenReturn(Arrays.asList(rating, rating, rating));

    List<RatingDTO> ratingDTOS = ratingService.findAll();

    assertNotNull(ratingDTOS);
    assertEquals(3, ratingDTOS.size());
    for (RatingDTO ratingDTO : ratingDTOS) {
      assertNotNull(ratingDTO.getId());
      assertEquals(rating.getMark(), ratingDTO.getMark());
    }
  }

  @Test
  void shouldFindMovieById_and_AddRatingToMovie_and_SaveRating_and_ReturnSavedRatingDTO() {
    Rating rating = RATING_WITH_ID;
    when(ratingRepository.save(RATING_WITHOUT_ID)).thenReturn(rating);
    when(movieRepository.findById(ID)).thenReturn(Optional.of(Entities.getMovie()));

    RatingDTO ratingDTO = ratingService.addRatingToMovie(ID, Models.getRatingDTO());

    assertNotNull(ratingDTO);
    assertEquals(rating.getId(), ratingDTO.getId());
    assertEquals(rating.getMark(), ratingDTO.getMark());
  }

  @Test
  void shouldFindRatingById_and_ReturnRatingDTO() {
    Rating rating = RATING_WITH_ID;
    when(ratingRepository.findById(ID)).thenReturn(Optional.of(rating));

    RatingDTO ratingDTO = ratingService.findById(ID);

    assertNotNull(ratingDTO);
    assertEquals(rating.getId(), ratingDTO.getId());
    assertEquals(rating.getMark(), ratingDTO.getMark());
  }

  @Test
  void shouldFindRatingById_and_UpdateRating_and_SaveRating_and_ReturnUpdatedRatingDTO() {
    Rating rating = RATING_WITH_ID;
    when(ratingRepository.findById(ID)).thenReturn(Optional.of(rating));
    when(ratingRepository.save(rating)).thenReturn(rating);

    RatingDTO newRatingDTO = Models.getUpdatedRatingDTO();
    RatingDTO ratingDTO = ratingService.updateById(ID, newRatingDTO);

    assertNotNull(ratingDTO);
    assertEquals(rating.getId(), rating.getId());
    assertEquals(newRatingDTO.getMark(), ratingDTO.getMark());
  }

  @Test
  void shouldFindRatingById_and_DeleteRatingById_and_ReturnVoid() {
    Rating rating = RATING_WITH_ID;
    when(ratingRepository.findById(ID)).thenReturn(Optional.of(rating));
    doNothing().when(ratingRepository).deleteById(ID);

    ratingService.deleteById(ID);

    verify(ratingRepository, times(1)).deleteById(ID);
  }

  @Test
  void shouldThrowRatingNotFoundException_onFindById() {
    String exceptionMessage = String.format("Rating with '%d' not found.", ID);
    when(ratingRepository.findById(ID)).thenThrow(new RatingNotFoundException(exceptionMessage));

    try {
      assertThrows(RatingNotFoundException.class, () -> ratingService.checkIfExistAndFindById(ID));
      ratingService.checkIfExistAndFindById(ID);
    } catch (RatingNotFoundException e) {
      assertEquals(exceptionMessage, e.getMessage());
    }
  }
}
