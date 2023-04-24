package com.oscar.movie.service;

import com.oscar.movie.model.Movie;
import com.oscar.movie.model.OscarAwards;
import org.springframework.data.domain.Sort;
import java.util.List;
import java.util.Optional;

public interface MovieService {
    List<Movie> findAll();
    List<Movie> findTopRatedMovies(Integer totalRecords);
    Optional<Movie> findById(Long movieId);
    Optional<Movie> findByMovieTitle(String movieTitle);
    Movie save(Movie movie);
}
