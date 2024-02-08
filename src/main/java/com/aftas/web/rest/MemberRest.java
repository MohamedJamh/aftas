package com.aftas.web.rest;

import com.aftas.domain.entities.Member;
import com.aftas.domain.dto.request.member.MemberRequestDto;
import com.aftas.domain.dto.response.member.MemberPageableDto;
import com.aftas.domain.dto.response.member.MemberResponseDto;
import com.aftas.exception.custom.ValidationException;
import com.aftas.domain.mapper.MemberDtoMapper;
import com.aftas.service.MemberService;
import com.aftas.utils.Response;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
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
    public ResponseEntity<Response<MemberPageableDto>> getMembers(
            @RequestParam(value="pageNo", required = false, defaultValue = "0") Integer pageNo,
            @RequestParam(value="pageSize", required = false, defaultValue = "5") Integer pageSize
    ) {
        Response<MemberPageableDto> response = new Response<>();
        Page<Member> memberPages = memberService.getAllMembers(pageNo,pageSize);
        List<MemberResponseDto> memberResponseDtos = new ArrayList<>();
        memberPages.stream().map(MemberDtoMapper::toDto).forEach(memberResponseDtos::add);
        response.setMessage("Members retrieved successfully");
        response.setResult(
                MemberPageableDto.builder()
                        .members(memberResponseDtos)
                        .totalPages(memberPages.getTotalPages())
                        .currentPage(memberPages.getNumber() + 1)
                        .totalElements(memberPages.getTotalElements())
                        .build()
        );
        return ResponseEntity.ok().body(response);
    }
    @GetMapping("/search")
    public ResponseEntity<Response<MemberPageableDto>> findByCriteria(
            @RequestParam(value="value") String searchValue,
            @RequestParam(value="pageNo", required = false, defaultValue = "0") Integer pageNo,
            @RequestParam(value="pageSize", required = false, defaultValue = "5") Integer pageSize
    ) {
        Response<MemberPageableDto> response = new Response<>();
        Page<Member> memberPages = memberService.findByCriteria(searchValue,pageNo,pageSize);
        List<MemberResponseDto> memberResponseDtos = new ArrayList<>();
        if(memberPages.isEmpty())
            response.setMessage("No member found");
        else response.setMessage("Members retrieved successfully");
        memberPages.stream().map(MemberDtoMapper::toDto).forEach(memberResponseDtos::add);
        response.setResult(
                MemberPageableDto.builder()
                        .members(memberResponseDtos)
                        .totalPages(memberPages.getTotalPages())
                        .currentPage(memberPages.getNumber() + 1)
                        .totalElements(memberPages.getTotalElements())
                        .build()
        );
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
