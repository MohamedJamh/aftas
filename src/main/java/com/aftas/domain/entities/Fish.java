package com.aftas.domain.entities;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "fishes")
public class Fish {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String name;
    private Double averageWeight;
    @Column(columnDefinition = "TEXT")
    private String image;

    @ManyToOne
    private Level level;
}
