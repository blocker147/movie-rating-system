package com.blocker.movieratingsystem.restcontroller.file;

import com.blocker.movieratingsystem.model.FileDTO;
import com.blocker.movieratingsystem.model.Models;
import com.blocker.movieratingsystem.service.FileService;
import com.blocker.movieratingsystem.service.MovieService;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.common.ConsoleNotifier;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {FileRestController.class})
class FileRestControllerTest {

  @Autowired private FileRestController fileRestController;
  @MockBean private FileService fileService;
  @MockBean private MovieService movieService;
  @MockBean private HttpServletResponse httpServletResponse;

  private static final String API = "/api/v1/files";
  private static final String URL = "http://localhost:8081" + API;
  private static final int PORT = 8081;
  private static final FileDTO FILE_DTO = Models.getFileDTO();
  private static final Long ID = Models.LONG_VALUE;

  private WireMockServer wireMockServer;
  private final RestTemplate restTemplate = new RestTemplate();

  @BeforeEach
  void setUp() {
    WireMockConfiguration wireMockConfiguration = getWireMockConfiguration();
    wireMockServer = new WireMockServer(wireMockConfiguration);
    setupStubForFindById();
    setupStubForFindAll();
    wireMockServer.start();
  }

  @AfterEach
  void tearDown() {
    wireMockServer.stop();
  }

  private WireMockConfiguration getWireMockConfiguration() {
    WireMockConfiguration wireMockConfiguration = new WireMockConfiguration();
    wireMockConfiguration.notifier(new ConsoleNotifier(true));
    wireMockConfiguration.port(PORT);
    return wireMockConfiguration;
  }

  private void setupStubForFindById() {
    wireMockServer.givenThat(
        get(urlEqualTo(API + "/1"))
            .willReturn(
                aResponse()
                    .withBodyFile("FileDTOResponse.json")
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")));
  }

  private void setupStubForFindAll() {
    wireMockServer.givenThat(
        get(urlEqualTo(API))
            .willReturn(
                aResponse()
                    .withBodyFile("FileDTOListResponse.json")
                    .withStatus(200)
                    .withHeader("Content-Type", "application/json")));
  }

  @Test
  void shouldReturnFileDTOResponse_on_FindById() {
    FileDTO response = restTemplate.getForObject(URL + "/1", FileDTO.class);
    assertNotNull(response);
    assertEquals(1L, response.getId());
    assertEquals("string", response.getSrc());
  }

  @Test
  void shouldReturnListOfFileDTOS_on_FindAll() {
    FileDTO[] fileDTOS = restTemplate.getForObject(URL, FileDTO[].class);

    assertNotNull(fileDTOS);
    assertEquals(2, fileDTOS.length);
    for (FileDTO fileDTO : fileDTOS) {
      assertEquals(1L, fileDTO.getId());
      assertEquals("string", fileDTO.getSrc());
    }
  }

  @Test
  void shouldReturnFileDTOList_on_findAll() {
    when(fileService.findAll()).thenReturn(Arrays.asList(FILE_DTO, FILE_DTO));

    List<FileDTO> fileDTOS = fileRestController.findAll();

    assertNotNull(fileDTOS);
    assertEquals(2, fileDTOS.size());
  }

  @Test
  void shouldReturnFileDTO_on_findById() {
    when(fileService.findById(ID)).thenReturn(FILE_DTO);

    FileDTO fileDTO = fileRestController.findById(ID);

    assertNotNull(fileDTO);
    assertEquals(FILE_DTO.getSrc(), fileDTO.getSrc());
  }

  @Test
  void shouldAddFileToMovie_on_findById() {
    when(fileService.addFileToMovie(ID, FILE_DTO)).thenReturn(FILE_DTO);

    FileDTO fileDTO = fileRestController.addFileToMovie(ID, FILE_DTO);

    assertNotNull(fileDTO);
    assertEquals(FILE_DTO.getSrc(), fileDTO.getSrc());
  }

  @Test
  void shouldUpdateFileForMovie_on_updateById() {
    FileDTO updatedFileDTO = Models.getUpdatedFileDTO();
    when(fileService.updateById(ID, updatedFileDTO)).thenReturn(updatedFileDTO);

    FileDTO fileDTO = fileRestController.updateById(ID, updatedFileDTO);

    assertNotNull(fileDTO);
    assertEquals(updatedFileDTO.getSrc(), fileDTO.getSrc());
  }

  @Test
  void shouldDeleteFile_on_deleteById() {
    doNothing().when(fileService).deleteById(ID);

    fileRestController.deleteById(ID);

    verify(fileService, times(1)).deleteById(ID);
  }

  @Test
  void shouldDisplayFileImage_on_displayImage() {
    try {
      when(httpServletResponse.getOutputStream()).thenThrow(new IOException("null"));
      fileRestController.displayImage("", httpServletResponse);
    } catch (IOException exception) {
      assertEquals("null", exception.getMessage());
    }
  }
}
