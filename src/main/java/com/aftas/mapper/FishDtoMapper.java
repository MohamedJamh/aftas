package com.aftas.mapper;

import com.aftas.domain.Fish;
import com.aftas.domain.Level;
import com.aftas.dto.FishDto;

public class FishDtoMapper {
    private FishDtoMapper() {
    }

    public static FishDto toDto(Fish fish) {
        return FishDto.builder()
                .id(fish.getId())
                .name(fish.getName())
                .averageWeight(fish.getAverageWeight())
                .level(fish.getLevel().getCode())
                .image(fish.getImage())
                .build();
    }
    public static Fish toEntity(FishDto fish) {
        return Fish.builder()
                .name(fish.getName())
                .averageWeight(fish.getAverageWeight())
                .level(
                        Level.builder()
                                .code(fish.getLevel())
                                .build()
                )
                .image(fish.getImage())
                .build();
    }
}
