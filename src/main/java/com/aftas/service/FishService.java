package com.aftas.service;

import com.aftas.domain.Fish;
import com.aftas.exception.ValidationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface FishService {
    Fish createFish(Fish fish) throws ValidationException;

    List<Fish> getAllFishes();
}
