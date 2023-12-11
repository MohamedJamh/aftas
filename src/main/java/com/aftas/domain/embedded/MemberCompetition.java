package com.aftas.domain.embedded;

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
public class MemberCompetition implements Serializable {
    @Column(name = "member_id")
    private Long memberId;
    @Column(name = "competition_id")
    private Long competitionId;
}
