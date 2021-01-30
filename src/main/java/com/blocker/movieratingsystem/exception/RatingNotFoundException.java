package com.blocker.movieratingsystem.exception;

public class RatingNotFoundException extends RuntimeException {
  public RatingNotFoundException(String message) {
    super(message);
  }
}
