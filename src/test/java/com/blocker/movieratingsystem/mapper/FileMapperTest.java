package com.blocker.movieratingsystem.mapper;

import com.blocker.movieratingsystem.entity.Entities;
import com.blocker.movieratingsystem.entity.File;
import com.blocker.movieratingsystem.model.FileDTO;
import com.blocker.movieratingsystem.model.Models;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = FileMapper.class)
class FileMapperTest {

  @Autowired private FileMapper fileMapper;

  @Test
  void shouldReturnFileEntity() {
    FileDTO fileDTO = Models.getFileDTO();

    File file = fileMapper.toEntity(fileDTO);

    assertNull(file.getId());
    assertEquals(fileDTO.getSrc(), file.getSrc());
  }

  @Test
  void shouldReturnFileModel() {
    File file = Entities.getFile();

    FileDTO fileDTO = fileMapper.toModel(file);

    assertEquals(file.getId(), fileDTO.getId());
    assertEquals(file.getSrc(), fileDTO.getSrc());
  }
}
