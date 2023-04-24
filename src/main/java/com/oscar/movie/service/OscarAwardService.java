package com.oscar.movie.service;

import com.oscar.movie.model.Movie;
import com.oscar.movie.model.OscarAwards;

import java.util.List;
import java.util.Optional;

public interface OscarAwardService {
    List<OscarAwards> findAll();
    Optional<OscarAwards> findByMovieTitle(String movieTitle);
    OscarAwards save(OscarAwards oscarAwards);

    Optional<OscarAwards> findById(Long oscarId);
}
