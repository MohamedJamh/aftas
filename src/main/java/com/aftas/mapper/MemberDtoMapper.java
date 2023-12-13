package com.aftas.mapper;

import com.aftas.domain.Member;
import com.aftas.dto.request.MemberRequestDto;
import com.aftas.dto.response.MemberResponseDto;

import javax.swing.text.html.parser.Entity;

public class MemberDtoMapper {

    private MemberDtoMapper() {
    }

    public static Member toEntity(MemberRequestDto memberRequestDto){
        return Member.builder()
            .firstName(memberRequestDto.getFirstName())
            .lastName(memberRequestDto.getLastName())
            .nationality(memberRequestDto.getNationality())
            .identityNumber(memberRequestDto.getIdentityNumber())
            .identityType(memberRequestDto.getIdentityType())
            .build();
    }

    public static MemberResponseDto toDto(Member member){
        return MemberResponseDto.builder()
            .id(member.getId())
            .firstName(member.getFirstName())
            .lastName(member.getLastName())
            .num(member.getNum())
            .nationality(member.getNationality())
            .identityNumber(member.getIdentityNumber())
            .identityType(member.getIdentityType())
            .accessionDate(member.getAccessionDate())
            .build();
    }

}
