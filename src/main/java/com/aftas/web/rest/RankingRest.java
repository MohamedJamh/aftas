package com.aftas.web.rest;

import com.aftas.dto.response.RankingResponseDto;
import com.aftas.exception.ValidationException;
import com.aftas.service.RankingService;
import com.aftas.utils.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.function.EntityResponse;

import java.util.List;

@RestController
@RequestMapping("/api/rankings")
public class RankingRest {

    private final RankingService rankingService;

    public RankingRest(RankingService rankingService) {
        this.rankingService = rankingService;
    }


    @GetMapping("/{competitionId}")
    public ResponseEntity<Response<List<RankingResponseDto>>> getRankings(@PathVariable Long competitionId) throws ValidationException {
        Response<List<RankingResponseDto>> response = new Response<>();
        response.setMessage("Rankings generated successfully");
        response.setResult(rankingService.getRankingsByCompetition(competitionId));
        return ResponseEntity.ok().body(response);
    }
}
