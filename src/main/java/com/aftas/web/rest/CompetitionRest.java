package com.aftas.web.rest;

import com.aftas.domain.Competition;
import com.aftas.dto.CompetitionDto;
import com.aftas.dto.response.MemberResponseDto;
import com.aftas.exception.ValidationException;
import com.aftas.mapper.CompetitionDtoMapper;
import com.aftas.mapper.MemberDtoMapper;
import com.aftas.service.CompetitionService;
import com.aftas.service.MemberService;
import com.aftas.utils.Response;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/competitions")
public class CompetitionRest {

    private final CompetitionService competitionService;

    public CompetitionRest(CompetitionService competitionService) {
        this.competitionService = competitionService;
    }

    @GetMapping
    public ResponseEntity<Response<List<CompetitionDto>>> getAllCompetitions() {
        Response<List<CompetitionDto>> response = new Response<>();
        List<CompetitionDto> competitions = new ArrayList<>();
        competitionService.getAllCompetitions()
                .stream()
                .map(CompetitionDtoMapper::toDto)
                .forEach(competitions::add);
        response.setMessage("Competitions retrieved successfully");
        response.setResult(competitions);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<Response<CompetitionDto>> createCompetition(@RequestBody @Valid CompetitionDto competition) throws ValidationException {
        Response<CompetitionDto> response = new Response<>();
        Competition savedCompetition = competitionService.createCompetition(CompetitionDtoMapper.toEntity(competition));
        response.setMessage("Competition created successfully");
        response.setResult(CompetitionDtoMapper.toDto(savedCompetition));
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{competitionId}/enroll/{memberId}")
    public ResponseEntity<Response<CompetitionDto>> enrollMember(@PathVariable("competitionId") String competitionId, @PathVariable("memberId") String memberId) throws ValidationException {
        Response<CompetitionDto> response = new Response<>();
        competitionService.enrollMember(Long.valueOf(competitionId), Long.valueOf(memberId));
        response.setMessage("Member enrolled successfully");
        return ResponseEntity.ok().body(response);
    }


    @GetMapping("/{competitionId}/members")
    public ResponseEntity<Response<List<MemberResponseDto>>> getMemberByCompetition(@PathVariable Long competitionId) throws ValidationException{
        Response<List<MemberResponseDto>> response = new Response<>();
        List<MemberResponseDto> members = new ArrayList<>();
        competitionService.getMembersByCompetitions(competitionId)
                .stream()
                .map(MemberDtoMapper::toDto)
                .forEach(members::add);
        response.setMessage("Members retrieved successfully");
        response.setResult(members);
        return ResponseEntity.ok().body(response);
    }
}
