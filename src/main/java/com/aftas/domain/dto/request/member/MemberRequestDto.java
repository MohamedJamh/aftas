package com.aftas.domain.dto.request.member;

import com.aftas.domain.enums.IdentityDocumentationType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MemberRequestDto {
    private Long id;

    @NotNull(message = "First name is required")
    @NotBlank(message = "First name is required")
    private String firstName;
    @NotNull(message = "Last name is required")
    @NotBlank(message = "Last name is required")
    private String lastName;

    private LocalDate accessionDate;
    @NotNull(message = "Nationality is required")
    @NotBlank(message = "Nationality is required")
    private String nationality;
    @NotNull(message = "Identity type is required")
    private IdentityDocumentationType identityType;
    @NotNull(message = "Identity number is required")
    @NotBlank(message = "Identity number is required")
    private String identityNumber;
}
