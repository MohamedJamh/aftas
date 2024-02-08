package com.aftas.domain.mapper;

import com.aftas.domain.entities.Competition;
import com.aftas.domain.dto.request.competition.CompetitionRequestDto;

public class CompetitionDtoMapper {

    private CompetitionDtoMapper() {
    }

    public static Competition toEntity(CompetitionRequestDto competitionDto) {
        return Competition.builder()
                .date(competitionDto.getDate())
                .startTime(competitionDto.getStartTime())
                .endTime(competitionDto.getEndTime())
                .location(competitionDto.getLocation())
                .amount(competitionDto.getAmount())
                .build();
    }

    public static CompetitionRequestDto toDto(Competition competition) {
        return CompetitionRequestDto.builder()
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
