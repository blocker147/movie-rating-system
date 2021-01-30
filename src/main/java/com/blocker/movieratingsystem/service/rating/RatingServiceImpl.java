package com.blocker.movieratingsystem.service.rating;

import com.blocker.movieratingsystem.entity.Movie;
import com.blocker.movieratingsystem.entity.Rating;
import com.blocker.movieratingsystem.exception.MovieNotFoundException;
import com.blocker.movieratingsystem.exception.RatingNotFoundException;
import com.blocker.movieratingsystem.mapper.RatingMapper;
import com.blocker.movieratingsystem.model.RatingDTO;
import com.blocker.movieratingsystem.repository.MovieRepository;
import com.blocker.movieratingsystem.repository.RatingRepository;
import com.blocker.movieratingsystem.service.RatingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class RatingServiceImpl implements RatingService {

  @Autowired private RatingMapper ratingMapper;
  @Autowired private RatingRepository ratingRepository;
  @Autowired private MovieRepository movieRepository;

  @Override
  public List<RatingDTO> findAll() {
    log.info("findAll Ratings");
    return ratingRepository.findAll().stream()
        .map(rating -> ratingMapper.toModel(rating))
        .collect(Collectors.toList());
  }

  @Override
  public RatingDTO addRatingToMovie(Long movieId, RatingDTO ratingDTO) {
    log.info("addRatingToMovie(ratingDTO='{}') byMovieId(id='{}')", ratingDTO, movieId);
    Movie foundMovie =
        movieRepository
            .findById(movieId)
            .orElseThrow(
                () ->
                    new MovieNotFoundException(
                        String.format("Movie with '%d' id not found.", movieId)));
    Rating rating = ratingMapper.toEntity(ratingDTO);
    rating.setMovie(foundMovie);
    Rating savedRating = ratingRepository.save(rating);
    return ratingMapper.toModel(savedRating);
  }

  @Override
  public RatingDTO findById(Long id) {
    log.info("findById(id='{}')", id);
    Rating foundRating = checkIfExistAndFindById(id);
    return ratingMapper.toModel(foundRating);
  }

  @Override
  public RatingDTO updateById(Long id, RatingDTO newRatingDTO) {
    log.info("updateById(id='{}', newRatingDTO='{}')", id, newRatingDTO);
    Rating foundRating = checkIfExistAndFindById(id);
    foundRating.setMark(newRatingDTO.getMark());
    Rating savedRating = ratingRepository.save(foundRating);
    return ratingMapper.toModel(savedRating);
  }

  @Override
  public void deleteById(Long id) {
    log.info("deleteById(id='{}')", id);
    checkIfExistAndFindById(id);
    ratingRepository.deleteById(id);
  }

  @Override
  public Rating checkIfExistAndFindById(Long id) {
    log.info("checkIfExistAndFindById(id='{}')", id);
    return ratingRepository
        .findById(id)
        .orElseThrow(
            () -> new RatingNotFoundException(String.format("Rating with id '%d' not found.", id)));
  }
}
