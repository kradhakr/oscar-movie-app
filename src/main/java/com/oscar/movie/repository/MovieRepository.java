package com.oscar.movie.repository;

import com.oscar.movie.model.Movie;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieRepository extends PagingAndSortingRepository<Movie, Long> {
    Optional<Movie> findAllByTitle(String movieTitle);

    Iterable<Movie> findAll(Sort sort);
    Page<Movie> findAllPageBy(Pageable pageable);
}
