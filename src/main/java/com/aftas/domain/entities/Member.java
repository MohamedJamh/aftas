package com.aftas.domain.entities;

import com.aftas.domain.enums.IdentityDocumentationType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "members")
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer num;
    private String firstName;
    private String lastName;
    private LocalDate accessionDate;
    private String nationality;
    @Enumerated(EnumType.STRING)
    private IdentityDocumentationType identityType;
    @Column(unique = true)
    private String identityNumber;
    @OneToMany(mappedBy = "member")
    private List<Ranking> competitions;
}
