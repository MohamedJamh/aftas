package com.aftas.mapper;

import com.aftas.domain.Hunting;
import com.aftas.dto.response.HuntingResponseDto;

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
