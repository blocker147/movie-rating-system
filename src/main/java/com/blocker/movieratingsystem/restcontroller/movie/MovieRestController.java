package com.blocker.movieratingsystem.restcontroller.movie;

import com.blocker.movieratingsystem.model.MovieDTO;
import com.blocker.movieratingsystem.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/movies")
public class MovieRestController {

  @Autowired private MovieService movieService;

  @GetMapping
  public List<MovieDTO> findAll() {
    log.info("findAll Movies");
    return movieService.findAll();
  }

  @GetMapping("/{id}")
  public MovieDTO findById(@PathVariable Long id) {
    log.info("findById(id='{}')", id);
    return movieService.findById(id);
  }

  @PostMapping
  public MovieDTO save(@RequestBody MovieDTO movieDTO) {
    log.info("save(movieDTO='{}')", movieDTO);
    return movieService.save(movieDTO);
  }

  @PutMapping("/{id}")
  public MovieDTO updateById(@PathVariable Long id, @RequestBody MovieDTO newMovieDTO) {
    log.info("updateById(id='{}', newMovieDTO='{}')", id, newMovieDTO);
    return movieService.updateById(id, newMovieDTO);
  }

  @DeleteMapping("/{id}")
  public void deleteById(@PathVariable Long id) {
    log.info("deleteById(id='{}')", id);
    movieService.deleteById(id);
  }
}
