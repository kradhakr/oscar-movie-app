package com.oscar.movie.controller;

import com.oscar.movie.exception.ResourceNotFoundException;
import com.oscar.movie.model.OscarAwards;
import com.oscar.movie.service.OscarAwardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class OscarAwardController {
    @Autowired
    private OscarAwardService oscarAwardService;

    @GetMapping("/oscarAwards")
    public List<OscarAwards> getAllOscarAwards() {
        return oscarAwardService.findAll();
    }

    @GetMapping("/oscarAwards/bestPictures/{movieTitle}")
    public OscarAwards getOscarAwardByMovieTitle(@PathVariable(value = "movieTitle") String movieTitle)
            throws ResourceNotFoundException {
        return this.oscarAwardService.findByMovieTitle(movieTitle)
                .orElseThrow(() -> new ResourceNotFoundException("Movie not won best picture award :: " + movieTitle));
    }
}
