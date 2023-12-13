package com.aftas.repository;

import com.aftas.domain.Ranking;
import com.aftas.dto.response.RankingResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RankingRepository extends JpaRepository<Ranking, Long> {
    @Query("SELECT r FROM Ranking r WHERE r.competition.id = :competitionId AND r.member.id = :memberId")
    Optional<Ranking> getRankingByCompetitionAndMember(Long competitionId, Long memberId);

    //If method return is 1 then competition is ranked
    @Query("SELECT count(r) FROM Ranking r WHERE r.competition.id = :competitionId and r.rank != 0")
    Integer countRankedMemberByCompetition(Long competitionId);
    @Query("SELECT r FROM Ranking r WHERE r.competition.id = :competitionId")
    List<Ranking> getRankingsByCompetitionOrderByScoreDesc(Long competitionId);

    @Query("SELECT new com.aftas.dto.response.RankingResponseDto(r.rank, r.score, r.member.firstName,r.member.lastName,r.member.nationality,r.member.num) " +
            "FROM Ranking r WHERE r.competition.id = :competitionId")
    List<RankingResponseDto> getRankingWithMemberByCompetition(Long competitionId);
}
