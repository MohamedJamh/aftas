package com.aftas.service.impl;

import com.aftas.domain.Competition;
import com.aftas.exception.ValidationException;
import com.aftas.repository.CompetitionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CompetitionServiceImplTest {

    @Mock
    private CompetitionRepository competitionRepository;
    @InjectMocks
    private CompetitionServiceImpl competitionService;

    private Competition competition;

    @BeforeEach
    void setUp() {
        LocalDate competitionDateTimeIn4Days = LocalDate.now().plusDays(1);
        competition = Competition.builder()
                .id(1L)
                .code("ABC-1-1-21")
                .date(competitionDateTimeIn4Days)
                .startTime(LocalTime.now())
                .endTime(LocalTime.now().plusHours(2))
                .location("ABC")
                .numberOfParticipants(0)
                .build();
    }
    @DisplayName("create competition in invalid date should throw error")
    @Test
    void createCompetition_saveCompetitionInvalidDate_throwsError() {
        Competition invalidDateTimeCompetition = Competition.builder()
                .code("ACD-1-1-21")
                .date(competition.getDate()) // same date as competition returned by repository mock
                .build();
        when(competitionRepository.getCompetitionByDate(invalidDateTimeCompetition.getDate())).thenReturn(java.util.Optional.of(competition));
        //doNothing().when(competitionRepository).save(invalidDateTimeCompetition);
        ValidationException exception = assertThrows(ValidationException.class, () -> competitionService.createCompetition(invalidDateTimeCompetition));
        assertEquals("There is already a competition on this day", exception.getErrorMessage().getMessage());
    }
}