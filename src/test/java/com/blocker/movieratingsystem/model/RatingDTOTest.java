package com.blocker.movieratingsystem.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RatingDTOTest {

  @Test
  void shouldReturnRatingDTO() {
    RatingDTO ratingDTO = new RatingDTO(Models.LONG_VALUE, Models.INT_VALUE);
    RatingDTO otherRatingDTO = new RatingDTO(Models.LONG_VALUE, Models.INT_VALUE);

    assertTrue(ratingDTO.canEqual(otherRatingDTO));
    assertTrue(ratingDTO.equals(otherRatingDTO));
    assertEquals(ratingDTO, otherRatingDTO);
    assertEquals(ratingDTO.hashCode(), otherRatingDTO.hashCode());
  }
}
