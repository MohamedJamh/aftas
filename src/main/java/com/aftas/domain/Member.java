package com.aftas.domain;

import com.aftas.enums.IdentityDocumentationType;
import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
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
    private Date accessionDate;
    private String nationality;
    @Enumerated(EnumType.STRING)
    private IdentityDocumentationType identityType;
    @Column(unique = true)
    private String identityNumber;


    @OneToMany(mappedBy = "member")
    private List<Ranking> competitions;
}
