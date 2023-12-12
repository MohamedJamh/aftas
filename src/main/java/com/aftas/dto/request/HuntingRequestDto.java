package com.aftas.dto.request;

import com.aftas.dto.FishDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.validation.annotation.Validated;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class HuntingRequestDto {
    @NotNull(message = "Member id cannot be null")
    private Long memberId;
    @NotNull(message = "Competition id cannot be null")
    private Long competitionId;
    @Valid
    @NotNull(message = "Fish cannot be null")
    private HuntingFishRequestDto fish;

}
