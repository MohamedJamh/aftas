package com.aftas.mapper;

import com.aftas.domain.entities.Level;
import com.aftas.domain.dto.LevelDto;

public class LevelDtoMapper {
    private LevelDtoMapper() {
    }

    public static LevelDto toDto(Level level) {
        return LevelDto.builder()
                .id(level.getId())
                .code(level.getCode())
                .description(level.getDescription())
                .points(level.getPoints())
                .build();
    }

    public static Level toEntity(LevelDto level) {
        return Level.builder()
                .code(level.getCode())
                .description(level.getDescription())
                .points(level.getPoints())
                .build();
    }
}
