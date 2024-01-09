package com.aftas.domain.entities;

import com.aftas.domain.entities.embedded.MemberCompetition;
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
    private MemberCompetition id;

    @ManyToOne
    @MapsId("competitionId")
    @JoinColumn(name = "competition_id")
    private Competition competition;

    @ManyToOne
    @MapsId("memberId")
    @JoinColumn(name = "member_id")
    private Member member;

    private Integer rank;
    private Integer score;

}
