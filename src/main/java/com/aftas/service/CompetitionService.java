package com.aftas.service;

import com.aftas.domain.entities.Competition;
import com.aftas.domain.entities.Member;
import com.aftas.domain.dto.response.CompetitionScoreResponseDto;
import com.aftas.exception.ValidationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CompetitionService {
    List<Competition> getAllCompetitions();

    Competition createCompetition(Competition competition) throws ValidationException;

    void enrollMember(Long competitionId, Integer memberCode) throws ValidationException;

    Competition getCompetitionIfExists(Long competitionId) throws ValidationException;

    List<Member> getMembersByCompetitions(Long competitionId) throws ValidationException;

    List<Competition> upcomingCompetition();

    List<CompetitionScoreResponseDto> realTimeScore(Long competitionId) throws ValidationException;
}
