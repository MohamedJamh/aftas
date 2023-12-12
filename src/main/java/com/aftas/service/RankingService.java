package com.aftas.service;

import com.aftas.domain.Ranking;
import com.aftas.dto.response.RankingResponseDto;
import com.aftas.exception.ValidationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RankingService {
    List<RankingResponseDto> getRankingsByCompetition(Long competitionId) throws ValidationException;

    Ranking getMemberCompetitionRankingIfExist(Long competitionId, Long memberId) throws ValidationException;

    List<RankingResponseDto> generateRankings(Long competitionId) throws ValidationException;
}
