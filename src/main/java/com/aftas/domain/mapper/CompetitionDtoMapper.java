package com.aftas.domain.mapper;

import com.aftas.domain.entities.Competition;
import com.aftas.domain.dto.CompetitionDto;

public class CompetitionDtoMapper {

    private CompetitionDtoMapper() {
    }

    public static Competition toEntity(CompetitionDto competitionDto) {
        return Competition.builder()
                .date(competitionDto.getDate())
                .startTime(competitionDto.getStartTime())
                .endTime(competitionDto.getEndTime())
                .location(competitionDto.getLocation())
                .amount(competitionDto.getAmount())
                .build();
    }

    public static CompetitionDto toDto(Competition competition) {
        return CompetitionDto.builder()
                .id(competition.getId())
                .code(competition.getCode())
                .date(competition.getDate())
                .startTime(competition.getStartTime())
                .endTime(competition.getEndTime())
                .location(competition.getLocation())
                .numberOfParticipants(competition.getNumberOfParticipants())
                .amount(competition.getAmount())
                .build();
    }
}
