package com.aftas.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import lombok.*;

import java.sql.Date;
import java.sql.Time;
import java.util.List;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "competitions")
public class Competition {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true)
    private String code;
    @Future(message = "Date must be in the future")
    private Date date;
    private Time startTime;
    private Time endtTime;
    private Integer numberOfParticipants;
    private String location;
    private Double amount;

    @OneToMany(mappedBy = "competition")
    private List<Ranking> members;
}
