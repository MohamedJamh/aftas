package com.aftas.web.rest;

import com.aftas.domain.Competition;
import com.aftas.dto.CompetitionDto;
import com.aftas.exception.ValidationException;
import com.aftas.mapper.CompetitionDtoMapper;
import com.aftas.service.CompetitionService;
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
}
