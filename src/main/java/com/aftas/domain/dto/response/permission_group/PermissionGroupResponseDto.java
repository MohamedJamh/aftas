package com.aftas.domain.dto.response.permission_group;

import com.aftas.domain.dto.response.permission.PermissionResponseDto;
import lombok.*;

import java.util.Set;
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PermissionGroupResponseDto {
    private String name;
    private Set<PermissionResponseDto> permissions;
}
