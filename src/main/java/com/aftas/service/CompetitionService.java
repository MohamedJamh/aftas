package com.aftas.service;

import com.aftas.domain.entities.Competition;
import com.aftas.domain.dto.response.competition.CompetitionScoreResponseDto;
import com.aftas.domain.entities.User;
import com.aftas.exception.custom.ValidationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CompetitionService {
    List<Competition> getAllCompetitions();

    Competition createCompetition(Competition competition) throws ValidationException;

    void enrollUser(Long competitionId, Integer userCode) throws ValidationException;

    Competition getCompetitionIfExists(Long competitionId) throws ValidationException;

    List<User> getUsersByCompetitions(Long competitionId) throws ValidationException;

    List<Competition> upcomingCompetition();

    List<CompetitionScoreResponseDto> realTimeScore(Long competitionId) throws ValidationException;
}
