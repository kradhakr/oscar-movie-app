package com.oscar.movie.service.impl;

import com.oscar.movie.model.Movie;
import com.oscar.movie.repository.MovieRepository;
import com.oscar.movie.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {
    @Autowired
    private MovieRepository movieRepository;

    @Override
    public List<Movie> findAll() {
        return (List<Movie>) movieRepository.findAll(
                Sort.by("title").ascending());
    }

    @Override
    public List<Movie> findTopRatedMovies(Integer totalRecords) {
        Pageable sortedByRatingDescBoxOfficeDesc =
                PageRequest.of(0, totalRecords,
                        Sort.by("totalRating").descending().and(Sort.by("boxOfficeValue").descending()));
        return movieRepository.findAllPageBy(sortedByRatingDescBoxOfficeDesc).getContent();
    }

    @Override
    public Optional<Movie> findById(Long movieId) {
        return movieRepository.findById(movieId);
    }

    @Override
    public Optional<Movie> findByMovieTitle(String movieTitle) {
        return movieRepository.findAllByTitle(movieTitle);
    }

    @Override
    public Movie save(Movie movie) {
        return movieRepository.save(movie);
    }
}
