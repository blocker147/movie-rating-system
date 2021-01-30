package com.blocker.movieratingsystem.mapper;

import com.blocker.movieratingsystem.entity.Entities;
import com.blocker.movieratingsystem.entity.Rating;
import com.blocker.movieratingsystem.model.Models;
import com.blocker.movieratingsystem.model.RatingDTO;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = RatingMapper.class)
class RatingMapperTest {

  @Autowired private RatingMapper ratingMapper;

  @Test
  void shouldReturnRatingEntity() {
    RatingDTO ratingDTO = Models.getRatingDTO();

    Rating rating = ratingMapper.toEntity(ratingDTO);

    assertNull(rating.getId());
    assertEquals(ratingDTO.getMark(), rating.getMark());
  }

  @Test
  void shouldReturnRatingModel() {
    Rating rating = Entities.getRating();

    RatingDTO ratingDTO = ratingMapper.toModel(rating);

    assertEquals(rating.getId(), ratingDTO.getId());
    assertEquals(rating.getMark(), ratingDTO.getMark());
  }
}
