package com.aftas.domain.dto.response.role;

import com.aftas.domain.dto.response.permission.PermissionResponseDto;
import lombok.*;

import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RoleResponseDto {
    private String name;
    private Set<PermissionResponseDto> permissions;
}
