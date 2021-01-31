package com.blocker.movieratingsystem.controller;

import com.blocker.movieratingsystem.entity.Genre;
import com.blocker.movieratingsystem.model.CheckedGenres;
import com.blocker.movieratingsystem.model.FileDTO;
import com.blocker.movieratingsystem.model.MovieDTO;
import com.blocker.movieratingsystem.model.RatingDTO;
import com.blocker.movieratingsystem.service.FileService;
import com.blocker.movieratingsystem.service.MovieService;
import com.blocker.movieratingsystem.service.RatingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Controller
@RequestMapping("/movies")
public class MoviesController {

  @Autowired private FileService fileService;
  @Autowired private MovieService movieService;
  @Autowired private RatingService ratingService;
  private static final String MOVIE = "movie";
  private static final String REDIRECT_MOVIE_ID = "redirect:/movies/";

  @GetMapping
  public String findAll(Model model) {
    log.info("movies page");
    model.addAttribute("movies", movieService.findAll());
    return "movies";
  }

  @GetMapping("/{id}")
  public String findById(@PathVariable Long id, Model model) {
    log.info("movie page with id='{}'", id);
    MovieDTO foundMovieDTO = movieService.findById(id);
    model.addAttribute(MOVIE, foundMovieDTO);
    model.addAttribute("newRatingDTO", new RatingDTO());
    model.addAttribute("avg", foundMovieDTO.getAverage().doubleValue());
    return MOVIE;
  }

  @GetMapping("/create")
  public String createMovie(Model model) {
    log.info("create movie");
    model.addAttribute("newMovie", new MovieDTO());
    model.addAttribute("genres", Genre.values());
    return "create-movie";
  }

  @PostMapping("/save")
  public String saveMovie(
      @ModelAttribute MovieDTO movieDTO, @RequestParam("mFile") MultipartFile file) {
    log.info("save movie='{}'", movieDTO);

    try {
      String folder = "/Users/ilja.tarasovs/accentureRoadmap/movie-rating-system/files/";
      byte[] bytes = file.getBytes();
      String fileTitle = file.getOriginalFilename();
      Path path = Paths.get(folder + fileTitle);
      Path write = Files.write(path, bytes);
      log.info("folder='{}', path='{}', writePath='{}'", folder, path, write);
      if (fileTitle != null && !fileTitle.isBlank()) {
        fileTitle = fileTitle.toLowerCase().replaceFirst("[.][^.]+$", "");
      }
      movieDTO.getFile().setSrc(fileTitle);
    } catch (IOException e) {
      log.error("saveMovie exception='{}', e='{}'", e.getMessage(), e);
    }
    MovieDTO savedMovieDTO = movieService.save(movieDTO);
    return REDIRECT_MOVIE_ID + savedMovieDTO.getId();
  }

  @GetMapping("/{id}/edit")
  public String editMovie(@PathVariable Long id, Model model) {
    log.info("edit movie with id='{}'", id);
    MovieDTO foundMovieDTO = movieService.findById(id);
    model.addAttribute(MOVIE, foundMovieDTO);

    List<CheckedGenres> checkedGenres = new ArrayList<>();
    boolean isPresented = false;
    for (Genre genre : Genre.values()) {
      for (Genre presented : foundMovieDTO.getGenres()) {
        if (presented == genre) {
          checkedGenres.add(new CheckedGenres(genre, true));
          isPresented = true;
          break;
        } else {
          isPresented = false;
        }
      }
      if (!isPresented) {
        checkedGenres.add(new CheckedGenres(genre, false));
      }
    }

    model.addAttribute("checkedGenres", checkedGenres);
    model.addAttribute("genres", Genre.values());
    return "edit-movie";
  }

  @PostMapping("/{id}/update")
  public String updateMovie(@PathVariable Long id, @ModelAttribute MovieDTO newMovieDTO) {
    log.info("update movie='{}'", newMovieDTO);
    MovieDTO updatedMovieDTO = movieService.updateById(id, newMovieDTO);
    return REDIRECT_MOVIE_ID + updatedMovieDTO.getId();
  }

  @PostMapping("/{id}/vote")
  public String voteForMovie(@PathVariable Long id, @RequestParam int mark) {
    log.info("voteForMovie='{}', mark='{}'", id, mark);
    ratingService.addRatingToMovie(id, new RatingDTO(null, mark));
    return REDIRECT_MOVIE_ID + id;
  }

  @PostMapping("/{id}/delete")
  public String deleteMovie(@PathVariable Long id) {
    log.info("deleteMovie='{}'", id);
    movieService.deleteById(id);
    return "redirect:/movies";
  }

  @GetMapping("/{id}/files/edit")
  public String editFile(@PathVariable Long id, Model model) {
    log.info("editFile='{}'", id);
    model.addAttribute(MOVIE, movieService.findById(id));
    return "edit-file";
  }

  @PostMapping("/{id}/files/update")
  public String updateFile(@PathVariable Long id, @RequestParam("mFile") MultipartFile file) {
    log.info("updateFile='{}'", id);
    FileDTO fileDTO = new FileDTO();
    try {
      String folder = "/Users/ilja.tarasovs/accentureRoadmap/movie-rating-system/files/";
      byte[] bytes = file.getBytes();
      String fileTitle = file.getOriginalFilename();
      Path path = Paths.get(folder + fileTitle);
      Path write = Files.write(path, bytes);
      log.info("folder='{}', path='{}', writePath='{}'", folder, path, write);
      if (fileTitle != null && !fileTitle.isBlank()) {
        fileTitle = fileTitle.toLowerCase().replaceFirst("[.][^.]+$", "");
      }
      fileDTO.setSrc(fileTitle);
      fileService.addFileToMovie(id, fileDTO);
    } catch (IOException e) {
      log.error("saveMovie exception='{}', e='{}'", e.getMessage(), e);
    }
    return REDIRECT_MOVIE_ID + id;
  }
}
