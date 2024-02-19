package com.aftas.repository;

import com.aftas.domain.entities.Hunting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HuntingRepository extends JpaRepository<Hunting, Long> {

    @Query("select h from Hunting h where h.competition.id = :competitionId and h.user.id = :userId and h.fish.id = :fishId")
    Optional<Hunting> getHuntsByCompetitionAndUserAndFish(Long competitionId, Long userId, Long fishId);
}
