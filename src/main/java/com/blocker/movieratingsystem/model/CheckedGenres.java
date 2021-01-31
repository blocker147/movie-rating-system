package com.blocker.movieratingsystem.model;

import com.blocker.movieratingsystem.entity.Genre;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CheckedGenres {
    private Genre genre;
    private boolean isChecked;
}
