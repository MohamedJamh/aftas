package com.aftas.service.impl;

import com.aftas.domain.entities.Competition;
import com.aftas.domain.entities.Ranking;
import com.aftas.domain.entities.User;
import com.aftas.domain.entities.embedded.UserCompetition;
import com.aftas.domain.dto.response.competition.CompetitionScoreResponseDto;
import com.aftas.exception.custom.ValidationException;
import com.aftas.repository.CompetitionRepository;
import com.aftas.repository.UserRepository;
import com.aftas.repository.RankingRepository;
import com.aftas.service.CompetitionService;
import com.aftas.service.UserService;
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
    private final UserService userService;
    private final UserRepository userRepository;
    public CompetitionServiceImpl(CompetitionRepository competitionRepository, RankingRepository rankRepository , UserService userService, UserRepository memberRepository) {
        this.competitionRepository = competitionRepository;
        this.rankRepository = rankRepository;
        this.userService = userService;
        this.userRepository = memberRepository;
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
    public void enrollUser(Long competitionId, Integer userCode) throws ValidationException {
        Competition competition = getCompetitionIfExists(competitionId);
        User user = userService.getUserIfExistsByNumber(userCode);

        if(rankRepository.getRankingByCompetitionAndMember(competitionId, user.getId()).isPresent())
            throw new ValidationException(new ErrorMessage("Member already enrolled in this competition"));

        if(competition.getDate().isBefore(LocalDate.now()) || competition.getDate().equals(LocalDate.now()))
            throw new ValidationException(new ErrorMessage("Competition is already over"));

        LocalDateTime competitionDateTime = getCompetitionDateTime(competition);
        if(competitionDateTime.minusDays(1).isBefore(LocalDateTime.now()))
            throw new ValidationException(new ErrorMessage("Competition registration is closed"));

        competition.setNumberOfParticipants(competition.getNumberOfParticipants() + 1);
        competitionRepository.save(competition);

        rankRepository.save(
                Ranking.builder()
                        .id(
                            UserCompetition.builder()
                                .competitionId(competitionId)
                                .userId(user.getId())
                                .build()
                        )
                        .user(user)
                        .competition(competition)
                        .score(0)
                        .rank(0)
                        .build()
        );
    }

    private static LocalDateTime getCompetitionDateTime(Competition competition) {
        return LocalDateTime.of(competition.getDate(), competition.getStartTime());
    }

    @Override
    public Competition getCompetitionIfExists(Long competitionId) throws ValidationException {
        Optional<Competition> optionalCompetition = competitionRepository.findById(competitionId);
        if(optionalCompetition.isEmpty())
            throw new ValidationException(new ErrorMessage("Competition not found"));
        return optionalCompetition.get();
    }


    @Override
    public List<User> getUsersByCompetitions(Long competitionId) throws ValidationException {
        getCompetitionIfExists(competitionId);
        return userRepository.findAllByCompetition(competitionId);
    }

    @Override
    public List<Competition> upcomingCompetition() {
        return competitionRepository.upcomingCompetition();
    }

    @Override
    public List<CompetitionScoreResponseDto> realTimeScore(Long competitionId) throws ValidationException {
        Competition competition = getCompetitionIfExists(competitionId);
        if(LocalDateTime.now().isBefore(getCompetitionDateTime(competition)))
            throw new ValidationException(new ErrorMessage("Competition has not started yet"));
        return rankRepository.getRealTimeScore(competitionId);
    }
}
