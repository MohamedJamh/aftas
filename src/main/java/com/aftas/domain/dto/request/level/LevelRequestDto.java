package com.aftas.domain.dto.request.level;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LevelRequestDto {
    private Long id;
    @NotNull(message = "Code is required")
    private Integer code;
    @NotNull(message = "Description is required")
    @NotBlank(message = "Description cannot be blank")
    private String description;
    @NotNull(message = "Points is required")
    @Min(value = 50, message = "Points must be at least 50")
    private Integer points;
}
