package com.blocker.movieratingsystem.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@Table
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Movie {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column private String title;
  @Column private String description;

  @Column(name = "GENRES")
  @ElementCollection(targetClass = Genre.class, fetch = FetchType.EAGER)
  @CollectionTable(name = "GENRE", joinColumns = @JoinColumn(name = "MOVIE_ID"))
  @Enumerated(value = EnumType.STRING)
  @OrderColumn
  private Genre[] genres;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "FILE_ID", referencedColumnName = "ID")
  private File file;

  @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL)
  private Set<Rating> ratings = new HashSet<>();

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Movie movie = (Movie) o;
    return Objects.equals(id, movie.id);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }

  @Override
  public String toString() {
    return "Movie{" +
            "id=" + id +
            ", title='" + title + '\'' +
            ", description='" + description + '\'' +
            ", genres=" + Arrays.toString(genres) +
            ", file=" + file +
            ", ratings=" + ratings +
            '}';
  }
}
