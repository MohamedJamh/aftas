package com.aftas.domain.mapper;

import com.aftas.domain.entities.Level;
import com.aftas.domain.dto.request.level.LevelRequestDto;

public class LevelDtoMapper {
    private LevelDtoMapper() {
    }

    public static LevelRequestDto toDto(Level level) {
        return LevelRequestDto.builder()
                .id(level.getId())
                .code(level.getCode())
                .description(level.getDescription())
                .points(level.getPoints())
                .build();
    }

    public static Level toEntity(LevelRequestDto level) {
        return Level.builder()
                .code(level.getCode())
                .description(level.getDescription())
                .points(level.getPoints())
                .build();
    }
}
