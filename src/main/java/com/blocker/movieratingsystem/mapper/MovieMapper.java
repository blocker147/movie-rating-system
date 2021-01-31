package com.blocker.movieratingsystem.mapper;

import com.blocker.movieratingsystem.entity.Movie;
import com.blocker.movieratingsystem.entity.Rating;
import com.blocker.movieratingsystem.model.MovieDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.Collectors;

@Slf4j
@Component
public class MovieMapper {

  @Autowired private FileMapper fileMapper;
  @Autowired private RatingMapper ratingMapper;

  public Movie toEntity(MovieDTO movieDTO) {
    log.info("Map movieDTO='{}' model to entity", movieDTO);
    Movie movie = new Movie();
    movie.setTitle(movieDTO.getTitle());
    movie.setDescription(movieDTO.getDescription());
    movie.setGenres(movieDTO.getGenres());

    if (movieDTO.getFile() != null) {
      movie.setFile(fileMapper.toEntity(movieDTO.getFile()));
    }

    if (!movieDTO.getRatings().isEmpty()) {
      movie.setRatings(
          movieDTO.getRatings().stream()
              .map(ratingDTO -> ratingMapper.toEntity(ratingDTO))
              .collect(Collectors.toSet()));
    }

    log.info("Mapped movie='{}'", movie);
    return movie;
  }

  public MovieDTO toModel(Movie movie) {
    log.info("Map movie='{}' entity to model", movie);
    MovieDTO movieDTO = new MovieDTO();
    movieDTO.setId(movie.getId());
    movieDTO.setTitle(movie.getTitle());
    movieDTO.setDescription(movie.getDescription());
    movieDTO.setGenres(movie.getGenres());

    if (movie.getFile() != null) {
      movieDTO.setFile(fileMapper.toModel(movie.getFile()));
    }

    if (!movie.getRatings().isEmpty()) {
      movieDTO.setRatings(
          movie.getRatings().stream()
              .map(rating -> ratingMapper.toModel(rating))
              .collect(Collectors.toSet()));

      movieDTO.setAverage(
          BigDecimal.valueOf(
                  movie.getRatings().stream().mapToInt(Rating::getMark).average().getAsDouble())
              .setScale(2, RoundingMode.HALF_EVEN));
    }

    log.info("Mapped movieDTO='{}'", movieDTO);
    return movieDTO;
  }
}
