package com.aftas.domain.mapper;

import com.aftas.domain.entities.Fish;
import com.aftas.domain.entities.Level;
import com.aftas.domain.dto.request.fish.FishRequestDto;

public class FishDtoMapper {
    private FishDtoMapper() {
    }

    public static FishRequestDto toDto(Fish fish) {
        return FishRequestDto.builder()
                .id(fish.getId())
                .name(fish.getName())
                .averageWeight(fish.getAverageWeight())
                .levelCode(fish.getLevel().getCode())
                .image(fish.getImage())
                .build();
    }
    public static Fish toEntity(FishRequestDto fish) {
        return Fish.builder()
                .name(fish.getName())
                .averageWeight(fish.getAverageWeight())
                .level(
                        Level.builder()
                                .code(fish.getLevelCode())
                                .build()
                )
                .image(fish.getImage())
                .build();
    }
}
