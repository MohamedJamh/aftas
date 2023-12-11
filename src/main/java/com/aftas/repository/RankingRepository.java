package com.aftas.repository;

import com.aftas.domain.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RankingRepository extends JpaRepository<Ranking, Long> {
    @Query("SELECT r FROM Ranking r WHERE r.competition.id = :competitionId AND r.member.id = :memberId")
    Optional<Ranking> getRankingByCompetitionAndMember(Long competitionId, Long memberId);
}
