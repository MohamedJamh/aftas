package com.aftas.dto.response;

import com.aftas.enums.IdentityDocumentationType;
import lombok.*;

import java.sql.Date;
import java.time.LocalDate;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MemberResponseDto {
    private Long id;
    private Integer num;
    private String firstName;
    private String lastName;
    private LocalDate accessionDate;
    private String nationality;
    private IdentityDocumentationType identityType;
    private String identityNumber;
}
