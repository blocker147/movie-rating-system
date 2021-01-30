package com.blocker.movieratingsystem;

import com.blocker.movieratingsystem.entity.File;
import com.blocker.movieratingsystem.entity.Genre;
import com.blocker.movieratingsystem.entity.Movie;
import com.blocker.movieratingsystem.entity.Rating;
import com.blocker.movieratingsystem.model.FileDTO;
import com.blocker.movieratingsystem.repository.FileRepository;
import com.blocker.movieratingsystem.repository.MovieRepository;
import com.blocker.movieratingsystem.repository.RatingRepository;
import com.blocker.movieratingsystem.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.Arrays;
import java.util.HashSet;

@Slf4j
@SpringBootApplication
public class MovieRatingSystemConsoleApplication implements CommandLineRunner {

  @Autowired private FileService fileService;
  @Autowired private FileRepository fileRepository;
  @Autowired private MovieRepository movieRepository;
  @Autowired private RatingRepository ratingRepository;

  @Override
  public void run(String... args) throws Exception {
    log.info("Console application started ...");

    File file = new File(null, "src/path", null);
    Movie movie =
        new Movie(
            null,
            "title",
            "description",
            new Genre[] {Genre.COMEDY, Genre.HORROR},
            file,
            new HashSet<>());


    Movie savedMovie = movieRepository.save(movie);
    fileService.updateById(savedMovie.getFile().getId(), new FileDTO(null, "18v1by0r"));

    Rating a = new Rating(null, 5, savedMovie);
    Rating b = new Rating(null, 4, savedMovie);
    Rating c = new Rating(null, 3, savedMovie);

    ratingRepository.saveAll(Arrays.asList(a, b, c));
  }
}
