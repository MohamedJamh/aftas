package com.aftas.service;

import com.aftas.domain.entities.Ranking;
import com.aftas.domain.dto.response.ranking.RankingResponseDto;
import com.aftas.exception.custom.ValidationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RankingService {
    List<RankingResponseDto> getRankingsByCompetition(Long competitionId) throws ValidationException;

    Ranking getMemberCompetitionRankingIfExist(Long competitionId, Long memberId) throws ValidationException;

    void generateRankings(Long competitionId);
}
