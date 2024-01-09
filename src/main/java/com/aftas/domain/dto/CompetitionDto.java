package com.aftas.domain.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CompetitionDto {
    private Long id;
    private String code;
    @NotNull(message = "Date cannot be null")
    @Future(message = "Date must be in the future")
    private LocalDate date;
    @NotNull(message = "Start time cannot be null")
    private LocalTime startTime;
    @NotNull(message = "End time cannot be null")
    private LocalTime endTime;
    private Integer numberOfParticipants;
    @NotNull(message = "Location cannot be null")
    @NotBlank(message = "Location cannot be blank")
    private String location;
    @NotNull(message = "Amount cannot be null")
    @Min(value = 0, message = "Amount cannot be negative")
    private Double amount;
}
