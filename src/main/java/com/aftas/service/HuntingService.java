package com.aftas.service;

import com.aftas.domain.Hunting;
import com.aftas.dto.request.HuntingRequestDto;
import com.aftas.exception.ValidationException;
import com.aftas.repository.HuntingRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface HuntingService {

    Optional<Hunting> createHunting(HuntingRequestDto hunting) throws ValidationException;
}
