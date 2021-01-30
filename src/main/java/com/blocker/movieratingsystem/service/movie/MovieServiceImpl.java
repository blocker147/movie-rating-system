package com.blocker.movieratingsystem.service.movie;

import com.blocker.movieratingsystem.entity.Movie;
import com.blocker.movieratingsystem.exception.MovieNotFoundException;
import com.blocker.movieratingsystem.mapper.MovieMapper;
import com.blocker.movieratingsystem.model.MovieDTO;
import com.blocker.movieratingsystem.repository.MovieRepository;
import com.blocker.movieratingsystem.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class MovieServiceImpl implements MovieService {

  @Autowired private MovieMapper movieMapper;
  @Autowired private MovieRepository movieRepository;

  @Override
  public List<MovieDTO> findAll() {
    log.info("findAll Movies");
    return movieRepository.findAll().stream()
        .map(movie -> movieMapper.toModel(movie))
        .collect(Collectors.toList());
  }

  @Override
  public MovieDTO save(MovieDTO movieDTO) {
    log.info("save(movieDTO='{}')", movieDTO);
    Movie movie = movieMapper.toEntity(movieDTO);
    Movie savedMovie = movieRepository.save(movie);
    return movieMapper.toModel(savedMovie);
  }

  @Override
  public MovieDTO findById(Long id) {
    log.info("findById(id='{}')", id);
    Movie foundMovie = checkIfExistAndFindById(id);
    return movieMapper.toModel(foundMovie);
  }

  @Override
  public MovieDTO updateById(Long id, MovieDTO newMovieDTO) {
    log.info("updateById(id='{}', newMovieDTO='{}')", id, newMovieDTO);
    Movie foundMovie = checkIfExistAndFindById(id);
    foundMovie.setTitle(newMovieDTO.getTitle());
    foundMovie.setDescription(newMovieDTO.getDescription());
    foundMovie.setGenres(newMovieDTO.getGenres());
    Movie savedMovie = movieRepository.save(foundMovie);
    return movieMapper.toModel(savedMovie);
  }

  @Override
  public void deleteById(Long id) {
    log.info("deleteById(id='{}')", id);
    checkIfExistAndFindById(id);
    movieRepository.deleteById(id);
  }

  @Override
  public Movie checkIfExistAndFindById(Long id) {
    log.info("checkIfExistAndFindById(id='{}')", id);
    return movieRepository
        .findById(id)
        .orElseThrow(
            () -> new MovieNotFoundException(String.format("Movie with id '%d' not found.", id)));
  }
}
