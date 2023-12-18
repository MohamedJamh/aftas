package com.aftas.dto.response;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CompetitionScoreResponseDto {
    private Integer num;
    private String firstName;
    private String lastName;
    private Integer score;
}
