package com.aftas.service;

import com.aftas.domain.Competition;
import com.aftas.domain.Member;
import com.aftas.exception.ValidationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CompetitionService {
    List<Competition> getAllCompetitions();

    Competition createCompetition(Competition competition) throws ValidationException;

    void enrollMember(Long competitionId, Long memberId) throws ValidationException;

    Competition getCompetitionIfExists(Long competitionId) throws ValidationException;

    List<Member> getMembersByCompetitions(Long competitionId) throws ValidationException;

    List<Competition> upcomingCompetition();
}
