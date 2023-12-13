package com.aftas.dto.response;

import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class MemberPageableDto {
    private List<MemberResponseDto> members;
    private Integer totalPages;
    private Long totalElements;
    private Integer currentPage;
}
