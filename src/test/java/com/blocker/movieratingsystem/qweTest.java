package com.blocker.movieratingsystem;

import com.blocker.movieratingsystem.entity.Genre;
import com.blocker.movieratingsystem.model.CheckedGenres;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class qweTest {

  @Test
  void shouldReturnArrayWithCheckedGenres() {
    Genre[] expected = new Genre[] {Genre.COMEDY, Genre.HORROR};

    List<CheckedGenres> actual = new ArrayList<>();
    boolean isPresented = false;
    for (Genre genre : Genre.values()) {
      for (Genre presented : expected) {
        if (presented == genre) {
          actual.add(new CheckedGenres(genre, true));
          isPresented = true;
          break;
        }
      }
      if (!isPresented) {
        actual.add(new CheckedGenres(genre, false));
      }
    }

    System.out.println(actual);
  }
}
