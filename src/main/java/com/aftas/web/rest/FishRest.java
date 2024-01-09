package com.aftas.web.rest;

import com.aftas.domain.entities.Fish;
import com.aftas.domain.dto.FishDto;
import com.aftas.exception.ValidationException;
import com.aftas.mapper.FishDtoMapper;
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
    public ResponseEntity<Response<List<FishDto>>> getAllFishes() {
        Response<List<FishDto>> response = new Response<>();
        List<FishDto> fishes = new ArrayList<>();
        fishService.getAllFishes().stream().map(FishDtoMapper::toDto).forEach(fishes::add);
        response.setMessage("Fishes retrieved successfully");
        response.setResult(fishes);
        return ResponseEntity.ok().body(response);
    }

    @PostMapping
    public ResponseEntity<Response<FishDto>> saveFish(@RequestBody @Valid FishDto fish) throws ValidationException {
        Response<FishDto> response = new Response<>();
        Fish savedFish = fishService.createFish(FishDtoMapper.toEntity(fish));
        response.setMessage("Fish created successfully");
        response.setResult(FishDtoMapper.toDto(savedFish));
        return ResponseEntity.ok().body(response);
    }
}
