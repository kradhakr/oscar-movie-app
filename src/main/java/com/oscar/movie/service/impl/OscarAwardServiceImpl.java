package com.oscar.movie.service.impl;

import com.oscar.movie.model.Movie;
import com.oscar.movie.model.OscarAwards;
import com.oscar.movie.repository.OscarAwardRepository;
import com.oscar.movie.service.OscarAwardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OscarAwardServiceImpl implements OscarAwardService {
    @Autowired
    private OscarAwardRepository oscarAwardRepository;

    @Override
    public List<OscarAwards> findAll() { return oscarAwardRepository.findAll(
            Sort.by("category").ascending().and(Sort.by("nominee").descending())); }

    @Override
    public Optional<OscarAwards> findByMovieTitle(String movieTitle) {
        return oscarAwardRepository.findOscarAwardsByNomineeIgnoreCase(movieTitle);
    }

    @Override
    public OscarAwards save(OscarAwards oscarAwards) {
        return oscarAwardRepository.save(oscarAwards);
    }

    @Override
    public Optional<OscarAwards> findById(Long oscarId) {
        return oscarAwardRepository.findById(oscarId);
    }
}
