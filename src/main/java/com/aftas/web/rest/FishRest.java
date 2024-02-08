package com.aftas.web.rest;

import com.aftas.domain.entities.Fish;
import com.aftas.domain.dto.request.fish.FishRequestDto;
import com.aftas.exception.custom.ValidationException;
import com.aftas.domain.mapper.FishDtoMapper;
import com.aftas.service.FishService;
import com.aftas.utils.Response;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/fishes")
public class FishRest {
    private final FishService fishService;

    public FishRest(FishService fishService) {
        this.fishService = fishService;
    }

    @GetMapping
    public ResponseEntity<Response<List<FishRequestDto>>> getAllFishes() {
        Response<List<FishRequestDto>> response = new Response<>();
        List<FishRequestDto> fishes = new ArrayList<>();
        fishService.getAllFishes().stream().map(FishDtoMapper::toDto).forEach(fishes::add);
        response.setMessage("Fishes retrieved successfully");
        response.setResult(fishes);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<Response<FishRequestDto>> saveFish(@RequestBody @Valid FishRequestDto fish) throws ValidationException {
        Response<FishRequestDto> response = new Response<>();
        Fish savedFish = fishService.createFish(FishDtoMapper.toEntity(fish));
        response.setMessage("Fish created successfully");
        response.setResult(FishDtoMapper.toDto(savedFish));
        return ResponseEntity.ok().body(response);
    }
}
