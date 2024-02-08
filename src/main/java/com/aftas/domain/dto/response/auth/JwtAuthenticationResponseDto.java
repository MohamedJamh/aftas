package com.aftas.domain.dto.response.auth;

import lombok.*;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class JwtAuthenticationResponseDto {
    private String accessToken;
    private String refreshToken;
}
