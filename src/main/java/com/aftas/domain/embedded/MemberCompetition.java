package com.aftas.domain.embedded;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class MemberCompetition implements Serializable {
    @Column(name = "member_id")
    private Long memberId;
    @Column(name = "competition_id")
    private Long competitionId;
}
