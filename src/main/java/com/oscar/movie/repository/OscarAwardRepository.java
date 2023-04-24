package com.oscar.movie.repository;

import com.oscar.movie.model.OscarAwards;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface OscarAwardRepository extends JpaRepository<OscarAwards, Long>{
    @Query("Select o from OscarAwards o  where (o.nominee = :movieTitle and o.category = 'Best Picture')")
    Optional<OscarAwards> findOscarAwardsByNomineeIgnoreCase(@Param("movieTitle") String movieTitle);

}
