package com.aftas.service;

import com.aftas.domain.entities.Hunting;
import com.aftas.domain.dto.request.HuntingRequestDto;
import com.aftas.exception.ValidationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface HuntingService {

    Optional<Hunting> createHunting(HuntingRequestDto hunting) throws ValidationException;
}
