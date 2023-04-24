package com.oscar.movie;

import com.oscar.movie.model.Movie;
import com.oscar.movie.model.OscarAwards;
import com.oscar.movie.repository.MovieRepository;
import com.oscar.movie.repository.OscarAwardRepository;
import com.oscar.movie.util.MovieUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

@SpringBootApplication
@EnableJpaAuditing
public class OscarApplication implements CommandLineRunner {
	private static final Logger logger = LoggerFactory.getLogger(OscarApplication.class);
	@Autowired
	OscarAwardRepository oscarAwardRepository;
	@Autowired
	MovieRepository movieRepository;
	public static void main(String[] args) throws FileNotFoundException {
		SpringApplication.run(OscarApplication.class, args);
	}
	@Override
	public void run(String... args) throws Exception {
		InputStream inputStream = getClass().getResourceAsStream("/academy_awards.csv");
		List<OscarAwards> oscarAwardsList = MovieUtility.csvToOscarAwards(inputStream);
		oscarAwardsList.sort(Comparator.comparing(OscarAwards::getCategory));
		Set<Movie> movieSet = MovieUtility.getMovieSet(oscarAwardsList);

		oscarAwardRepository.saveAll(oscarAwardsList);
		movieRepository.saveAll(movieSet);
		logger.info("Oscar award data loaded..");
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return NoOpPasswordEncoder.getInstance();
	}
}
