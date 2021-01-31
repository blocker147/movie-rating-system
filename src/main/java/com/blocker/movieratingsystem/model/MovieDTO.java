package com.blocker.movieratingsystem.model;

import com.blocker.movieratingsystem.entity.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MovieDTO {
  private Long id;
  private String title;
  private String description;
  private Genre[] genres;
  private FileDTO file = new FileDTO();
  private Set<RatingDTO> ratings = new HashSet<>();
  private BigDecimal average = new BigDecimal("0.0");
}
