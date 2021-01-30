package com.blocker.movieratingsystem.service.file;

import com.blocker.movieratingsystem.entity.File;
import com.blocker.movieratingsystem.entity.Movie;
import com.blocker.movieratingsystem.exception.FileNotFoundException;
import com.blocker.movieratingsystem.exception.MovieNotFoundException;
import com.blocker.movieratingsystem.mapper.FileMapper;
import com.blocker.movieratingsystem.model.FileDTO;
import com.blocker.movieratingsystem.repository.FileRepository;
import com.blocker.movieratingsystem.repository.MovieRepository;
import com.blocker.movieratingsystem.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class FileServiceImpl implements FileService {

  @Autowired private FileMapper fileMapper;
  @Autowired private FileRepository fileRepository;
  @Autowired private MovieRepository movieRepository;

  @Override
  public List<FileDTO> findAll() {
    log.info("findAll Files");
    return fileRepository.findAll().stream()
        .map(file -> fileMapper.toModel(file))
        .collect(Collectors.toList());
  }

  @Override
  public FileDTO addFileToMovie(Long movieId, FileDTO fileDTO) {
    log.info("addFileToMovie(fileDTO='{}') byMovieId(id='{}')", fileDTO, movieId);
    Movie foundMovie =
            movieRepository
                    .findById(movieId)
                    .orElseThrow(
                            () ->
                                    new MovieNotFoundException(
                                            String.format("Movie with '%d' id not found.", movieId)));
    File file = fileMapper.toEntity(fileDTO);
    foundMovie.setFile(file);
    Movie savedMovie = movieRepository.save(foundMovie);
    return fileMapper.toModel(savedMovie.getFile());
  }

  @Override
  public FileDTO findById(Long id) {
    log.info("findById(id='{}')", id);
    File foundFile = checkIfExistAndFindById(id);
    return fileMapper.toModel(foundFile);
  }

  @Override
  public FileDTO updateById(Long id, FileDTO newFileDTO) {
    log.info("updateById(id='{}', newFileDTO='{}')", id, newFileDTO);
    File foundFile = checkIfExistAndFindById(id);
    foundFile.setSrc(newFileDTO.getSrc());
    File savedFile = fileRepository.save(foundFile);
    return fileMapper.toModel(savedFile);
  }

  @Override
  public void deleteById(Long id) {
    log.info("deleteById(id='{}')", id);
    checkIfExistAndFindById(id);
    fileRepository.deleteById(id);
  }

  @Override
  public File checkIfExistAndFindById(Long id) {
    log.info("checkIfExistAndFindById(id='{}')", id);
    return fileRepository
        .findById(id)
        .orElseThrow(
            () -> new FileNotFoundException(String.format("File with id '%d' not found.", id)));
  }
}
