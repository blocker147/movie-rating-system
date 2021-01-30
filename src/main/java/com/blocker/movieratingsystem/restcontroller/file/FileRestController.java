package com.blocker.movieratingsystem.restcontroller.file;

import com.blocker.movieratingsystem.model.FileDTO;
import com.blocker.movieratingsystem.service.FileService;
import com.blocker.movieratingsystem.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1/files")
public class FileRestController {

  @Autowired private FileService fileService;
  @Autowired private MovieService movieService;

  @GetMapping
  public List<FileDTO> findAll() {
    log.info("findAll Files");
    return fileService.findAll();
  }

  @GetMapping("/{id}")
  public FileDTO findById(@PathVariable Long id) {
    log.info("findById(id='{}')", id);
    return fileService.findById(id);
  }

  @PostMapping("/{id}")
  public FileDTO addFileToMovie(@PathVariable Long id, @RequestBody FileDTO fileDTO) {
    log.info("addFileToMovie(fileDTO='{}') byMovieId(id='{}')", fileDTO, id);
    return fileService.addFileToMovie(id, fileDTO);
  }

  @PutMapping("/{id}")
  public FileDTO updateById(@PathVariable Long id, @RequestBody FileDTO newFileDTO) {
    log.info("updateById(id='{}', newFileDTO='{}')", id, newFileDTO);
    return fileService.updateById(id, newFileDTO);
  }

  @DeleteMapping("/{id}")
  public void deleteById(@PathVariable Long id) {
    log.info("deleteById(id='{}')", id);
    fileService.deleteById(id);
  }
}
