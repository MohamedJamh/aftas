package com.aftas.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.ListIndexBase;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FishDto {
    private Long id;

    @NotNull(message = "Fish name cannot be null")
    @NotBlank(message = "Fish name cannot be blank")
    private String name;
    @NotNull(message = "Fish average weight cannot be null")
    @Min(value = 0, message = "Fish average weight cannot be negative")
    @DecimalMin(value = "0.1", message = "Fish average weight cannot be zero")
    private Double averageWeight;
    @NotNull(message = "Fish level cannot be null")
    @Min(value = 1, message = "Fish level cannot be negative or zero")
    private Integer level;
    @NotBlank(message = "Fish image cannot be blank")
    private String image;
}
