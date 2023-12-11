package com.aftas.service.impl;

import com.aftas.domain.Competition;
import com.aftas.exception.ValidationException;
import com.aftas.repository.CompetitionRepository;
import com.aftas.service.CompetitionService;
import com.aftas.utils.ErrorMessage;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class CompetitionServiceImpl implements CompetitionService {

    private final CompetitionRepository competitionRepository;
    public CompetitionServiceImpl(CompetitionRepository competitionRepository) {
        this.competitionRepository = competitionRepository;
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
        competition.setNumberOfParticipants(0);
        String competitionCode = competition.getLocation().substring(0, 3).toUpperCase() + "-" +
                competition.getDate().getDayOfMonth() + "-" +
                competition.getDate().getMonthValue() + "-" +
                String.valueOf(competition.getDate().getYear()).substring(2);
        competition.setCode(competitionCode);
        return competitionRepository.save(competition);
    }
}
