package com.aftas.web.rest;

import com.aftas.domain.entities.Hunting;
import com.aftas.domain.dto.request.HuntingRequestDto;
import com.aftas.domain.dto.response.HuntingResponseDto;
import com.aftas.exception.custom.ValidationException;
import com.aftas.domain.mapper.HuntingDtoMapper;
import com.aftas.service.HuntingService;
import com.aftas.utils.Response;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/api/hunts")
public class HuntingRest {

    private final HuntingService huntingService;

    public HuntingRest(HuntingService huntingService) {
        this.huntingService = huntingService;
    }

    @PostMapping("/register")
    public ResponseEntity<Response<HuntingResponseDto>> createHunting(@RequestBody @Valid HuntingRequestDto hunting) throws ValidationException {
        Response<HuntingResponseDto> response = new Response<>();
        Optional<Hunting> optionalSavedHunting = huntingService.createHunting(hunting);
        HttpStatus status = HttpStatus.CREATED;
        if(optionalSavedHunting.isPresent()) {
            response.setMessage("Hunting registered successfully");
            response.setResult(HuntingDtoMapper.toDto(optionalSavedHunting.get()));
        }else {
            response.setMessage("Hunting not registered due to weight not above average weight");
            status = HttpStatus.BAD_REQUEST;
        }
        return ResponseEntity.status(status).body(response);
    }
}
