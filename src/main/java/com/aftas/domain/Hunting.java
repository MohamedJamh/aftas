package com.aftas.domain;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "huntings")
public class Hunting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer numberOfFish;

    @ManyToOne
    private Fish fish;
    @ManyToOne
    private Member member;
    @ManyToOne
    private Competition competition;
}
