package com.aftas.domain.dto.request.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class SaveUserRequestDto {
    @NotBlank(message = "First name is required")
    private String firstName;
    @NotBlank(message = "Last name is required")
    private String lastName;
    @NotBlank(message = "Nationality is required")
    private String nationality;
    @NotBlank(message = "Identity Type is required")
    private String identityType;
    @NotBlank(message = "Identity Number is required")
    private String identityNumber;
}
