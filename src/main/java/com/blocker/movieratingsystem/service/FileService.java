package com.blocker.movieratingsystem.service;

import com.blocker.movieratingsystem.entity.File;
import com.blocker.movieratingsystem.model.FileDTO;

import java.util.List;

public interface FileService {
  List<FileDTO> findAll();

  FileDTO addFileToMovie(Long movieId, FileDTO fileDTO);

  FileDTO findById(Long id);

  FileDTO updateById(Long id, FileDTO newFileDTO);

  void deleteById(Long id);

  File checkIfExistAndFindById(Long id);
}
