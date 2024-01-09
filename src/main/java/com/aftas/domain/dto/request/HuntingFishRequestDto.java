package com.aftas.domain.dto.request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HuntingFishRequestDto {
    @NotBlank(message = "Name cannot be empty")
    private String name;
    @NotNull(message = "Weight cannot be null")
    @DecimalMin(value = "0.10", message = "Weight cannot be negative or zero")
    private Double weight;
}
