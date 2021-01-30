package com.blocker.movieratingsystem.controller;

import com.blocker.movieratingsystem.model.MovieDTO;
import com.blocker.movieratingsystem.model.RatingDTO;
import com.blocker.movieratingsystem.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Slf4j
@Controller
@RequestMapping("/movies")
public class MoviesController {

  @Autowired private MovieService movieService;

  @GetMapping
  public String movies(Model model) {
    log.info("movies page");
    model.addAttribute("movies", movieService.findAll());
    model.addAttribute("movie", new MovieDTO());

    System.out.println(movieService.findById(1L).getRatings().stream().mapToInt(r -> r.getMark()).average().getAsDouble());
    ;
    return "movies";
  }
}
