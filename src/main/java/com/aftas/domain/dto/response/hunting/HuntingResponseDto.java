package com.aftas.domain.dto.response.hunting;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HuntingResponseDto {
    private Long id;
    private Integer numberOfFish;
}
