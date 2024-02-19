package com.aftas.domain.dto.response.member;

import com.aftas.domain.dto.response.user.UserResponseDto;
import lombok.*;

import java.util.List;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserPageableDto {
    private List<UserResponseDto> users;
    private Integer totalPages;
    private Long totalElements;
    private Integer currentPage;
}
