package com.aftas.domain.dto.response.user;

import com.aftas.domain.enums.IdentityDocumentationType;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserResponseDto {
    private Integer num;
    private String email;
    private String firstName;
    private String lastName;
    private LocalDate accessionDate;
    private String nationality;
    private IdentityDocumentationType identityType;
    private String identityNumber;
    private Set<String> rolePermissions;
    private Set<String> permissionGroupPermissions;
    private Boolean isEnable;
}
