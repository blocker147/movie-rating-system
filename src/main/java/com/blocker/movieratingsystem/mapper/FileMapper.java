package com.blocker.movieratingsystem.mapper;

import com.blocker.movieratingsystem.entity.File;
import com.blocker.movieratingsystem.model.FileDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class FileMapper {

  public File toEntity(FileDTO fileDTO) {
    log.info("Map fileDTO='{}' model to entity", fileDTO);
    File file = new File();
    file.setSrc(fileDTO.getSrc());
    log.info("Mapped file='{}'", file);
    return file;
  }

  public FileDTO toModel(File file) {
    log.info("Map file='{}' entity to model", file);
    FileDTO fileDTO = new FileDTO();
    fileDTO.setId(file.getId());
    fileDTO.setSrc(file.getSrc());
    log.info("Mapped fileDTO='{}", fileDTO);
    return fileDTO;
  }
}
