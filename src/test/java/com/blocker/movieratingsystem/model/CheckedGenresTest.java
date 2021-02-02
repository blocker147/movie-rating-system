package com.blocker.movieratingsystem.model;

import com.blocker.movieratingsystem.entity.Genre;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CheckedGenresTest {

  @Test
  void shouldReturnCheckedGenres() {
    CheckedGenres checkedGenres = new CheckedGenres(Genre.ACTION, true);
    CheckedGenres otherCheckedGenres = new CheckedGenres(Genre.ACTION, true);

    assertTrue(checkedGenres.canEqual(otherCheckedGenres));
    assertTrue(checkedGenres.equals(otherCheckedGenres));
    assertEquals(checkedGenres, otherCheckedGenres);
    assertEquals(checkedGenres.hashCode(), otherCheckedGenres.hashCode());
  }
}
