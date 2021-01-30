package com.blocker.movieratingsystem.service;

import com.blocker.movieratingsystem.entity.Rating;
import com.blocker.movieratingsystem.model.RatingDTO;

import java.util.List;

public interface RatingService {
  List<RatingDTO> findAll();

  RatingDTO addRatingToMovie(Long movieId, RatingDTO ratingDTO);

  RatingDTO findById(Long id);

  RatingDTO updateById(Long id, RatingDTO newRatingDTO);

  void deleteById(Long id);

  Rating checkIfExistAndFindById(Long id);
}
