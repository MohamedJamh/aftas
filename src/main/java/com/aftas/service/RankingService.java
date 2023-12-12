package com.aftas.service;

import com.aftas.domain.Ranking;
import com.aftas.exception.ValidationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RankingService {
    List<Ranking> getRankingsByCompetition(Long competitionId) throws ValidationException;

    Ranking getRankingIfExists(Long competitionId, Long memberId) throws ValidationException;
}
