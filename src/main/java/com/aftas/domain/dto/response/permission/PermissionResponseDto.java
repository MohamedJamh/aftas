package com.aftas.domain.dto.response.permission;

import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PermissionResponseDto {
    private String subject;
    private String action;
}
