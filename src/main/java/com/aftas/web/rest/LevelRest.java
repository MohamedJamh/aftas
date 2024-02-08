package com.aftas.web.rest;

import com.aftas.domain.entities.Level;
import com.aftas.domain.dto.request.level.LevelRequestDto;
import com.aftas.exception.custom.ValidationException;
import com.aftas.domain.mapper.LevelDtoMapper;
import com.aftas.service.LevelService;
import com.aftas.utils.Response;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/levels")
public class LevelRest {
    private final LevelService levelService;

    public LevelRest(LevelService levelService) {
        this.levelService = levelService;
    }

    @GetMapping
    public ResponseEntity<Response<List<LevelRequestDto>>> getAllLevels() {
        Response<List<LevelRequestDto>> response = new Response<>();
        List<LevelRequestDto> levels = new ArrayList<>();
        levelService.getAllLevels().stream().map(LevelDtoMapper::toDto).forEach(levels::add);
        response.setMessage("Levels retrieved successfully");
        response.setResult(levels);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<Response<LevelRequestDto>> saveLevel(@RequestBody @Valid LevelRequestDto level) throws ValidationException {
        Response<LevelRequestDto> response = new Response<>();
        Level savedLevel = levelService.createLevel(LevelDtoMapper.toEntity(level));
        response.setMessage("Level created successfully");
        response.setResult(LevelDtoMapper.toDto(savedLevel));
        return ResponseEntity.ok().body(response);
    }
}
