package com.aftas.service;

import com.aftas.domain.entities.Fish;
import com.aftas.exception.custom.ValidationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FishService {
    Fish createFish(Fish fish) throws ValidationException;
    List<Fish> getAllFishes();

    Fish getFishIfExists(String fishName) throws ValidationException;
}
