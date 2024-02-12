package com.aftas.web.rest;

import com.aftas.domain.entities.Competition;
import com.aftas.domain.dto.request.competition.CompetitionRequestDto;
import com.aftas.domain.dto.response.competition.CompetitionScoreResponseDto;
import com.aftas.domain.dto.response.member.MemberResponseDto;
import com.aftas.exception.custom.ValidationException;
import com.aftas.domain.mapper.CompetitionDtoMapper;
import com.aftas.domain.mapper.MemberDtoMapper;
import com.aftas.service.CompetitionService;
import com.aftas.utils.Response;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyAuthority('competition:read','competition:all','manage:all')")
    public ResponseEntity<Response<List<CompetitionRequestDto>>> getAllCompetitions() {
        Response<List<CompetitionRequestDto>> response = new Response<>();
        List<CompetitionRequestDto> competitions = new ArrayList<>();
        competitionService.getAllCompetitions()
                .stream()
                .map(CompetitionDtoMapper::toDto)
                .forEach(competitions::add);
        response.setMessage("Competitions retrieved successfully");
        response.setResult(competitions);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<Response<CompetitionRequestDto>> createCompetition(@RequestBody @Valid CompetitionRequestDto competition) throws ValidationException {
        Response<CompetitionRequestDto> response = new Response<>();
        Competition savedCompetition = competitionService.createCompetition(CompetitionDtoMapper.toEntity(competition));
        response.setMessage("Competition created successfully");
        response.setResult(CompetitionDtoMapper.toDto(savedCompetition));
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{competitionId}/enroll/{memberNumber}")
    public ResponseEntity<Response<CompetitionRequestDto>> enrollMember(@PathVariable("competitionId") String competitionId, @PathVariable("memberNumber") Integer memberNumber) throws ValidationException {
        Response<CompetitionRequestDto> response = new Response<>();
        competitionService.enrollMember(Long.valueOf(competitionId), memberNumber);
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

    @GetMapping("/upcoming")
    public ResponseEntity<Response<List<CompetitionRequestDto>>> upcomingCompetition() throws ValidationException{
        Response<List<CompetitionRequestDto>> response = new Response<>();
        List<Competition> competitions = competitionService.upcomingCompetition();
        if(competitions.isEmpty())
            response.setMessage("No upcoming competition");
        else response.setMessage("Upcoming competition retrieved successfully");
        response.setResult(
                competitions.stream().map(CompetitionDtoMapper::toDto).toList()
        );
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{competitionId}/real-time-score")
    public ResponseEntity<Response<List<CompetitionScoreResponseDto>>> realTimeScore(@PathVariable Long competitionId) throws ValidationException{
        Response<List<CompetitionScoreResponseDto>> response = new Response<>();
        List<CompetitionScoreResponseDto> competitionScores = competitionService.realTimeScore(competitionId);
        response.setMessage("Real time score retrieved successfully");
        response.setResult(competitionScores);
        return ResponseEntity.ok().body(response);
    }
}
