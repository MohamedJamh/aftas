package com.aftas.service.impl;

import com.aftas.domain.Competition;
import com.aftas.domain.Member;
import com.aftas.domain.Ranking;
import com.aftas.domain.embedded.MemberCompetition;
import com.aftas.exception.ValidationException;
import com.aftas.repository.CompetitionRepository;
import com.aftas.repository.RankingRepository;
import com.aftas.service.CompetitionService;
import com.aftas.service.MemberService;
import com.aftas.utils.ErrorMessage;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class CompetitionServiceImpl implements CompetitionService {

    private final CompetitionRepository competitionRepository;
    private final RankingRepository rankRepository;
    private final MemberService memberService;
    public CompetitionServiceImpl(CompetitionRepository competitionRepository, RankingRepository rankRepository , MemberService memberService) {
        this.competitionRepository = competitionRepository;
        this.rankRepository = rankRepository;
        this.memberService = memberService;
    }

    @Override
    public List<Competition> getAllCompetitions() {
        return competitionRepository.findAll();
    }

    @Override
    public Competition createCompetition(Competition competition) throws ValidationException {
        if(competitionRepository.getCompetitionByDate(competition.getDate()).isPresent())
            throw new ValidationException(new ErrorMessage("There is already a competition on this day"));
        if( competition.getStartTime().isAfter(competition.getEndTime()) || competition.getStartTime().equals(competition.getEndTime()))
            throw new ValidationException(new ErrorMessage("Start time cannot be equal or after end time"));
        if(competition.getDate().minusDays(2).isBefore(LocalDate.now()) || competition.getDate().minusDays(2).equals(LocalDate.now()))
            throw new ValidationException(new ErrorMessage("Competition date cannot be less than 2 days from now"));
        competition.setNumberOfParticipants(0);
        String competitionCode = competition.getLocation().substring(0, 3).toUpperCase() + "-" +
                competition.getDate().getDayOfMonth() + "-" +
                competition.getDate().getMonthValue() + "-" +
                String.valueOf(competition.getDate().getYear()).substring(2);
        competition.setCode(competitionCode);
        return competitionRepository.save(competition);
    }

    @Override
    public void enrollMember(Long competitionId, Long memberId) throws ValidationException {
        Competition competition = getCompetitionIfExists(competitionId);
        Member member = memberService.getMemberIfExists(memberId);

        if(rankRepository.getRankingByCompetitionAndMember(competitionId, memberId).isPresent())
            throw new ValidationException(new ErrorMessage("Member already enrolled in this competition"));

        if(competition.getDate().isBefore(LocalDate.now()) || competition.getDate().equals(LocalDate.now()))
            throw new ValidationException(new ErrorMessage("Competition is already over"));

        LocalDateTime competitionDateTime = LocalDateTime.of(competition.getDate(), competition.getStartTime());
        if(competitionDateTime.minusDays(1).isBefore(LocalDateTime.now()))
            throw new ValidationException(new ErrorMessage("Competition registration is closed"));

        competition.setNumberOfParticipants(competition.getNumberOfParticipants() + 1);
        competitionRepository.save(competition);

        rankRepository.save(
                Ranking.builder()
                        .id(
                            MemberCompetition.builder()
                                    .competitionId(competitionId)
                                    .memberId(memberId)
                                    .build()
                        )
                        .member(member)
                        .competition(competition)
                        .score(0)
                        .rank(0)
                        .build()
        );
    }

    @Override
    public Competition getCompetitionIfExists(Long competitionId) throws ValidationException {
        Optional<Competition> optionalCompetition = competitionRepository.findById(competitionId);
        if(optionalCompetition.isEmpty())
            throw new ValidationException(new ErrorMessage("Competition not found"));
        return optionalCompetition.get();
    }
}
