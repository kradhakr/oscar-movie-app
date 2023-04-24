package com.oscar.movie.controller;

import com.oscar.movie.exception.ResourceNotFoundException;
import com.oscar.movie.model.Movie;
import com.oscar.movie.service.MovieService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class MovieController {
	private static final Log LOG = LogFactory.getLog(MovieController.class);

	@Autowired
	private MovieService movieService;

	@GetMapping("/movies")
	public List<Movie> getAllMovies() {
		return movieService.findAll();
	}

	@GetMapping("/movies/topRatedMovies/{totalRecords}")
	public List<Movie> getTopRatedMovies(@PathVariable(value = "totalRecords") Integer totalRecords) {
		return movieService.findTopRatedMovies(totalRecords);
	}

	@GetMapping("/movies/{movieTitle}")
	public Movie getMovieByMovieTitle(@PathVariable(value = "movieTitle") String movieTitle)
			throws ResourceNotFoundException {
		return this.movieService.findByMovieTitle(movieTitle)
				.orElseThrow(() -> new ResourceNotFoundException("Movie not found :: " + movieTitle));
	}

	@PostMapping("/movies")
	public Movie createMovie(@Valid @RequestBody Movie movie) {
		return movieService.save(movie);
	}

	@PutMapping("/movies/{id}")
	public Movie updateMovie(@PathVariable(value = "id") Long movieId,
							   @Valid @RequestBody Movie movieDetails) throws ResourceNotFoundException {
		return this.movieService.findById(movieId)
				.map(movie -> {
					movie.setTotalRating(movieDetails.getTotalRating());
					movie.setRatingDescription(movieDetails.getRatingDescription());
					movie.setBoxOfficeValue(movieDetails.getBoxOfficeValue());
					return this.movieService.save(movie);
				})
				.orElseThrow(() -> new ResourceNotFoundException("Movie not found :: " + movieId));
	}
}