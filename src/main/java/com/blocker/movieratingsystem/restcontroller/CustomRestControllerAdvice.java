package com.blocker.movieratingsystem.restcontroller;

import com.blocker.movieratingsystem.exception.FileNotFoundException;
import com.blocker.movieratingsystem.exception.MovieNotFoundException;
import com.blocker.movieratingsystem.exception.RatingNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class CustomRestControllerAdvice {

    private static final String MESSAGE = "message";
    private static final String STATUS_CODE = "statusCode"; 
    
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(FileNotFoundException.class)
    public Map<String, Object> handleFileNotFoundException(FileNotFoundException exception) {
        Map<String, Object> response = new HashMap<>();
        response.put(MESSAGE, exception.getMessage());
        response.put(STATUS_CODE, HttpStatus.NOT_FOUND);
        return response;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(RatingNotFoundException.class)
    public Map<String, Object> handleRatingNotFoundException(RatingNotFoundException exception) {
        Map<String, Object> response = new HashMap<>();
        response.put(MESSAGE, exception.getMessage());
        response.put(STATUS_CODE, HttpStatus.NOT_FOUND);
        return response;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(MovieNotFoundException.class)
    public Map<String, Object> handleMovieNotFoundException(MovieNotFoundException exception) {
        Map<String, Object> response = new HashMap<>();
        response.put(MESSAGE, exception.getMessage());
        response.put(STATUS_CODE, HttpStatus.NOT_FOUND);
        return response;
    }
}
