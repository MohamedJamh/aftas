package com.aftas.service.impl;

import com.aftas.domain.Competition;
import com.aftas.domain.Ranking;
import com.aftas.dto.response.RankingResponseDto;
import com.aftas.exception.ValidationException;
import com.aftas.repository.RankingRepository;
import com.aftas.service.CompetitionService;
import com.aftas.service.RankingService;
import com.aftas.utils.ErrorMessage;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
@Component
public class RankingServiceImpl implements RankingService {

    private final RankingRepository rankingRepository;
    private final CompetitionService competitionService;

    public RankingServiceImpl(RankingRepository rankingRepository, CompetitionService competitionService) {
        this.rankingRepository = rankingRepository;
        this.competitionService = competitionService;
    }
    @Override
    public List<RankingResponseDto> getRankingsByCompetition(Long competitionId) throws ValidationException {
        competitionService.getCompetitionIfExists(competitionId);
        if(rankingRepository.countRankedMemberByCompetition(competitionId) <= 0)
            throw new ValidationException(new ErrorMessage("Competition rankings has not been generated"));
        return rankingRepository.getRankingWithMemberByCompetition(competitionId);
    }

    @Override
    public Ranking getMemberCompetitionRankingIfExist(Long competitionId, Long memberId) throws ValidationException {
        Optional<Ranking> optionalRanking = rankingRepository.getRankingByCompetitionAndMember(competitionId, memberId);
        if(optionalRanking.isEmpty())
            throw new ValidationException(new ErrorMessage("Member not enrolled in competition"));
        return optionalRanking.get();
    }

    @Override
    public List<RankingResponseDto> generateRankings(Long competitionId) throws ValidationException {
        competitionService.getCompetitionIfExists(competitionId);
        if(rankingRepository.countRankedMemberByCompetition(competitionId) > 1)
            throw new ValidationException(new ErrorMessage("Competition already ranked"));
        List<Ranking> rankings = rankingRepository.getRankingsByCompetitionOrderByScoreDesc(competitionId);
        rankings.forEach(ranking -> {
            ranking.setRank(rankings.indexOf(ranking) + 1);
            rankingRepository.save(ranking);
        });
        return rankingRepository.getRankingWithMemberByCompetition(competitionId);
    }
}
