package com.aftas.service.impl;

import com.aftas.domain.entities.Competition;
import com.aftas.exception.custom.ValidationException;
import com.aftas.repository.CompetitionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
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
    @DisplayName("create competition in occupied date should throw error")
    @Test
    void createCompetition_saveCompetitionInvalidDate_throwsError() {
        Competition invalidDateTimeCompetition = Competition.builder()
                .date(competition.getDate()) // same date as competition returned by repository mock
                .build();
        when(competitionRepository.getCompetitionByDate(invalidDateTimeCompetition.getDate())).thenReturn(java.util.Optional.of(competition));
        //doNothing().when(competitionRepository).save(invalidDateTimeCompetition);
        ValidationException exception = assertThrows(ValidationException.class, () -> competitionService.createCompetition(invalidDateTimeCompetition));
        assertEquals("There is already a competition on this day", exception.getErrorMessage().getMessage());
    }

    @DisplayName("create competition in invalid start / end time should throw error")
    @Test
    void createCompetition_saveCompetitionInvalidTime_throwsError() {
        Competition invalidTimeCompetition = Competition.builder()
                .date(LocalDate.now().plusDays(3))
                .startTime(LocalTime.now().plusHours(1)) // start time after end time
                .endTime(LocalTime.now())
                .build();
        when(competitionRepository.getCompetitionByDate(invalidTimeCompetition.getDate())).thenReturn(Optional.empty());
        ValidationException exception = assertThrows(ValidationException.class, () -> competitionService.createCompetition(invalidTimeCompetition));
        assertEquals("Start time cannot be equal or after end time", exception.getErrorMessage().getMessage());
    }

    @DisplayName("create competition in invalid date (less than 2 days from now) should throw error")
    @Test
    void createCompetition_saveCompetitionInvalidDate_throwsError2() {
        Competition invalidDateCompetition = Competition.builder()
                .date(LocalDate.now().plusDays(1)) // date less than 2 days from now
                .startTime(LocalTime.now())
                .endTime(LocalTime.now().plusHours(2))
                .build();
        when(competitionRepository.getCompetitionByDate(invalidDateCompetition.getDate())).thenReturn(Optional.empty());
        ValidationException exception = assertThrows(ValidationException.class, () -> competitionService.createCompetition(invalidDateCompetition));
        assertEquals("Competition date cannot be less than 2 days from now", exception.getErrorMessage().getMessage());
    }

    @DisplayName("create competition should pass with no thrown error")
    @Test
    void createCompetition_saveCompetitionValid_shouldPass() throws ValidationException {
        Competition validCompetition = Competition.builder()
                .location("somewhere")
                .numberOfParticipants(0)
                .date(LocalDate.now().plusDays(3))
                .startTime(LocalTime.now())
                .endTime(LocalTime.now().plusHours(2))
                .build();
        when(competitionRepository.getCompetitionByDate(validCompetition.getDate())).thenReturn(Optional.empty());
        when(competitionRepository.save(validCompetition)).thenReturn(validCompetition);
        Competition savedCompetition = competitionService.createCompetition(validCompetition);
        assertEquals(validCompetition, savedCompetition);
    }

}