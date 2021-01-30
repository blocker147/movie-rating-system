package com.blocker.movieratingsystem.mapper;

import com.blocker.movieratingsystem.entity.Rating;
import com.blocker.movieratingsystem.model.RatingDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RatingMapper {

  public Rating toEntity(RatingDTO ratingDTO) {
    log.info("Map ratingDTO='{}' model to entity", ratingDTO);
    Rating rating = new Rating();
    rating.setMark(ratingDTO.getMark());
    log.info("Mapped rating='{}'", rating);
    return rating;
  }

  public RatingDTO toModel(Rating rating) {
    log.info("Map rating='{}' entity to model", rating);
    RatingDTO ratingDTO = new RatingDTO();
    ratingDTO.setId(rating.getId());
    ratingDTO.setMark(rating.getMark());
    log.info("Mapped ratingDTO='{}'", ratingDTO);
    return ratingDTO;
  }
}
