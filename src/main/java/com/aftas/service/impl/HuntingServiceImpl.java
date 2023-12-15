package com.aftas.service.impl;

import com.aftas.domain.*;
import com.aftas.dto.request.HuntingRequestDto;
import com.aftas.exception.ValidationException;
import com.aftas.repository.HuntingRepository;
import com.aftas.repository.RankingRepository;
import com.aftas.service.*;
import com.aftas.utils.ErrorMessage;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
@Component
public class HuntingServiceImpl implements HuntingService {

    private final HuntingRepository huntingRepository;
    private final MemberService memberService;
    private final CompetitionService competitionService;
    private final FishService fishService;
    private final RankingService rankingService;
    private final RankingRepository rankingRepository;

    public HuntingServiceImpl(HuntingRepository huntingRepository, MemberService memberService, CompetitionService competitionService, FishService fishService, RankingService rankingService, RankingRepository rankingRepository) {
        this.huntingRepository = huntingRepository;
        this.memberService = memberService;
        this.competitionService = competitionService;
        this.fishService = fishService;
        this.rankingService = rankingService;
        this.rankingRepository = rankingRepository;
    }

    @Override
    @Transactional
    public Optional<Hunting> createHunting(HuntingRequestDto huntingDto) throws ValidationException {
        //todo : follow best practices and keep service clean without dots , create mapper to entity , send hunt entity and weight seperately
        Member member = memberService.getMemberIfExists(huntingDto.getMemberId());
        Competition competition = competitionService.getCompetitionIfExists(huntingDto.getCompetitionId());
        LocalDateTime competitionStartDateTime = LocalDateTime.of(competition.getDate(), competition.getStartTime());
        if(LocalDateTime.now().isBefore(competitionStartDateTime))
            throw new ValidationException(new ErrorMessage("Competition has not started yet"));
        Ranking ranking = rankingService.getMemberCompetitionRankingIfExist(competition.getId(),member.getId());
        Fish fish = fishService.getFishIfExists(huntingDto.getFish().getName());
        if(fish.getAverageWeight() > huntingDto.getFish().getWeight())
            return Optional.empty();
        Optional<Hunting> optionalHunting = huntingRepository.getHuntingsByCompetitionAndMemberAndFish(competition.getId(), member.getId(), fish.getId());
        Hunting hunting = new Hunting();
        if(optionalHunting.isPresent()){
            hunting = optionalHunting.get();
            hunting.setNumberOfFish(hunting.getNumberOfFish() + 1);
        }else{
            hunting.setCompetition(competition);
            hunting.setMember(member);
            hunting.setFish(fish);
            hunting.setNumberOfFish(1);
        }
        ranking.setScore(ranking.getScore() + fish.getLevel().getPoints());
        rankingRepository.save(ranking);
        return Optional.of(huntingRepository.save(hunting));
    }
}
