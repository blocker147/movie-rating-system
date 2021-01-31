package com.blocker.movieratingsystem.service.file;

import com.blocker.movieratingsystem.entity.Entities;
import com.blocker.movieratingsystem.entity.File;
import com.blocker.movieratingsystem.exception.FileNotFoundException;
import com.blocker.movieratingsystem.mapper.FileMapper;
import com.blocker.movieratingsystem.model.FileDTO;
import com.blocker.movieratingsystem.model.Models;
import com.blocker.movieratingsystem.repository.FileRepository;
import com.blocker.movieratingsystem.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {FileMapper.class, FileServiceImpl.class})
class FileServiceImplTest {

  @Autowired private FileServiceImpl fileService;
  @MockBean private FileRepository fileRepository;
  @MockBean private MovieRepository movieRepository;

  private static File FILE_WITH_ID;
  private static final Long ID = Entities.LONG_VALUE;

  @BeforeEach
  void setUp() {
    FILE_WITH_ID = Entities.getFile();
  }

  @Test
  void shouldReturnListOfFileDTOS() {
    File file = FILE_WITH_ID;

    when(fileRepository.findAll()).thenReturn(Arrays.asList(file, file, file));

    List<FileDTO> fileDTOS = fileService.findAll();

    assertNotNull(fileDTOS);
    assertEquals(3, fileDTOS.size());
    for (FileDTO fileDTO : fileDTOS) {
      assertNotNull(fileDTO.getId());
      assertEquals(file.getSrc(), fileDTO.getSrc());
    }
  }

  @Test
  void shouldFindMovieById_and_AddFileToMovie_and_SaveMovie_andReturnFileDTO() {
    File file = FILE_WITH_ID;
    when(movieRepository.findById(ID)).thenReturn(Optional.of(Entities.getMovie()));
    when(movieRepository.save(Entities.getMovie())).thenReturn(Entities.getMovie());

    FileDTO fileDTO = fileService.addFileToMovie(ID, Models.getFileDTO());

    assertNotNull(fileDTO);
    assertEquals(file.getId(), fileDTO.getId());
    assertEquals(file.getSrc(), fileDTO.getSrc());
  }

  @Test
  void shouldFindFileById_and_ReturnFileDTO() {
    File file = FILE_WITH_ID;
    when(fileRepository.findById(ID)).thenReturn(Optional.of(file));

    FileDTO fileDTO = fileService.findById(ID);

    assertNotNull(fileDTO);
    assertEquals(file.getId(), fileDTO.getId());
    assertEquals(file.getSrc(), fileDTO.getSrc());
  }

  @Test
  void shouldFindFileById_and_UpdateFile_and_SaveFile_and_ReturnUpdatedFileDTO() {
    File file = FILE_WITH_ID;
    when(fileRepository.findById(ID)).thenReturn(Optional.of(file));
    when(fileRepository.save(file)).thenReturn(file);

    FileDTO newFileDTO = Models.getUpdatedFileDTO();
    FileDTO fileDTO = fileService.updateById(ID, newFileDTO);

    assertNotNull(fileDTO);
    assertEquals(file.getId(), fileDTO.getId());
    assertEquals(newFileDTO.getSrc(), fileDTO.getSrc());
  }

  @Test
  void shouldFindFileById_and_DeleteFileById_and_ReturnVoid() {
    File file = FILE_WITH_ID;
    when(fileRepository.findById(ID)).thenReturn(Optional.of(file));
    doNothing().when(fileRepository).deleteById(ID);

    fileService.deleteById(ID);

    verify(fileRepository, times(1)).deleteById(ID);
  }

  @Test
  void shouldThrowFileNotFoundException_onFindById() {
    String exceptionMessage = String.format("File with '%d' not found.", ID);
    when(fileRepository.findById(ID)).thenThrow(new FileNotFoundException(exceptionMessage));

    try {
      assertThrows(FileNotFoundException.class, () -> fileService.checkIfExistAndFindById(ID));
      fileService.checkIfExistAndFindById(ID);
    } catch (FileNotFoundException e) {
      assertEquals(exceptionMessage, e.getMessage());
    }
  }
}
