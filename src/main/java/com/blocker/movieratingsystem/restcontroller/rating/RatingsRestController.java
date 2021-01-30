package com.blocker.movieratingsystem.restcontroller.rating;

import com.blocker.movieratingsystem.model.RatingDTO;
import com.blocker.movieratingsystem.service.MovieService;
import com.blocker.movieratingsystem.service.RatingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/ratings")
public class RatingsRestController {

  @Autowired private RatingService ratingService;
  @Autowired private MovieService movieService;

  @GetMapping
  public List<RatingDTO> findAll() {
    log.info("findAll Files");
    return ratingService.findAll();
  }

  @GetMapping("/{id}")
  public RatingDTO findById(@PathVariable Long id) {
    log.info("findById(id='{}')", id);
    return ratingService.findById(id);
  }

  @PostMapping("/{id}")
  public RatingDTO addRatingToMovie(@PathVariable Long id, @RequestBody RatingDTO ratingDTO) {
    log.info("addRatingToMovie(ratingDTO='{}') byMovieId(id='{}')", ratingDTO, id);
    return ratingService.addRatingToMovie(id, ratingDTO);
  }

  @PutMapping("/{id}")
  public RatingDTO updateById(@PathVariable Long id, @RequestBody RatingDTO newRatingDTO) {
    log.info("updateById(id='{}', newRatingDTO='{}')", id, newRatingDTO);
    return ratingService.updateById(id, newRatingDTO);
  }

  @DeleteMapping("/{id}")
  public void deleteById(@PathVariable Long id) {
    log.info("deleteById(id='{}')", id);
    ratingService.deleteById(id);
  }
}
