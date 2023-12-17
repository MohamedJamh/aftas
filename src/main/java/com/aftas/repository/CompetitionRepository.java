package com.aftas.repository;

import com.aftas.domain.Competition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface CompetitionRepository extends JpaRepository<Competition, Long> {
    @Query("SELECT c FROM Competition c WHERE c.date = :date")
    Optional<Competition> getCompetitionByDate(LocalDate date);
    @Query("SELECT c FROM Competition c WHERE c.date >= current date order by c.date asc limit 2")
    List<Competition> upcomingCompetition();
}
