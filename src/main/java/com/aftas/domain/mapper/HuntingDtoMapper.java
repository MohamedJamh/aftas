package com.aftas.domain.mapper;

import com.aftas.domain.entities.Hunting;
import com.aftas.domain.dto.response.hunting.HuntingResponseDto;

public class HuntingDtoMapper {

    private HuntingDtoMapper() {
    }

    public static HuntingResponseDto toDto(Hunting hunting) {
        return HuntingResponseDto.builder()
                .id(hunting.getId())
                .numberOfFish(hunting.getNumberOfFish())
                .build();
    }
}
