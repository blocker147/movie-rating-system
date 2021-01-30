package com.blocker.movieratingsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Data
@Table
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Rating {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column private int mark;

  @ManyToOne
  @JoinColumn(name = "MOVIE_ID")
  private Movie movie;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Rating rating = (Rating) o;
    return Objects.equals(id, rating.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "Rating{" +
            "id=" + id +
            ", mark=" + mark +
            '}';
  }
}
