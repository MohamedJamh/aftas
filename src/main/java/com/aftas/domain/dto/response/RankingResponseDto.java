package com.aftas.domain.dto.response;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RankingResponseDto {
    private Integer rank;
    private Integer score;
    private String firstName;
    private String lastName;
    private String nationality;
    private Integer num;

}
