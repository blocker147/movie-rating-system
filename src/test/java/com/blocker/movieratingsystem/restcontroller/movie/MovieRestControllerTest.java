package com.blocker.movieratingsystem.restcontroller.movie;

import com.blocker.movieratingsystem.model.MovieDTO;
import com.blocker.movieratingsystem.model.Models;
import com.blocker.movieratingsystem.service.MovieService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@ExtendWith(SpringExtension.class)
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = {MovieRestController.class})
class MovieRestControllerTest {

    @Autowired private MovieRestController movieRestController;
    @MockBean private MovieService movieService;
    
    private static final MovieDTO MOVIE_DTO = Models.getMovieDTO();
    private static final Long ID = Models.LONG_VALUE;

    @Test
    void shouldReturnMovieDTOList_on_findAll() {
        when(movieService.findAll()).thenReturn(Arrays.asList(MOVIE_DTO, MOVIE_DTO));

        List<MovieDTO> movieDTOS = movieRestController.findAll();

        assertNotNull(movieDTOS);
        assertEquals(2, movieDTOS.size());
    }

    @Test
    void shouldReturnMovieDTO_on_findById() {
        when(movieService.findById(ID)).thenReturn(MOVIE_DTO);

        MovieDTO movieDTO = movieRestController.findById(ID);

        assertNotNull(movieDTO);
        assertEquals(MOVIE_DTO.getGenres(), movieDTO.getGenres());
    }

    @Test
    void shouldSaveMovieToMovie_on_save() {
        when(movieService.save(MOVIE_DTO)).thenReturn(MOVIE_DTO);

        MovieDTO movieDTO = movieRestController.save(MOVIE_DTO);

        assertNotNull(movieDTO);
        assertEquals(MOVIE_DTO.getGenres(), movieDTO.getGenres());
    }

    @Test
    void shouldUpdateMovieForMovie_on_updateById() {
        MovieDTO updatedMovieDTO = Models.getUpdatedMovieDTO();
        when(movieService.updateById(ID, updatedMovieDTO)).thenReturn(updatedMovieDTO);

        MovieDTO movieDTO = movieRestController.updateById(ID, updatedMovieDTO);

        assertNotNull(movieDTO);
        assertEquals(updatedMovieDTO.getGenres(), movieDTO.getGenres());
    }

    @Test
    void shouldDeleteMovie_on_deleteById() {
        doNothing().when(movieService).deleteById(ID);

        movieRestController.deleteById(ID);

        verify(movieService, times(1)).deleteById(ID);
    }
}