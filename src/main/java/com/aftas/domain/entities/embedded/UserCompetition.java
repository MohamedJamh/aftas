package com.aftas.domain.entities.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserCompetition implements Serializable {
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "competition_id")
    private Long competitionId;
}
