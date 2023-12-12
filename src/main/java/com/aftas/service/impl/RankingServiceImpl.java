package com.aftas.service.impl;

import com.aftas.domain.Ranking;
import com.aftas.exception.ValidationException;
import com.aftas.repository.RankingRepository;
import com.aftas.service.RankingService;
import com.aftas.utils.ErrorMessage;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Component
public class RankingServiceImpl implements RankingService {

    private final RankingRepository rankingRepository;

    public RankingServiceImpl(RankingRepository rankingRepository) {
        this.rankingRepository = rankingRepository;
    }
    @Override
    public List<Ranking> getRankingsByCompetition(Long competitionId) throws ValidationException {
        return null;
    }

    @Override
    public Ranking getRankingIfExists(Long competitionId, Long memberId) throws ValidationException {
        Optional<Ranking> optionalRanking = rankingRepository.getRankingByCompetitionAndMember(competitionId, memberId);
        if(optionalRanking.isEmpty())
            throw new ValidationException(new ErrorMessage("Member not enrolled in competition"));
        return optionalRanking.get();

    }
}
