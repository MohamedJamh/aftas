package com.aftas.domain.mapper;

import com.aftas.domain.entities.Fish;
import com.aftas.domain.entities.Level;
import com.aftas.domain.dto.FishDto;

public class FishDtoMapper {
    private FishDtoMapper() {
    }

    public static FishDto toDto(Fish fish) {
        return FishDto.builder()
                .id(fish.getId())
                .name(fish.getName())
                .averageWeight(fish.getAverageWeight())
                .levelCode(fish.getLevel().getCode())
                .image(fish.getImage())
                .build();
    }
    public static Fish toEntity(FishDto fish) {
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
