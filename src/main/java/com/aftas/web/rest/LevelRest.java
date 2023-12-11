package com.aftas.web.rest;

import com.aftas.domain.Level;
import com.aftas.dto.LevelDto;
import com.aftas.exception.ValidationException;
import com.aftas.mapper.LevelDtoMapper;
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
    public ResponseEntity<Response<List<LevelDto>>> getAllLevels() {
        Response<List<LevelDto>> response = new Response<>();
        List<LevelDto> levels = new ArrayList<>();
        levelService.getAllLevels().stream().map(LevelDtoMapper::toDto).forEach(levels::add);
        response.setMessage("Levels retrieved successfully");
        response.setResult(levels);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<Response<LevelDto>> saveLevel(@RequestBody @Valid LevelDto level) throws ValidationException {
        Response<LevelDto> response = new Response<>();
        Level savedLevel = levelService.createLevel(LevelDtoMapper.toEntity(level));
        response.setMessage("Level created successfully");
        response.setResult(LevelDtoMapper.toDto(savedLevel));
        return ResponseEntity.ok().body(response);
    }
}
