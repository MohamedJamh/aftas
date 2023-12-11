package com.aftas.web.rest;

import com.aftas.domain.Member;
import com.aftas.dto.request.MemberRequestDto;
import com.aftas.dto.response.MemberResponseDto;
import com.aftas.exception.ValidationException;
import com.aftas.mapper.MemberDtoMapper;
import com.aftas.service.MemberService;
import com.aftas.utils.Response;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/members")
public class MemberRest {
    private final MemberService memberService;

    public MemberRest(MemberService memberService) {
        this.memberService = memberService;
    }
    @GetMapping
    public ResponseEntity<Response<List<MemberResponseDto>>> getMember() {
        Response<List<MemberResponseDto>> response = new Response<>();
        List<MemberResponseDto> members = new ArrayList<>();
        memberService.getAllMembers().stream().map(MemberDtoMapper::toDto).forEach(members::add);
        response.setMessage("Members retrieved successfully");
        response.setResult(members);
        return ResponseEntity.ok().body(response);
    }
    @PostMapping
    public ResponseEntity<Response<MemberResponseDto>> saveMember(@RequestBody @Valid MemberRequestDto memberDto) throws ValidationException {
        Response<MemberResponseDto> response = new Response<>();
        Member member = MemberDtoMapper.toEntity(memberDto);
        Member savedMember = memberService.createMember(member);
        response.setMessage("Member created successfully");
        response.setResult(MemberDtoMapper.toDto(savedMember));
        return ResponseEntity.ok().body(response);
    }
}
