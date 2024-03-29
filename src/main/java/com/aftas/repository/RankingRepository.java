package com.aftas.repository;

import com.aftas.domain.entities.Ranking;
import com.aftas.domain.dto.response.competition.CompetitionScoreResponseDto;
import com.aftas.domain.dto.response.ranking.RankingResponseDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RankingRepository extends JpaRepository<Ranking, Long> {
    @Query("SELECT r FROM Ranking r WHERE r.competition.id = :competitionId AND r.user.id = :memberId")
    Optional<Ranking> getRankingByCompetitionAndMember(Long competitionId, Long memberId);
    //If method return is 1 then competition is ranked
    @Query("SELECT count(r) FROM Ranking r WHERE r.competition.id = :competitionId and r.rank != 0")
    Integer countRankedMemberByCompetition(Long competitionId);
    @Query("SELECT r FROM Ranking r WHERE r.competition.id = :competitionId order by r.score desc")
    List<Ranking> getRankingsByCompetitionOrderByScoreDesc(Long competitionId);
    @Query("SELECT new com.aftas.domain.dto.response.ranking.RankingResponseDto(r.rank, r.score, r.user.firstName,r.user.lastName,r.user.nationality,r.user.num) " +
            "FROM Ranking r WHERE r.competition.id = :competitionId order by r.rank asc")
    List<RankingResponseDto> getRankingWithMemberByCompetition(Long competitionId);
    @Query("Select new com.aftas.domain.dto.response.competition.CompetitionScoreResponseDto(r.user.num,r.user.firstName,r.user.lastName,r.score) from Ranking r where r.competition.id = :competitionId order by r.score desc")
    List<CompetitionScoreResponseDto> getRealTimeScore(Long competitionId);
}
