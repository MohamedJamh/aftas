package com.aftas.repository;

import com.aftas.domain.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LevelRepository extends JpaRepository<Level, Long> {
    @Query("SELECT l FROM Level l WHERE l.code = :code")
    Optional<Level> getLevelByCode(Integer code);
    @Query("SELECT MAX(l.points) FROM Level l WHERE l.code < :code")
    Integer findMaxPointUnderLevelCode(Integer code);
    @Query("SELECT l FROM Level l WHERE l.points = :points")
    Optional<Level> getLevelByPoints(Integer points);
}
