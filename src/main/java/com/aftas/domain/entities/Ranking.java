package com.aftas.domain.entities;

import com.aftas.domain.entities.embedded.UserCompetition;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "rankings")
public class Ranking {
    @EmbeddedId
    private UserCompetition id;

    @ManyToOne
    @MapsId("competitionId")
    @JoinColumn(name = "competition_id")
    private Competition competition;

    @ManyToOne
    @MapsId("memberId")
    @JoinColumn(name = "user_id")
    private User user;

    private Integer rank;
    private Integer score;

}
