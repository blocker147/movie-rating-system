package com.blocker.movieratingsystem.service;

import com.blocker.movieratingsystem.entity.Movie;
import com.blocker.movieratingsystem.model.MovieDTO;

import java.util.List;

public interface MovieService {
  List<MovieDTO> findAll();

  MovieDTO save(MovieDTO movieDTO);

  MovieDTO findById(Long id);

  MovieDTO updateById(Long id, MovieDTO newMovieDTO);

  void deleteById(Long id);

  Movie checkIfExistAndFindById(Long id);
}
